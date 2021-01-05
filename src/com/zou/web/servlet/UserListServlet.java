package com.zou.web.servlet;


import com.zou.entity.Page;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet("/userListServlet")
public class UserListServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");

        String currentPageString = req.getParameter("currentPage");
        String rowsString = req.getParameter("rows");

        Map<String,String[]> strMap = req.getParameterMap();
        Map<String,String[]> map = new HashMap<>();
        Set<String> keyset = strMap.keySet();
        for(String s : keyset){
            if(s.equals("currentPage")||s.equals("rows")){
                continue;
            }
            map.put(s,strMap.get(s));
        }


        //如果前端页面传递过来的值是null，我们就展示第一页数据，手动给currentPage设值为1，rows设值为5
        if (currentPageString == null || currentPageString.equals("")){
            currentPageString = "1";
        }
        if (rowsString == null || rowsString.equals("")){
            rowsString = "5";
        }

        UserService userService =new UserServiceImpl();
        Page<User> userPage=userService.findAll(currentPageString,rowsString,map);
        req.setAttribute("userPage",userPage);
        req.setAttribute("condition",map);
        req.getRequestDispatcher("/list.jsp").forward(req,resp);
    }
}
