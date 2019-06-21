package com.muniao.shirodemo.service.impl;

import com.muniao.shirodemo.domain.User;
import com.muniao.shirodemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Override
    public User findUser(User user)
    {
        if ("one".equals(user.getName()) && "123456".equals(user.getPassword()))
        {
            return new User("one", "123456", "user:one");
        }
        else if ("two".equals(user.getName()) && "123456".equals(user.getPassword()))
        {
            return new User("two", "123456", "user:two");
        }
        else
            return null;

    }
}
