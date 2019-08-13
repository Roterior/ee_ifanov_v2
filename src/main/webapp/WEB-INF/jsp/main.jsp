<%@ page import="com.accenture.flowershop.fe.enums.PurchaseStatus" %>
<%@ page import="com.accenture.flowershop.fe.enums.BasketItemStatus" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
                <span>Login: <kbd>${client.login}</kbd></span>
                <span>Balance: <kbd>
                    ${client.balance}$
                </kbd></span>
                <span>Discount: <kbd>${client.discount}%</kbd></span>
                <form class="d-inline-block" action="logout" method="post">
                    <input class="btn btn-success btn-sm" type="submit" value="Logout">
                </form>
            </div>
        </div>
    </div>
    <div class="container pt-4 w-50">
        <div class=" pb-1 pt-1 mb-2">
            <form class="m-1" action="/" method="get">
                <div class="d-flex justify-content-between align-items-lg-center">
                    <span><kbd class="">Search</kbd></span>
                    <div class="">
                        <input class="form-control form-control-sm" type="text" name="name" id="name" value="${name}" placeholder="Search...">
                    </div>
                    <span><kbd>From</kbd></span>
                    <div class="">
                        <input class="form-control form-control-sm" type="number" name="from" id="from" value="${from}" min="0" placeholder="From Price...">
                    </div>
                    <span><kbd>To</kbd></span>
                    <div class="">
                        <input class="form-control form-control-sm" type="number" name="to" id="to" value="${to}" min="0" placeholder="To Price...">
                    </div>
                    <div class="">
                        <input class="btn btn-success btn-sm btn-outline-dark d-inline-block" type="submit" name="act" value="Search">
                    </div>
                </div>
            </form>
        </div>
        <div>
            <table class="table table-sm">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Price</th>
                        <th scope="col">Quantity</th>
                        <th>To Buy</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${flowersList}">
                        <tr class="font-weight-bold">
                            <td><kbd>${item.name}</kbd></td>
                            <td><kbd>${item.price}</kbd></td>
                            <td><kbd>${item.quantity}</kbd></td>
                            <form action="" method="get">
                                <input type="hidden" name="id" value="${item.id}">
                                <td><kbd><input class="border-0" style="width: 40px;" type="number" name="quantityToBuy" value="1" min="1" max="${item.quantity}"></kbd></td>
                                <td><input class="btn btn-success btn-sm btn-outline-dark" type="submit" name="act" value="+"></td>
                            </form>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${basketItemList ne null}">
            <h5>Basket Table</h5>
            <table class="table table-sm">
                <thead class="thead-dark">
                    <tr>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Sum</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${basketItemList}">
<%--                        <c:if test="${item.status eq BasketItemStatus.WAITING}">--%>
                        <tr class="font-weight-bold">
                            <td><kbd>${item.flower.name}</kbd></td>
                            <td><kbd>${item.flower.price}</kbd></td>
                            <td><kbd>${item.quantityToBuy}</kbd></td>
                            <td><kbd>${item.sum}</kbd></td>
                            <td>
                                <form action="" method="get">
                                    <input type="hidden" name="id" value="${item.id}">
                                    <input class="btn btn-danger btn-sm btn-outline-dark" type="submit" name="act" value="x">
                                </form>
                            </td>
                        </tr>
<%--                        </c:if>--%>
                    </c:forEach>
                </tbody>
            </table>
            <form class="float-right mb-2" action="" method="get">
                <input class="btn btn-success btn-sm btn-outline-dark" type="submit" name="act" value="Order">
                <span><kbd>Sum: ${sum}</kbd></span>
            </form>
        </c:if>
        <c:if test="${purchaseList ne null}">
            <h5>Orders Table</h5>
            <table class="table table-sm pt-2">
                <thead class="thead-dark">
                    <tr>
                        <th scope="col">Summary</th>
                        <th scope="col">Created</th>
                        <th scope="col">Closed</th>
                        <th scope="col">Status</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${purchaseList}">
                        <tr class="font-weight-bold">
                            <td><kbd>${item.totalPrice}</kbd></td>
                            <td><kbd>${item.createDate}</kbd></td>
                            <td><kbd>${item.closeDate}</kbd></td>
                            <td><kbd>${item.status}</kbd></td>
                            <c:if test="${item.getStatus() eq PurchaseStatus.CREATED}">
                                <td>
                                    <form action="" method="get">
                                        <input type="hidden" name="id" value="${item.id}">
                                        <input class="btn btn-success btn-sm btn-outline-dark" type="submit" name="act" value="pay">
                                    </form>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>