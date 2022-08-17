<%@ page import="mycart.Model.UserOrder" %>
<%@ page import="java.util.List" %>
<%@ page import="mycart.Dao.ProductDao" %>
<%@ page import="mycart.Helper.FactoryProvider" %>
<%@ page import="mycart.Model.Orders" %>

<%
    User user=(User)session.getAttribute("current-user");
    if(user==null)
    {
        session.setAttribute("message","You are not logged in Login first");
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Your Orders</title>
    <%@include file="common_css_js.jsp"%>
</head>
<body>
<%@include file="navbar.jsp"%>
<%@include file="message.jsp"%>
<div class="row" style="margin-top: 10rem">
    <div class="col-4" style="margin-left: 10rem">
        <h3>Your Ordered Products</h3>
        <table>
            <tr class="top">
                <td>Product Name</td>
                <td>Product Price</td>
                <td>Product Quantity</td>
                <td>Order Id</td>
            </tr>
            <%
                List<UserOrder> userOrderList = new ProductDao(FactoryProvider.getFactory()).getAllOrderedProducts(user.getUserId());
                                for (UserOrder p: userOrderList){
            %>
        <%--Product Card--%>
        <div class="item1">
            <tr class="top">
                <td><%=p.getProductName()%></td>
                <td><%=p.getProductPrice()%></td>
                <td><%=p.getProductQuantity()%></td>
                <td><%=p.getOrder().getOrderId()%></td>
            </tr>
        </div>
<%}%>
    </table>
    </div>
    <div class="col-5">
        <h3>Your Orders Transactions</h3>
        <table>
            <tr class="top">
                <td>Order Id</td>
                <td>Order Price</td>
                <td>Order Date and Time</td>
                <td>Status</td>
                <td>Action</td>
            </tr>
            <%
                List<Orders> orderList = new ProductDao(FactoryProvider.getFactory()).getALLOrdersByUserId(user.getUserId());
                for (Orders p: orderList){
            %>
            <%--Product Card--%>
            <div class="item1">
                <tr class="top">
                    <td><%=p.getOrderId()%></td>
                    <td><%=p.getTotalPrice()%></td>
                    <td><%=p.getDate()%></td>
                    <td><%=p.getStatus()%></td>
                    <td>
                        <form action="RemoveProductServlet" method="post">
                            <input type="hidden" value="<%=p.getOrderId()%>" name="orderId">
                            <input class="btn btn-danger" type="submit" value="Cancel">
                        </form>
                    </td>
                </tr>
            </div>
            <%}%>
        </table>
    </div>
</div>
<%@include file="common_modals.jsp"%>
</body>
</html>
