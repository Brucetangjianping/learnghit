package com.youzijie.login.svc;

import com.jami.exception.BusinessException;
import com.youzijie.login.Po.AdminUserPo;

/**
 * Created by lei on 2015/7/16.
 */
public interface LoginService {
    public AdminUserPo auth(String userName, String pwd) throws BusinessException;

    public void logout(String userName, String token) throws BusinessException;
}