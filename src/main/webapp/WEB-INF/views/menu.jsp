<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>
    <table>
        <c:forEach items="${contacts}" var="contact">
            <tr>
                <form:form method="post">
                <td><form:input name="contactId" disabled value="${contact.get(\"id\")}"/></td>
                <td><form:input name="firstName" value="${contact.get(\"firstName\")}"/></td>
                <td><form:input name="lastName" value="${contact.get(\"lastName\")}"/></td>
                <td><form:input name="phoneNumber" value="${contact.get(\"phoneNumber\")}"/></td>
                <td><form:input type="submit">SAVE</input></td>
                </form>
                <td><form:form method="post"><input type="submit" name="deleteContact" value="${contact.get(\"id\")}"/>DELETE</form></td>
            </tr>
        </c:forEach>
            <tr>
                <form:form method="post">
                <td>Add new contact: </td>
                <td><form:input name="newFirstName" placeholder="First name"/></td>
                <td><form:input name="newLastName" placeholder="Last name"/></td>
                <td><form:input name="newPhoneNumber" placeholder="Phone number"/></td>
                <td><form:input type="submit">ADD</input></td>
                </form>
                <td></td>
            </tr>
    </table>
    <table>
        <c:forEach items="${groups}" var="group">
            <tr>
                <form:form method="post">
                <td><form:input name="groupId" disabled value="${group.get(\"id\")}"/></td>
                <td><form:input name="groupName" value="${group.get(\"groupName\")}"/></td>
                <td><form:input type="submit">SAVE</input></td>
                </form>
                <td><form:form method="post"><input type="submit" name="deleteGroup" value="${group.get(\"id\")}"/>DELETE</form></td>
            </tr>
        </c:forEach>
            <tr>
                <form:form method="post">
                <td>Add new group: </td>
                <td><form:input name="newGroupName"/></td>
                <td><form:input type="submit">ADD</input></td>
                </form>
                <td></td>
            </tr>
    </table>
</body>
</html>