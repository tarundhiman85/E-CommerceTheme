package mycart.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;
    private String name;
    private String email;
    private String message;
    private Date date;
    @PrePersist
    protected void onCreate() {
        date = new Date();
    }
public ContactUs(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }
    public ContactUs() {
    }
    public int getContactId() {
        return contactId;
    }
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
