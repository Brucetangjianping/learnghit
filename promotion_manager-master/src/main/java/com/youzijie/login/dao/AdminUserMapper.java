package com.youzijie.login.dao;

import com.jami.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AdminUserMapper extends BaseMapper {
	@Select("select * from t_promotion_admin where userName= #{name};")
	public AdminUserDo queryUserByName(String name);

	@Update("UPDATE t_promotion_admin SET token=#{userDo.token} WHERE userName=#{userDo.userName}")
	public int update(@Param("userDo") AdminUserDo userDo);
}
