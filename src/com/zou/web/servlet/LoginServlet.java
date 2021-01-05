package com.zou.web.servlet;

import com.zou.entity.User;
import com.zou.service.Impl.UserServiceImpl;
import com.zou.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置字符集
        req.setCharacterEncoding("utf-8");

        //获取前端传递的参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        UserService userService =new UserServiceImpl();
        User login = userService.login(username, password);
        if(login==null){
            //用户不存在
            req.setAttribute("login_error","用户名密码错误！");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }else {
            HttpSession session = req.getSession();
            session.setAttribute("loginUser",login);
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
