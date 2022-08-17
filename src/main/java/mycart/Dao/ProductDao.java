package mycart.Dao;

import mycart.Model.Balance;
import mycart.Model.Orders;
import mycart.Model.Product;
import mycart.Model.UserOrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductDao {

    private SessionFactory factory;

    public ProductDao(SessionFactory factory) {
        this.factory = factory;
    }

    //save product to db
    public boolean saveProduct(Product product){
        boolean f=false;
        try {
            Session session = this.factory.openSession();
            Transaction tx = session.beginTransaction();
            session.save(product);
            tx.commit();
            session.close();
            f=true;
        }
        catch (Exception e){
            e.printStackTrace();
            //f=false;
        }
        return f;
    }
    //get all products
    public List<Product> getAllProducts(){
        Session s = this.factory.openSession();
        Query query = s.createQuery("FROM Product ");
        List<Product> list=query.list();
        return list;

    }  //get all products of given category

    public List<Product> getAllProductsById(int cid){
        Session s = this.factory.openSession();
        Query query = s.createQuery("FROM Product as p where p.category.categoryId=:id");
        query.setParameter("id",cid);
        List<Product> list=query.list();
        return list;
    }

    public List<UserOrder> getAllOrderedProducts(int id) {
        List<Orders> list1 = getALLOrdersByUserId(id);
        List<UserOrder> list = new ArrayList<>();
        for( Orders orders : list1){
            int orderId=orders.getOrderId();
            String query = "from UserOrder where order.orderId=: e";
            Session session=this.factory.openSession();
            Query q= session.createQuery(query);
            q.setParameter("e",orderId);
            List<UserOrder> temp = q.list();
            list.addAll(temp);
            session.close();
        }
        return list;
    }

    public List<Orders> getALLOrdersByUserId(int id) {
        List<Orders> list1 = null;
        try{
            String query = "from Orders where user.userId=: e";
            Session session=this.factory.openSession();
            Query q= session.createQuery(query);
            q.setParameter("e",id);
            list1 = q.list();
            session.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list1;
    }

    public void removeOrderByOrderId(int orderId) {
        Session session=this.factory.openSession();
        Transaction tx = session.beginTransaction();
        Orders orders = session.get(Orders.class, orderId);
        session.delete(orders);
        tx.commit();
        session.close();
    }

    public void removeProductsByOrderId(int orderId) {
        String s = "Delete from UserOrder where order.orderId=:e";
        Session session=this.factory.openSession();
        Transaction tx=session.beginTransaction();
        Query q= session.createQuery(s);
        q.setParameter("e",orderId);
        q.executeUpdate();
        tx.commit();
        session.close();
    }

    public void RevertBalanceToUserId(int orderId) {

        Session session = factory.openSession();
        Orders orders = session.get(Orders.class, orderId);
        int balanceToRevert = orders.getTotalPrice();
        String q = "from Balance where user.userId=:e";
        Query query = session.createQuery(q);
        query.setParameter("e",orders.getUser().getUserId());
        Balance balance  = (Balance)query.list().get(0);
        balance.setAvailBalance(balance.getAvailBalance()+balanceToRevert);
        Transaction tx=session.beginTransaction();
        session.update(balance);
        tx.commit();
        session.close();
    }
}
