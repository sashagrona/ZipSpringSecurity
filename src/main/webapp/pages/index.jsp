<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: olexandr
  Date: 06.04.19
  Time: 13:50
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

    <title>MyCabinet</title>
</head>
<body>
<div align="center">
    <h1>Hello dear ${log} !</h1>
    <h2>On this website your Roles is: <br></h2>
    <c:forEach items="${role}" var="r">
        <c:out value="${r.authority}"/>
    </c:forEach>
    <br>
    <br>
    <div class="custom-file">
        <c:url value="/make" var="url"/>
        <form action=${url} enctype="multipart/form-data" method="post">
            <input type="file" name="files" class="custom-file-input" id="customFile" multiple>
            <label class="custom-file-label" for="customFile">Choose files</label>
            <br>
            <br>
            <input type="submit" class="btn btn-success">
        </form>
        <br>

        <br>
        <br>
        <h3><p align="center"><b>My Archives</b></p></h3>
        <table align="center" class="table" >
            <thead>
            <tr>
                <th></th>
                <th>Name of Zip</th>
                <th>Size in kB</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${zips}" var="z">
                <tr>
                        <td><img src="/static/zip.png" class="img-circle" width="40" height="40"/></td>
                    <td><a href="/get?name=${z.name}"> <c:out value="${z.name}"/></a></td>
                    <td><c:out value="${z.size}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <p><a href="/logout" class="btn btn-primary">Log Out</a></p>
        <c:if test="${admin}">
            <p><b><a href="/admin" class="btn btn-danger">Admin page</a></b></p>
        </c:if>
    </div>
</div>


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
