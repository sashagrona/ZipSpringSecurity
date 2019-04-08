<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olexandr
  Date: 07.04.19
  Time: 15:49
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

    <title>Files</title>

</head>
<body>
<table align="center" class="table">
    <h1>There are files of ${name}</h1>
    <thead>
    <tr>
        <th></th>
        <th>Name of File</th>
        <th>Size in kB</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${files}" var="f">
        <tr>
            <td><img src="/static/file.png" class="img-circle" width="40" height="40"/></td>
            <td><a href="/get_f?name=${f.name}"> <c:out value="${f.name}"/></a></td>
            <td><c:out value="${f.size}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="/" class="btn btn-primary">Come To My Cabinet</a>
<a href="/logout" class="btn btn-primary">Log Out</a> </p>


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
