package models;

import java.util.Objects;

public class UserAddress {
    String street;
    String suite;
    String city;
    String zipcode;
    UserAddressGeo geo;

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
}
