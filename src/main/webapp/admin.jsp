<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <%
        AdminController controller = View.getController().getAdminController();
        for (User users ) {
            preparedStatement.setInt(1, user.get("id"));
            groupsCountByUser.put(user.get("id"), resultSet.getInt(1));
        }
    %>
    Total user count: <%=controller.totalUsers()%> </br>
    Average contacts per group = <%=controller.averageContactsPerGroup()%> </br>
    Average contacts per user = <%=controller.averageContactsPerUser()%> </br>
    Inactive users: <%=controller.inactiveUsersCount()%> </br>
    <c:forEach items="${View.getController().getUserController().getAll()}" var="user">
        Contacts count for ${user.get("login")}: ${controller.contactsCountByUser()} </br>
    </c:forEach>
    <c:forEach items="${View.getController().getUserController().getAll()}" var="user">
        Groups count for ${user.get("login")}: ${controller.groupsCountByUser()} </br>
    </c:forEach>
</body>
</html>