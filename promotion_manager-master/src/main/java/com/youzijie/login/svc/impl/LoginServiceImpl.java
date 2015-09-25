package com.youzijie.login.svc.impl;

import com.jami.exception.BizErrorCode;
import com.jami.exception.BusinessException;
import com.youzijie.login.Po.AdminUserPo;
import com.youzijie.login.dao.AdminUserDo;
import com.youzijie.login.dao.AdminUserMapper;
import com.youzijie.login.svc.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by lei on 2015/7/16.
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Resource
    AdminUserMapper adminUserMapper;

    private SecureRandom random = new SecureRandom();

    private String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

    @Override
    public AdminUserPo auth(String userName, String pwd) throws BusinessException {
        AdminUserDo adminUserDo = adminUserMapper.queryUserByName(userName);
        if (adminUserDo == null) {
            throw BusinessException.createInstance(BizErrorCode.USER_NOT_EXIST);
        }
        String storePassword = adminUserDo.getPassword();
        String[] l = storePassword.split(";");
        if (l.length != 2) {
            throw BusinessException.createInstance(BizErrorCode.UNKNOW_ERROR);
        }
        String pwdMd5 = l[0];
        String salt = l[1];
        pwd = salt + pwd;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(pwd.getBytes(), 0, pwd.length());
            String md5 = new BigInteger(1, digest.digest()).toString(16);

            if (!md5.equals(pwdMd5)) {
                throw BusinessException.createInstance(BizErrorCode.PASSWORD_ERROR);
            }
        } catch (NoSuchAlgorithmException e) {
            throw BusinessException.createInstance(BizErrorCode.UNKNOW_ERROR);
        }

        adminUserDo.setToken(nextSessionId());
        adminUserMapper.update(adminUserDo);
        return new AdminUserPo(adminUserDo);
    }

    @Override
    public void logout(String userName, String token) throws BusinessException {
        AdminUserDo adminUserDo = adminUserMapper.queryUserByName(userName);
        if (adminUserDo == null) {
            throw BusinessException.createInstance(BizErrorCode.USER_NOT_EXIST);
        }
        if (adminUserDo.getToken() != token) {
            throw BusinessException.createInstance(BizErrorCode.PERMISSION_DENIED);
        }
        adminUserDo.setToken("");
        adminUserMapper.update(adminUserDo);
    }
}
