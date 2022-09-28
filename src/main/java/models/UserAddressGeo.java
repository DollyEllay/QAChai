package models;

import java.util.Objects;

public class UserAddressGeo {
    String lat;
    String lng;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserAddressGeo otherUserAddressGeo = (UserAddressGeo) obj;
        return lat.equals(otherUserAddressGeo.lat) && lng.equals(otherUserAddressGeo.lng);
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
}
