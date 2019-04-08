<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olexandr
  Date: 06.04.19
  Time: 18:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
    <title>Admin</title>
</head>
<body>
<div>
    <h1><b>Welcome to Admin page</b></h1>
    <h3>Here you are able to see files of all users</h3>
    <h4>and all users as well</h4>
    <br>
    <h3>If you want to see files, just click on the name of user</h3>
<form action="/delete" method="post">
    <table align="center" class="table">
        <thead>
        <tr>
            <th></th>
            <th>ID</th>
            <th>Login</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="u">
            <tr>
                <td><input type="checkbox" name="toDelete" value="${u.id}"></td>
                <td><c:out value="${u.id}"/></td>
                <td><a href="/get_files?name=${u.login}"> <c:out value="${u.login}"/></a></td>
                <td><c:out value="${u.role}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button type="button" class="btn btn-primary" id="add_user">Add</button>
    <button type="submit" class="btn btn-primary">Delete</button>
    <button type="button" id="back" class="btn btn-primary">Back to my Cabinet</button>
    <button type="button" id="logout" class="btn btn-primary">Log Out</button>
</form>
</div>
<script>
    $('#add_user').click(function () {
        window.location.href="/register";
    });

    $('#back').click(function () {
        window.location.href="/";
    });

    $('#logout').click(function () {
        window.location.href="/logout";
    });

</script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
        crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>
