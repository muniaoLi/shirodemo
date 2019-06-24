package com.muniao.shirodemo.service;

import com.muniao.shirodemo.domain.User;

public interface UserService
{
    User findUserByName(String userName);
}
