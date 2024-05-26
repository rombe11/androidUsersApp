package com.example.usersapp.models;

public class RandomUser {
    private String gender;
    private Name name;
    private Location location;
    private String email;
    private String phone;
    private String cell;
    private Dob dob;
    private Picture picture;


    public Picture getPicture() {
        return picture;
    }
    public String getGender() {
        return gender;
    }

    public Name getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCell() {
        return cell;
    }

    public Dob getDob() {
        return dob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RandomUser that = (RandomUser) o;

        if (!gender.equals(that.gender)) return false;
        if (!name.equals(that.name)) return false;
        if (!location.equals(that.location)) return false;
        if (!email.equals(that.email)) return false;
        if (!phone.equals(that.phone)) return false;
        if (!cell.equals(that.cell)) return false;
        if (!dob.equals(that.dob)) return false;

        return true;
    }


}
