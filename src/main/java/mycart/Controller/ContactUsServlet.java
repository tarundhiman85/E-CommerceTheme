package mycart.Controller;

import mycart.Helper.FactoryProvider;
import mycart.Model.ContactUs;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ContactUsServlet", value = "/ContactUsServlet")
public class ContactUsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");
        ContactUs contactUs = new ContactUs(name, email, message);
        Session session = FactoryProvider.factory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(contactUs);
        tx.commit();
        session.close();
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("message", "Thank you for contacting us. We will get back to you soon.");
        response.sendRedirect("contactUs.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
