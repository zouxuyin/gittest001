package com.zou.service.Impl;

import com.zou.dao.UserDao;
import com.zou.dao.Impl.UserDaoImpl;
import com.zou.entity.Page;
import com.zou.entity.User;
import com.zou.service.UserService;

import java.util.List;
import java.util.Map;


public class UserServiceImpl implements UserService {

    UserDao userDao =new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User userLogin = userDao.login(username, password);
        return userLogin;
    }


    @Override
    public Page<User> findAll(String currentPageString,String rowString,Map<String,String[]> map) {
        Integer currentPage = Integer.parseInt(currentPageString);
        int rows = Integer.parseInt(rowString);

        Integer startIndex =(currentPage-1)*rows;

        Integer totalCount = userDao.findTotalCount(map);

        Integer totalPage = totalCount % rows ==0 ?totalCount/rows:totalCount/rows+1;

        Page<User> userPage = new Page<>();
        List<User> list = userDao.findAll(startIndex,rows,map);

        userPage.setList(list);
        userPage.setCurrentPage(currentPage);
        userPage.setRow(rows);
        userPage.setTotalCount(totalCount);
        userPage.setTotalPage(totalPage);

        return  userPage;

    }

    @Override
    public boolean addUser(User user) {
        userDao.addUser(user);
        return true;
     }

    @Override
    public boolean update(String id) {
        userDao.update(id);
        return true;
    }


}
