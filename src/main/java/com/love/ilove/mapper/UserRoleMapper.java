package com.love.ilove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.ilove.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author dengtianjiao
 */
@Repository
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {


}
