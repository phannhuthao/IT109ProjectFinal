package ra.edu.business.entity;

import java.util.Date;

public class Reader {
    private int id;
    private String username;
    private boolean gender;
    private Date birthdate;
    private String phone;
    private String email;

    public Reader() {}
    public Reader(int id, String username, boolean gender,Date birthdate, String phone, String email ) {
        this.id = id;
        this.username = username;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setSex(boolean gender) {
        this.gender = gender;
    }
    public boolean getSex() {
        return gender;
    }
    public Date getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}