package models;

import com.google.gson.Gson;

import java.util.Objects;

public class User {
    private static final Gson gson = new Gson();

    private Integer id;
    private String name;
    private String username;
    private String email;
    private UserAddress address;
    private String phone;
    private String website;
    private UserCompany company;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserAddress getAddress() {
        return address;
    }

    public void setAddress(UserAddress address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public UserCompany getCompany() {
        return company;
    }

    public void setCompany(UserCompany company) {
        this.company = company;
    }

    public class UserAddress {
        String street;
        String suite;
        String city;
        String zipcode;
        AddressGeo geo;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            UserAddress otherAddress = (UserAddress) obj;
            return street.equals(otherAddress.street) &&
                    suite.equals(otherAddress.suite) &&
                    city.equals(otherAddress.city) &&
                    zipcode.equals(otherAddress.zipcode) &&
                    geo.equals(otherAddress.geo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(street, suite, city, zipcode, geo);
        }

        @Override
        public String toString() {
            return "UserAddress{" +
                    "street='" + street + '\'' +
                    ", suite='" + suite + '\'' +
                    ", city='" + city + '\'' +
                    ", zipcode='" + zipcode + '\'' +
                    ", geo=" + geo +
                    '}';
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getSuite() {
            return suite;
        }

        public void setSuite(String suite) {
            this.suite = suite;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public void setZipcode(String zipcode) {
            this.zipcode = zipcode;
        }

        public AddressGeo getGeo() {
            return geo;
        }

        public void setGeo(AddressGeo geo) {
            this.geo = geo;
        }
    }

    public class AddressGeo {
        String lat;
        String lng;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            AddressGeo otherAddressGeo = (AddressGeo) obj;
            return lat.equals(otherAddressGeo.lat) && lng.equals(otherAddressGeo.lng);
        }

        @Override
        public int hashCode() {
            return Objects.hash(lat, lng);
        }

        @Override
        public String toString() {
            return "AddressGeo{" +
                    "lat='" + lat + '\'' +
                    ", lng='" + lng + '\'' +
                    '}';
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }
    }

    private class UserCompany {
        String name;
        String catchPhrase;
        String bs;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            UserCompany otherUserCompany = (UserCompany) obj;
            return name.equals(otherUserCompany.name) &&
                    catchPhrase.equals(otherUserCompany.catchPhrase) &&
                    bs.equals(otherUserCompany.bs);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, catchPhrase, bs);
        }

        @Override
        public String toString() {
            return "UserCompany{" +
                    "name='" + name + '\'' +
                    ", catchPhrase='" + catchPhrase + '\'' +
                    ", bs='" + bs + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public void setCatchPhrase(String catchPhrase) {
            this.catchPhrase = catchPhrase;
        }

        public String getBs() {
            return bs;
        }

        public void setBs(String bs) {
            this.bs = bs;
        }
    }
}
