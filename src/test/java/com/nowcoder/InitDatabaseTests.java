package com.nowcoder;


import com.nowcoder.toutiao.dao.UserDao;
import com.nowcoder.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)
@Sql("/init-schema.sql")

public class InitDatabaseTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void initDate(){

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            User user  = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("USER%d", i));
            user.setPassword("");
            user.setSalt("");
            userDao.addUser(user);

            user.setPassword("nowcoder");
            userDao.updatePassword(user);

        }
        Assert.assertEquals("nowcoder", userDao.selectById(1).getPassword());
        userDao.deleById(1);
        Assert.assertNull(userDao.selectById(1));

    }

}
