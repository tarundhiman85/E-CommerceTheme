package mycart.Controller;

import mycart.Dao.UserDao;
import mycart.Helper.FactoryProvider;
import mycart.Model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        try (PrintWriter out = response.getWriter()) {
            try {
                String userName=request.getParameter("user_name");
                String userEmail=request.getParameter("user_email");
                String userPassword=request.getParameter("user_password");
                String confirmPassword=request.getParameter("confirm_password");
                String userPhone=request.getParameter("user_phone");
                String userAddress=request.getParameter("user_address");

                HttpSession httpSession = request.getSession();
                //validation
                UserDao userDao = new UserDao(FactoryProvider.getFactory());
                if(!userDao.validateUserRegistrationEmail(userEmail)){
                    httpSession.setAttribute("message","This email is already registered with us choose another");
                    response.sendRedirect("register.jsp");
                    return;
                }
                else if(!userDao.validateUserRegistrationUserName(userName)){
                    httpSession.setAttribute("message","This UserName is already available choose another");
                    response.sendRedirect("register.jsp");
                }
                else if(!userDao.authenticatePassword(userPassword)){
                    httpSession.setAttribute("message","Please follow instruction of password");
                    response.sendRedirect("register.jsp");
                }
                else if(!confirmPassword.equals(userPassword)){
                    httpSession.setAttribute("message","Password don't match Please Retype it");
                    response.sendRedirect("register.jsp");
                }
                else {
                    //creating user object to store data
                    User user = new User(userName, userEmail, userPassword, userPhone, "default.jpg", userAddress, "normal");
                    Session hibernateSession = FactoryProvider.getFactory().openSession();
                    Transaction tx = hibernateSession.beginTransaction();

                    int userId = (int) hibernateSession.save(user);   //return userid
                    tx.commit();

                    hibernateSession.close();
                    httpSession.setAttribute("message", "Registration Successful !!" + userId);
                    response.sendRedirect("register.jsp");
                    return;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
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
