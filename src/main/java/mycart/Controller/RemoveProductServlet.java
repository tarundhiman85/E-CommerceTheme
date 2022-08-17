package mycart.Controller;

import mycart.Dao.ProductDao;
import mycart.Helper.FactoryProvider;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RemoveProductServlet", value = "/RemoveProductServlet")
public class RemoveProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            ProductDao productDao = new ProductDao(FactoryProvider.getFactory());
            productDao.RevertBalanceToUserId(orderId);
            productDao.removeOrderByOrderId(orderId);
            productDao.removeProductsByOrderId(orderId);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("message","Product Removed Successfully");
            response.sendRedirect("viewOrders.jsp");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request,response);
    }
}
