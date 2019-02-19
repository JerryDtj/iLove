package com.love.iLove.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.iLove.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
