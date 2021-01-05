package com.zou.service;


import com.zou.entity.Page;
import com.zou.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User login(String username,String password);

    Page<User> findAll(String currentPageString, String rows, Map<String,String []> map);

    boolean addUser(User user);

    boolean update(String id);
}
