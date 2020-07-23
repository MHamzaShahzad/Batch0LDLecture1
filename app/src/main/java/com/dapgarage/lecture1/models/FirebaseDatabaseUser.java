package com.dapgarage.lecture1.models;

public class FirebaseDatabaseUser {

    String firstName, lastName, email, cnic, phoneNumber, address, profileImage;

    public FirebaseDatabaseUser() {
    }

    public FirebaseDatabaseUser(String firstName, String lastName, String email, String cnic, String phoneNumber, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cnic = cnic;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCnic() {
        return cnic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
