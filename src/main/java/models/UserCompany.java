package models;

import java.util.Objects;

public class UserCompany {
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
}
