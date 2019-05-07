package com.love.iLove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.iLove.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select name from role r left join user_role ur on id=role_id and user_role_status=0 where user_id=#{userId}")
    List<String> getNameByUserId(int userId);
}
