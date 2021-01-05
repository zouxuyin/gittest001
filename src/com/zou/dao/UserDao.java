package com.zou.dao;



import com.zou.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User login(String username,String password);

    List<User> findAll(Integer startIndex, Integer rows, Map<String,String[]> map);

    Integer findTotalCount(Map<String,String[]> map);

    void addUser(User user);

    void update(String id);
}
