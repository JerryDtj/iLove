package com.love.iLove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.iLove.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {


}
