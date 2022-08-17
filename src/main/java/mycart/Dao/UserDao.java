package mycart.Dao;

import mycart.Model.Balance;
import mycart.Model.ContactUs;
import mycart.Model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDao {
    private SessionFactory factory;

    //we need to give session factory to dao
    public UserDao(SessionFactory factory){
        this.factory = factory;
    }

    //get user by email and password
    public User getUserByEmailAndPassword(String email, String password){
        User user=null;
        try{
            String query="from User where userEmail=: e and userPassword=: p";
            Session session=this.factory.openSession();
            Query q=session.createQuery(query);
            q.setParameter("e",email);
            q.setParameter("p",password);
            user=(User)q.uniqueResult();  //return object if found else null
            session.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    public Balance getBalanceByUserId(int id, User user){
        Balance balance = null;
        try {
            System.out.println("here i am getting error 1");
            String query = "from Balance where user.userId=: e";
            Session session=this.factory.openSession();
            Query q= session.createQuery(query);
            q.setParameter("e",id);
            System.out.println("here i am getting error 2");
            balance = (Balance) q.uniqueResult();
            session.close();
            System.out.println("here i am getting error 3");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(balance==null){
            Balance balance1 = new Balance();
            balance1.setAvailBalance(0);
            balance1.setUser(user);
            Session session=this.factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(balance1);
            tx.commit();
            session.close();
            return balance1;
        }
        return balance;
    }

    public boolean validateUserRegistrationEmail(String userEmail) {
        String q="From User where userEmail=:e";
        Session session=this.factory.openSession();
        Query qu= session.createQuery(q);
        qu.setParameter("e",userEmail);
        if(qu.uniqueResult()==null) return true;
        return false;
    }

    public boolean validateUserRegistrationUserName(String userName) {
        String q="From User where userName=:e";
        Session session=this.factory.openSession();
        Query qu= session.createQuery(q);
        qu.setParameter("e",userName);
        if(qu.uniqueResult()==null) return true;
        return false;
    }

    public boolean authenticatePassword(String userPassword) {
        boolean capitalLetterFlag=false;
        boolean smallLetterFlag=false;
        boolean numberFlag=false;
        for(int i=0; i< userPassword.length(); i++){
            int val = userPassword.charAt(i);
            if(65<=val && val<=90){
                capitalLetterFlag=true;
            }
            if(97<=val && val<=122){
                smallLetterFlag=true;
            }
            if(48<=val && val<=57){
                numberFlag=true;
            }
        }
        if(capitalLetterFlag && smallLetterFlag && numberFlag){
            return true;
        }
        return false;
    }

    public List<ContactUs> getUserContactUs(){
        String q="From ContactUs";
        Session session=this.factory.openSession();
        Query qu= session.createQuery(q);
        List<ContactUs> contactUsList = qu.list();
        session.close();
        return contactUsList;
    }
}
