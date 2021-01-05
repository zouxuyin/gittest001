<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>
    <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>籍贯</th>
            <th>QQ</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>

        <c:forEach varStatus="sum" var="user" items="${userPage.list}">
            <tr>
                <td>${sum.count}</td>
                <td>${user.name}</td>
                <td>${user.gender}</td>
                <td>${user.age}</td>
                <td>${user.address}</td>
                <td>${user.qq}</td>
                <td>${user.email}</td>
                <td><a class="btn btn-default btn-sm" id="update" onclick="update(${user.id})">修改</a>&nbsp;
                    <a title="删除"  onclick="cImg_del(this,'${user.id}')" class="btn btn-default btn-sm">删除</a>
            </tr>
        </c:forEach>

        <tr>
            <td colspan="8" align="center"><a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a></td>
        </tr>
    </table>

    <%--分页--%>
    <div>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <c:if test="${userPage.currentPage == 1}">
                <li class="disabled">
                    </c:if>
                    <c:if test="${userPage.currentPage != 1}">
                <li >
                    </c:if>

                    <a href="${pageContext.request.contextPath}/findAllUser?currentPage=${userPage.currentPage-1 == 0 ? 1 : userPage.currentPage-1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <c:forEach begin="1" end="${userPage.totalPage}" step="1" var="i" varStatus="s">
                    <c:if test="${userPage.currentPage == i}">
                        <li class="active">
                    </c:if>
                    <c:if test="${userPage.currentPage != i}">
                        <li>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/findAllUser?currentPage=${i}&name=${condition.name[0]}&address=${condition.address[0]}&age=${condition.age[0]}">${i}</a>
                    </li>
                </c:forEach>

                <c:if test="${userPage.currentPage == userPage.totalPage}">
                <li class="disabled">
                    </c:if>
                    <c:if test="${userPage.currentPage != userPage.totalPage}">
                <li >
                    </c:if>
                    <a href="${pageContext.request.contextPath}/findAllUser?currentPage=${userPage.currentPage+1 >= userPage.totalPage ? userPage.totalPage : userPage.currentPage+1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>

                <li>
                    <span>总记录数为：${userPage.totalCount}</span>
                    <span>总页码为：${userPage.totalPage}</span>
                </li>
            </ul>
        </nav>
    </div>
</div>
</body>
        <script>
            function update(id) {
                var userId= id;
                var url="http://localhost:8080/updtae?id"+userId;
                window.open(url,'_self')
            }
            /*封面图-删除*/
            function cImg_del(obj,c_id){//id为取到的行id
                var r=confirm("是否确认删除？");
                if(r==true){
                    //确定执行删除
                    var id = c_id;
                    $.get("deleteById?id="+id,function(data){
                        if(data=="ok"){
                            alert("删除成功!");
                            //删除成功后，刷新页面信息
                            location.reload();
                        }else{
                            alert("删除失败！!");
                        }
                    });
                    return true;
                }else{
                    //反之取消删除
                    return false;
                }
            }

        </script>

</html>