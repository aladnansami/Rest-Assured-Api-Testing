package model;

public class CreateUserModel {
    String name;
    String phone_number;
    String email;
    String password;
    String nid;
    String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone_number;
    }

    public void setPhone(String phone) {
        this.phone_number = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public CreateUserModel(String name,String email,String password,String phone,String nid,String role){
        this.name=name;
        this.phone_number=phone;
        this.email=email;
        this.password=password;
        this.nid=nid;
        this.role=role;

    }
}
