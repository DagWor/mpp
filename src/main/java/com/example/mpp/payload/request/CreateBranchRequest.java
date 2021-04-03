package com.example.mpp.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CreateBranchRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    private String password;

    private String branchName;


    //    private Set<String> roles;
    @NotBlank
    private String  firstName;

    private String lastName;

    private int ssn;

    private String street;

    private String city;

    private String postalCode;

    private int zipCode;

    private String country;


    private String  adminFirstName;


    private int adminSsn;

    private String adminStreet;

    private String adminCity;

    private String adminPostalCode;

    private int adminZipCode;

    private String adminCountry;





    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getAdminFirstName() {
        return adminFirstName;
    }

    public void setAdminFirstName(String adminFirstName) {
        this.adminFirstName = adminFirstName;
    }

    public int getAdminSsn() {
        return adminSsn;
    }

    public void setAdminSsn(int adminSsn) {
        this.adminSsn = adminSsn;
    }

    public String getAdminStreet() {
        return adminStreet;
    }

    public void setAdminStreet(String adminStreet) {
        this.adminStreet = adminStreet;
    }

    public String getAdminCity() {
        return adminCity;
    }

    public void setAdminCity(String adminCity) {
        this.adminCity = adminCity;
    }

    public String getAdminPostalCode() {
        return adminPostalCode;
    }

    public void setAdminPostalCode(String adminPostalCode) {
        this.adminPostalCode = adminPostalCode;
    }

    public int getAdminZipCode() {
        return adminZipCode;
    }

    public void setAdminZipCode(int adminZipCode) {
        this.adminZipCode = adminZipCode;
    }

    public String getAdminCountry() {
        return adminCountry;
    }

    public void setAdminCountry(String adminCountry) {
        this.adminCountry = adminCountry;
    }
}
