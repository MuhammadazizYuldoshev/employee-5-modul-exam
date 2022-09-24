<%--
  Created by IntelliJ IDEA.
  User: Muhammadaziz
  Date: 9/15/2022
  Time: 10:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>VIEW EMPLOYEE</title>
    <link rel="stylesheet" href="/webjars/bootstrap/5.1.3/css/bootstrap.min.css">
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</head>
<body>


<div class="container text-center">

    <h1>EMPLOYEES LIST</h1>

    <a class="btn btn-outline-primary my-4" href="/employees/add-form">ADD NEW EMPLOYEE</a>
    <div class="row">
        <div class="col-md-12">
            <table class="table">
                <thead class="table-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">ID</th>
                    <th scope="col">NAME</th>
                    <th scope="col">LAST NAME</th>
                    <th scope="col">SALARY</th>
                    <th scope="col">POSITION</th>
                    <th scope="col">COUNTRY</th>
                    <th scope="col">ACTION</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${employees}" var="employee" varStatus="loop">
                    <tr>
                        <th scope="row">${loop.index+1}</th>
                        <td>
                                ${employee.id}
                        </td>

                        <td>
                                ${employee.name}
                        </td>
                        <td>
                                ${employee.lastname}
                        </td>
                        <td>
                                ${employee.salary}
                        </td>
                        <td>
                                ${employee.position.position_name}
                        </td>
                        <td>
                                ${employee.country.country_name}
                        </td>
                        <td>
                            <a class="btn btn-warning" href="/employees/edit/${employee.id}">EDIT</a>
                            <a class="btn btn-danger" href="/employees/delete/${employee.id}">DELETE</a>
                        </td>




                    </tr>

                </c:forEach>

                </tbody>
            </table>

        </div>
    </div>

    <hr>
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <li class="page-item"><a class="btn btn-outline-primary" href="/employees/${page - 1}">Previous</a></li>

            <%--             Math.ceil(   totalElementsCount / size  )    --%>
            <c:forEach varStatus="loop" begin="1" end="${Math.ceil(count/size)}">
                <li class="page-item">
                    <a
                            class="btn btn-outline-success"
                            href="/employees/${loop.index}">
                            ${loop.index}
                    </a>
                </li>
            </c:forEach>

            <li class="page-item"><a class="btn btn-outline-primary" href="/employees/${page + 1}">Next</a></li>
        </ul>
    </nav>

</div>



</body>
</html>
