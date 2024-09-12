package com.example.demo.mockito.service;

import com.example.demo.mockito.entity.User;
import com.example.demo.mockito.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangtao
 * @since 2024/9/12 23:11
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int save(User user){
        return userMapper.insert(user);
    }

    public void echo(){
        System.out.println("echo");
    }

    public User getUser(){
        User user =new User();
        user.setName("zhangsan");
        user.setAge(12);

        System.out.println("getUser");
        return user;
    }
}
