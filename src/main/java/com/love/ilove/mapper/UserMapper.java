package com.love.ilove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.ilove.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author dengtianjiao
 */
@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 更具用户名，查询用户和对应的权限
     * @param userName
     * @return
     */
    @Select("select * from user where username=#{userName}")
    @Results({
            @Result(id=true,property="id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "enabled",column = "enabled"),
            @Result(property = "accountNonLocked",column = "account_non_locked"),
            @Result(property = "accountNonExpired",column = "account_non_expired"),
            @Result(property = "roles",column = "id",many = @Many(select = "com.love.ilove.mapper.RoleMapper.getNameByUserId"))
    })
    User getUserRoleByUserName(String userName);
}
