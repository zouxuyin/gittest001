package com.zou.dao.Impl;

import com.zou.dao.UserDao;
import com.zou.entity.Page;
import com.zou.entity.User;
import com.zou.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    @Override
    public User login(String username, String password) {

        //定义sql语句
        String sql ="select * from user where username=? and password=?";
        User user = new User();
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            //获取连接对象
            connection = JDBCUtils.getConnection();
            //获取preparedStatement
            preparedStatement =connection.prepareStatement(sql);
            //给占位符赋值
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            //执行sql语句
            resultSet = preparedStatement.executeQuery();
            //处理resultSet对象
            while (resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setGender(resultSet.getString("gender"));
                user.setAge(resultSet.getInt("age"));
                user.setAddress(resultSet.getString("address"));
                user.setQq(resultSet.getString("qq"));
                user.setEmail(resultSet.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(resultSet,preparedStatement,connection);
        }

        return null;
    }

    @Override
    public List<User> findAll(Integer startIndex, Integer rows, Map<String,String[]> map) {

        //定义sql语句
        //String sql="select * from user LIMIT ?,?;";
        StringBuilder builderSql = new StringBuilder("select * from user where 1=1");

        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<User> userList=new ArrayList<>();

        List values = new ArrayList();

        //将map集合中的字段和字段对应的值封装到两个list集合中，value的集合有值name就拼接对应的字段到sql语句
        //1.遍历map集合
        Set<String> keys = map.keySet();
        for (String key : keys) {
            //将空的字段排除
            if (map.get(key)[0].equals("") || map.get(key)[0] == null){
                //该字段没有传递过来值
                continue;
            }
            //该字段有值
            //2.将传递过来的参数进行封装
            String[] strings = map.get(key);
            values.add(strings[0]);
            //3.拼接sql语句
            builderSql.append(" and "+key+" like ?");//select * from user where 1 = 1 and name like ?;
        }
        //4.拼接分页查询的占位符
        builderSql.append(" limit ?,?");//select * from user where 1 = 1 and name like ? limit ?,?;

        //5.builderSql转为String类型
        String sql = builderSql.toString();
        try {
            //获取连接对象
            connection=JDBCUtils.getConnection();

            preparedStatement=connection.prepareStatement(sql);

            //给占位符赋值，遍历values集合，进行赋值
            //instanceof 可以判断该数据的数据类型
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i) instanceof String){
                    preparedStatement.setString(i+1, "%"+(String) values.get(i)+"%");
                }
                if (values.get(i) instanceof Integer){
                    preparedStatement.setInt(i+1,(Integer)values.get(i));
                }
            }

            preparedStatement.setInt(1,startIndex);
            preparedStatement.setInt(2,rows);

            resultSet=preparedStatement.executeQuery();

            while (resultSet.next()){
                User user=new User();
                user.setId(resultSet.getInt(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setName(resultSet.getString(4));
                user.setGender(resultSet.getString(5));
                user.setAge(resultSet.getInt(6));
                user.setAddress(resultSet.getString(7));
                user.setQq(resultSet.getString(8));
                user.setEmail(resultSet.getString(9));
                userList.add(user);
            }

            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(resultSet,preparedStatement,connection);
        }
        return null;
    }

    @Override
    public Integer findTotalCount(Map<String,String[]> map) {
        //定义sql
        //String sql = "select count(*) from user";
        StringBuilder builderSql = new StringBuilder("select count(*) from user where 1 = 1");



        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List values = new ArrayList();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            //将空的字段排除
            if (map.get(key)[0].equals("") || map.get(key)[0] == null){
                //该字段没有传递过来值
                continue;
            }
            //该字段有值
            //2.将传递过来的参数进行封装
            String[] strings = map.get(key);
            values.add(strings[0]);
            //3.拼接sql语句
            builderSql.append(" and "+key+" like ?");//select * from user where 1 = 1 and name like ?;
        }

        //4.builderSql转为String类型
        String sql = builderSql.toString();

        try {
            //获取连接对象
            connection = JDBCUtils.getConnection();
            //获取preparedStatement
            preparedStatement = connection.prepareStatement(sql);


            //给占位符赋值，遍历values集合，进行赋值
            //instanceof 可以判断该数据的数据类型
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i) instanceof String){
                    preparedStatement.setString(i+1, "%"+(String) values.get(i)+"%");
                }
                if (values.get(i) instanceof Integer){
                    preparedStatement.setInt(i+1,(Integer)values.get(i));
                }
            }

            //执行sql语句
            resultSet = preparedStatement.executeQuery();
            //处理resultSet对象
            while(resultSet.next()){
                int totalCount = resultSet.getInt(1);
                return totalCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(resultSet,preparedStatement,connection);
        }
        return null;

    }

    @Override
    public void addUser(User user) {

        //定义sql语句
        String sql= "insert into user(name,gender,age,address,qq,email) values(?,?,?,?,?,?)";
        Connection connection=null;
        PreparedStatement preparedStatement = null;
        int i=0;
        try {
            connection = JDBCUtils.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getGender());
            preparedStatement.setInt(3,user.getAge());
            preparedStatement.setString(4,user.getAddress());
            preparedStatement.setString(5,user.getQq());
            preparedStatement.setString(6,user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.close(null,preparedStatement,connection);
        }


    }

    @Override
    public void update(String id) {

    }


}
