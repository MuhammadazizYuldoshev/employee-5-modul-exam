<%--
  Created by IntelliJ IDEA.
  User: Muhammadaziz
  Date: 9/20/2022
  Time: 9:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UPDATE EMPLOYEE</title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.14.0-beta2/css/bootstrap-select.min.css">

    <%--    SCRIPTS     --%>
    <script src="/webjars/jquery/3.6.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.14.0-beta2/js/bootstrap-select.min.js"></script>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>

<div class="container text-center">

    <h1>UPDATE EMPLOYEE</h1>

    <c:if test="${employee != null}">
    <div class="row">
        <div class="col-md-6 offset-3">
            <form action="/employees/update-form" method="post">

                <input type="hidden" name="id">
                <div class="mb-3">
                    <label for="name" class="form-label">NAME</label>
                    <input name="name" type="text" class="form-control" id="name" value="${employee.name}">
                </div>
                <div class="mb-3">
                    <label for="lastname" class="form-label">LAST NAME</label>
                    <input name="lastname" type="text" class="form-control" id="lastname" value="${employee.lastname}">
                </div>
                <div class="mb-3">
                    <label for="salary" class="form-label">SALARY</label>
                    <input name="salary" type="number" class="form-control" id="salary" value="${employee.salary}">
                </div>
                <div class=form-group" >

                    <label for="position_id" class="form-label">POSITION</label>
                    <select
                            required
                            name="position_id"
                            id="position_id"
                            class="form-select"
                            aria-label="Default select example">
                        <option value="0" >${employee.position.name}</option>
                        <c:forEach items="${position}" var="val">
                            <option value="${val.id}">${val.name}</option>
                        </c:forEach>
                    </select>

                </div>

                <button type="submit" class="btn btn-primary">UPDATE</button>
            </form>
        </div>
    </div>
</div>
</c:if>

</body>
</html>
