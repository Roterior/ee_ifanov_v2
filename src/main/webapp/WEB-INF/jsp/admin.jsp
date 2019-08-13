<%@ page import="com.accenture.flowershop.fe.enums.PurchaseStatus" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Flower Shop</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
</head>
<body style="background-color: #999">
    <div class="bg-dark">
        <div class="container d-flex justify-content-between align-items-lg-center">
            <div class="d-flex justify-content-between align-items-lg-center">
                <span><i class="fas fa-seedling" style='font-size:38px;color:green'></i></span>
                <span class="text-white">Flower Shop</span>
            </div>
            <div class="text-white">
                <form class="d-inline-block" action="logout" method="post">
                    <input class="btn btn-success btn-sm" type="submit" value="Logout">
                </form>
            </div>
        </div>
    </div>
    <div class="container pt-3 w-50">
        <table class="table table-sm pt-2">
            <thead class="thead-dark">
            <tr>
                <th>Login</th>
                <th>Summary</th>
                <th>Created</th>
                <th>Closed</th>
                <th>Status</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items='${purchaseListAll}' >
                    <tr class="font-weight-bold">
                        <td><kbd>${item.client.login}</kbd></td>
                        <td><kbd>${item.totalPrice}</kbd></td>
                        <td><kbd>${item.createDate}</kbd></td>
                        <td><kbd>${item.closeDate}</kbd></td>
                        <td><kbd>${item.status}</kbd></td>
                        <c:if test="${item.getStatus() eq PurchaseStatus.PAID}">
                            <td>
                                <form action="admin" method="get">
                                    <input type="hidden" name="id" value="${item.id}">
                                    <input class="btn btn-success btn-sm btn-outline-dark" type="submit" name="act" value="close">
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>