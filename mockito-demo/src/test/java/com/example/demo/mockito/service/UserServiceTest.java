package com.example.demo.mockito.service;

import com.example.demo.mockito.entity.User;
import com.example.demo.mockito.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper mockUserMapper;

    @InjectMocks
    private UserService userServiceUnderTest;

    @Test
    void testSave() {
        // Setup
        final User user = new User(0, "name", 0);
        when(mockUserMapper.insert(new User(0, "name", 0))).thenReturn(0);

        // Run the test
        final int result = userServiceUnderTest.save(user);

        // Verify the results
        assertThat(result).isEqualTo(0);
    }

    @Test
    void testEcho() {
        // Setup
        // Run the test
        userServiceUnderTest.echo();

        // Verify the results
    }

    @Test
    void testGetUser() {
        // Setup
        final User expectedResult = new User(0, "name", 0);

        // Run the test
        final User result = userServiceUnderTest.getUser();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
