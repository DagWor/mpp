package com.example.mpp.models;

public class Address {
	private String street;
	private String city;
	private String postalCode;
	private int zipCode;
	private String country;

	public Address(String street, String city, String postalCode, int zipCode, String country) {
		this.street = street;
		this.city = city;
		this.postalCode = postalCode;
		this.zipCode = zipCode;
		this.country = country;
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
}
