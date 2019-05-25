package com.love.ilove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.ilove.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dengtianjiao
 */
@Repository
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 用户用户id查询角色名
     * @param userId
     * @return
     */
    @Select("select name from role r left join user_role ur on id=role_id and user_role_status=0 where user_id=#{userId}")
    List<String> getNameByUserId(int userId);
}
