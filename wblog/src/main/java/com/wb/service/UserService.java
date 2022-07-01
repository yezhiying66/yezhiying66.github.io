package com.wb.service;

import com.wb.pojo.User;

/**
 * Created by wb on 2020/2/18 0018 13:04
 */
public interface UserService {

    User checkUser(String username, String password);
}
