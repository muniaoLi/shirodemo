package com.muniao.shirodemo.service.impl;

import com.muniao.shirodemo.domain.User;
import com.muniao.shirodemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{

    @Override
    public User findUserByName(String userName)
    {
        if ("one".equals(userName))
        {
            return new User("one", "123456", "user:one","abcd");
        }
        else if ("two".equals(userName))
        {
            return new User("two", "123456", "user:two","abcd");
        }
        else
            return null;
    }

}
