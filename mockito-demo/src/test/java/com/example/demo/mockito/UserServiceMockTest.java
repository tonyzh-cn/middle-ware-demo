package com.example.demo.mockito;

import com.example.demo.mockito.entity.User;
import com.example.demo.mockito.mapper.UserMapper;
import com.example.demo.mockito.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import javax.annotation.Resource;

import static org.mockito.Mockito.*;

/**
 * @author zhangtao
 * @since 2024/9/12 23:15
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceMockTest {
    @Mock
    private UserService mockUserService;
    @Spy
    private UserService spyUserService;

    private UserService staticMockUserService;
    private UserService staticSpyUserService;


    @InjectMocks
    @Spy
    private UserService injectUserService;
    @MockBean
    private UserMapper userMapper;

    @BeforeEach
    public void beforeEach(){
        staticMockUserService = Mockito.mock(UserService.class);
        staticSpyUserService = Mockito.spy(UserService.class);
    }

    @Test
    public void testMock() {
        System.out.println(Mockito.mockingDetails(mockUserService).isMock());//true
        System.out.println(Mockito.mockingDetails(spyUserService).isMock());//true
        System.out.println(Mockito.mockingDetails(mockUserService).isSpy());//false
        System.out.println(Mockito.mockingDetails(spyUserService).isSpy());//false
    }

    @Test
    public void testParamMatch(){
        User user =new User();
        user.setName("zhangsan");
        user.setAge(12);

        //参数完全匹配
        when(mockUserService.save(user)).thenReturn(100);
        System.out.println(mockUserService.save(user));//100

        user.setName("lisi");
        user.setAge(20);
        System.out.println(mockUserService.save(user));//0

        //参数任意匹配
        when(mockUserService.save(ArgumentMatchers.any())).thenReturn(100);
        System.out.println(mockUserService.save(user));//100
    }

    @Test
    public void testVerify(){
        verify(mockUserService,never()).echo();

        User user =new User();
        user.setName("zhangsan");
        user.setAge(12);
        mockUserService.save(user);

        //校验save user调用了一次
        Mockito.verify(mockUserService,Mockito.times(1)).save(user);

        //校验save任意对象调用了一次
        Mockito.verify(mockUserService,Mockito.times(1)).save(ArgumentMatchers.any());
    }

    @Test
    public void testAssert(){
        User user =new User();
        user.setName("zhangsan");
        user.setAge(12);

        when(mockUserService.save(user)).thenReturn(200);

        Assertions.assertEquals(200,mockUserService.save(user));
    }

    @Test
    public void testVoid(){
        doNothing().when(mockUserService).echo();
        Mockito.verify(mockUserService,Mockito.times(1)).echo();
    }

    @Test
    public void testSpy(){
        //调用真实对象方法
        when(spyUserService.getUser()).thenReturn(null);
        spyUserService.getUser();

        //不会调用真实对象方法
        doReturn(null).when(spyUserService).getUser();
        spyUserService.getUser();
    }

    @Test
    public void testException(){
        doThrow(RuntimeException.class).when(mockUserService).getUser();

        try {
            mockUserService.getUser();
        }catch (Exception e){
            Assertions.assertTrue(e instanceof RuntimeException);
        }
    }

    @Test
    public void testMulti(){
        when(mockUserService.getUser())
                .thenReturn(new User(1,"1",1))
                .thenReturn(new User(2,"2",2));

        System.out.println(mockUserService.getUser());//第一次返回1

        System.out.println(mockUserService.getUser());//第二次返回2
    }

    @Test
    public void testAnswer(){
        when(mockUserService.save(ArgumentMatchers.any())).thenAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                User user = invocation.getArgument(0, User.class);
                return user.getId();
            }
        });

        System.out.println(mockUserService.save(new User(10,"110",10)));
    }

    @Test
    public void testCallRealMethod(){
        doCallRealMethod().when(mockUserService).echo();
        mockUserService.echo();
    }

    @Test
    public void testInjectMocks(){
        when(userMapper.insert(ArgumentMatchers.any())).thenReturn(200);

        Assertions.assertEquals(200,injectUserService.save(new User(1,"1",1)));
    }
}
