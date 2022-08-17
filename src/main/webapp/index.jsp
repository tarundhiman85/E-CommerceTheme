<%@ page import="mycart.Dao.ProductDao" %>
<%@ page import="mycart.Helper.FactoryProvider" %>
<%@ page import="mycart.Model.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="mycart.Dao.CategoryDao" %>
<%@ page import="mycart.Model.Category" %>
<%@ page import="mycart.Helper.Helper" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>MyCart</title>
    <%@include file="common_css_js.jsp"%>
</head>
<body>
<%@include file="navbar.jsp"%>

<div class="container text-center">
    <h1 style="color: black">Welcome <span class="animate"></span></h1>
</div>
    <%
        String cat = request.getParameter("category");
        ProductDao dao = new ProductDao(FactoryProvider.getFactory());
        List<Product> list = null;
        if (cat==null)
        {
            list = dao.getAllProducts();
        }
             else if (cat.trim().equals("all"))
             {
                list = dao.getAllProducts();
            }
             else {
                int cid = Integer.parseInt(cat.trim());
                list = dao.getAllProductsById(cid);
            }
        CategoryDao cdao=new CategoryDao(FactoryProvider.getFactory());
        List<Category> clist = cdao.getCategories();
    %>
<div class="row">
    <div class="text-left mt-4 col-2" style="margin-left: 1rem">
        <div class="list-group">
            <a href="index.jsp?category=all" class="list-group-item list-group-item-action active">
                All Products
            </a>
            <%
                for(Category c:clist){
            %>
            <a href="index.jsp?category=<%=c.getCategoryId()%>" class="list-group-item list-group-item-action"><%=c.getCategoryTitle()%></a>
            <%
                }
            %>
        </div>
    </div>
    <div class="container1 col-9">
        <%
            for (Product p:list){
        %>
        <div class="item1">
            <img src="img/products/<%=p.getpPhoto()%>" style="max-height: 200px;width: auto;" class="card-img-top m-3 image-resize" alt="">
            <div class="divC">
                <h5>
                    <%=p.getpName()%>
                </h5>
                <p>
                    <%=Helper.get10Words(p.getpDesc())%>
                </p>
            </div>
                <button class="btn custom-bg text-white btnC" onclick="add_to_cart(<%=p.getPid()%>,'<%=p.getpName()%>',<%=p.getPriceAfterApplyingDiscount()%>)">Add to Cart</button>
                <button class="btn btn-outline-success btnC">&#8377;<%=p.getPriceAfterApplyingDiscount()%>/- <span>&#8377;<%=p.getpPrice()%> <%=p.getpDiscount()%>%off</span></button>
        </div>
        <%
            }
            if(list.size()==0){
                out.println("<h3>No items in this Category</h3>");
            }
        %>
    </div>
</div>
<%@include file="common_modals.jsp"%>
</body>
</html>