package com.love.iLove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.iLove.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user where username=#{userName}")
    @Results({
            @Result(id=true,property="id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "enabled",column = "enabled"),
            @Result(property = "accountNonLocked",column = "account_non_locked"),
            @Result(property = "accountNonExpired",column = "account_non_expired"),
            @Result(property = "roles",column = "id",many = @Many(select = "com.love.iLove.mapper.RoleMapper.getNameByUserId"))
    })
    User getUserRoleByUserName(String userName);
}
