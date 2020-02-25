package me.hujinbo.boot.example.mapper;

import me.hujinbo.boot.example.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * Mybatis Plus通用Mapper测试类
 *
 * @author hujinbo
 */
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        List<User> userList = userMapper.selectList(null);
        Assertions.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}