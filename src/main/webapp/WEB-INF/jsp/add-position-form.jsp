<%--
  Created by IntelliJ IDEA.
  User: Muhammadaziz
  Date: 9/15/2022
  Time: 4:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>

<div class="container text-center">

    <h1>ADD NEW POSITION</h1>

    <div class="row">
        <div class="col-md-6 offset-3">
            <form action="/position" method="post">

                <div class="mb-3">
                    <label for="name" class="form-label">NAME</label>
                    <input name="name" type="text" class="form-control" id="name">
                </div>

                <div class="mb-3">
                    <label for="description">DESCRIPTION</label>
                    <textarea class="form-control" id="description" name="description" rows="5" cols="25"></textarea>
                </div>

                <button type="submit" class="btn btn-primary">SAVE</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>
