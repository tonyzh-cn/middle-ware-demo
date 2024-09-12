package com.example.demo.mockito;

import com.example.demo.mockito.entity.User;
import com.example.demo.mockito.mapper.UserMapper;
import com.example.demo.mockito.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.annotation.Resource;

import static org.mockito.Mockito.*;

/**
 * @author zhangtao
 * @since 2024/9/12 23:15
 */
@SpringBootTest(classes = Application.class)
public class UserServiceMockBeanTest {
    @Resource
    @SpyBean
    private UserService userService;

    @MockBean(name = "userMapper")//这里必须配置name，否则无法注入mock的mapper
    private UserMapper userMapper;

    @Test
    public void testMockBean(){
        when(userMapper.insert(ArgumentMatchers.any())).thenReturn(200);
        Assertions.assertEquals(200, userService.save(new User(1,"1",1)));

    }
}
