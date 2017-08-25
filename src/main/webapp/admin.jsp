<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <%
        int totalUsers = 0;
        Map<String, int> contactsCountByUser = new HashMap<>();
        Map<String, int> groupsCountByUser = new HashMap<>();
        int averageContactsPerGroup = 0;
        int averageContactsPerUser = 0;
        int inactiveUsersCount = 0;

        String totalUsersQuery = "SELECT \"UserCount\"()";
        String contactsCountByUserQuery = "SELECT \"ContactsCountByUser\"(?)";
        String groupsCountByUserQuery = "SELECT \"GroupsCountByUser\"(?)";
        String averageContactsPerGroupQuery = "SELECT \"AverageContactsPerGroup\"()";
        String averageContactsPerUserQuery = "SELECT \"AverageContactsPerUser\"()";
        String inactiveUsersCountQuery = "SELECT \"InactiveUsersCount\"()";

        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:comp/env/jdbc/postgres");
            Connection connection = ds.getConnection();
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            preparedStatement = connection.prepareStatement(totalUsersQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            totalUsers = resultSet.getInt(1);

            preparedStatement = connection.prepareStatement(averageContactsPerGroupQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            averageContactsPerGroup = resultSet.getInt(1);

            preparedStatement = connection.prepareStatement(averageContactsPerUserQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            averageContactsPerUser = resultSet.getInt(1);

            preparedStatement = connection.prepareStatement(inactiveUsersCountQuery);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            inactiveUsersCount = resultSet.getInt(1);

            for (User user : View.getController().getUserController().getAll()) {
                preparedStatement = connection.prepareStatement(contactsCountByUserQuery);
                preparedStatement.setInt(1, user.get("id"));
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                contactsCountByUser.put(user.get("id"), resultSet.getInt(1));

                preparedStatement = connection.prepareStatement(groupsCountByUserQuery);
                preparedStatement.setInt(1, user.get("id"));
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                groupsCountByUser.put(user.get("id"), resultSet.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>
    Total user count: <%=totalUsers%> </br>
    Average contacts per group = <%=averageContactsPerGroup%> </br>
    Average contacts per user = <%=averageContactsPerUser%> </br>
    Inactive users: <%=inactiveUsersCount%> </br>

    <c:forEach items="${contactsCountByUser.entrySet()}" var="entry">
        ${entry.getKey()}'s contacts count: ${entry.getValue()} </br>
    </c:forEach>

    <c:forEach items="${groupsCountByUser.entrySet()}" var="entry">
        ${entry.getKey()}'s groups count: ${entry.getValue()} </br>
    </c:forEach>
</body>
</html>