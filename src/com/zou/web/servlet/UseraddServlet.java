package com.zou.web.servlet;


import com.zou.entity.User;
import com.zou.service.Impl.UserServiceImpl;
import com.zou.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/useraddServlet")
public class UseraddServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");


        String name = req.getParameter("name");
        String gender = req.getParameter("sex");
        int age = Integer.parseInt(req.getParameter("age"));
        String address = req.getParameter("address");
        String qq = req.getParameter("qq");
        String email = req.getParameter("email");
        User user = new User();

        user.setName(name);
        user.setGender(gender);
        user.setAge(age);
        user.setAddress(address);
        user.setQq(qq);
        user.setEmail(email);
        UserService userService = new UserServiceImpl();
        userService.addUser(user);
        resp.sendRedirect(req.getContextPath()+"/list.jsp");
//
//            req.setAttribute("add_error","添加失败！");
//            req.getRequestDispatcher("/add.jsp").forward(req,resp);
       // }


    }
}
