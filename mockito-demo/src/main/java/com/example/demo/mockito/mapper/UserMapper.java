package com.example.demo.mockito.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.mockito.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhangtao
 * @since 2024/9/12 23:13
 */
//@Mapper
//@Repository
@DS("test")
public interface UserMapper  extends BaseMapper<User> {
}
