<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Flower Shop - Registration</title>
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
        <form action="register" class="container text-center pt-5 w-50" method="post">
            <div class="row">
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="login">Login:</label>
                    <input onkeyup="checkClientLogin();" type="text" class="form-control" id="login" name="login" required>
                    <span class="h5 text-danger" id="main"></span>
                </div>
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="password">Password:</label>
                    <input type="password" class="form-control" id="password" name="password">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="fname">First name*:</label>
                    <input type="text" class="form-control" id="fname" name="fname">
                </div>
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="lname">Last name*:</label>
                    <input type="text" class="form-control" id="lname" name="lname">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="mname">Middle name*:</label>
                    <input type="text" class="form-control" id="mname" name="mname">
                </div>
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="phonenumber">Phone number*:</label>
                    <input type="number" class="form-control" id="phonenumber" name="phonenumber">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm">
                    <label class="h4 font-weight-bold" for="address">Address*:</label>
                    <input type="text" class="form-control" id="address" name="address">
                </div>
            </div>
            <a href="login" class="btn btn-dark">Back</a>
            <input id="btnReg" type="submit" class="btn btn-dark" value="Create"/>
            <div><kbd>* - Fields with sign are optional</kbd></div>
            <c:if test="${error2 ne null}">
                <div><kbd class="text-danger">${error2}</kbd></div>
            </c:if>
        </form>
    </div>
</body>
<script>
    function checkClientLogin() {
        var url = "http://localhost:8080/rest/getClient/";
        var login = document.getElementById("login").value;
        url += login;
        var xhr = new XMLHttpRequest();
        xhr.open("GET", url, false);
        xhr.send();
        var res = JSON.parse(xhr.responseText);
        var text = document.getElementById("main");
        if (res === true) {
            text.innerHTML = "This login is already exist!";
        }
        else {
            text.innerHTML = "";
        }
    }
</script>
</html>