package models;

import java.util.Objects;

public class User {

    public Integer id;
    public String name;
    public String username;
    public String email;
    public UserAddress address;
    public String phone;
    public String website;
    public UserCompany company;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email, phone, website, address, company);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User otherUser = (User) obj;
        return id.equals(otherUser.id) &&
                name.equals(otherUser.name) &&
                username.equals(otherUser.username) &&
                email.equals(otherUser.email) &&
                address.equals(otherUser.address) &&
                phone.equals(otherUser.phone) &&
                website.equals(otherUser.website) &&
                company.equals(otherUser.company);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", company=" + company +
                '}';
    }
}
