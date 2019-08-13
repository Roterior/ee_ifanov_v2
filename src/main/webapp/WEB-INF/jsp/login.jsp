<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Flower Shop - Login</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel='stylesheet' href='https://use.fontawesome.com/releases/v5.7.0/css/all.css' integrity='sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ' crossorigin='anonymous'>
</head>
<body style="background-color: #999; height: 100vh">
    <div class="bg-dark">
        <div class="container d-flex justify-content-between align-items-lg-center">
            <div class="d-flex justify-content-between align-items-lg-center">
                <span><i class="fas fa-seedling" style='font-size:38px;color:green'></i></span>
                <span class="text-white">Flower Shop</span>
            </div>
        </div>
    </div>
    <div>
        <form class="container text-center pt-5 w-25" action="login" method="get">
            <div class="form-group">
                <label class="h5 font-weight-bold" for="login">Login:</label>
                <input class="form-control" type="text" name="login" id="login">
            </div>
            <div class="form-group">
                <label class="h5 font-weight-bold" for="password">Password:</label>
                <input class="form-control" type="password" name="password" id="password">
            </div>
            <input class="btn btn-dark" type="submit" name="act" value="Login">
            <input class="btn btn-dark" type="button" name="act" value="Register" onclick="location.href='/register'">
            <c:if test="${error ne null}">
                <div><kbd class="text-danger">${error}</kbd></div>
            </c:if>
        </form>
    </div>
</body>
</html>