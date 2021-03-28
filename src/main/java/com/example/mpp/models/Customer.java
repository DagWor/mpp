package com.example.mpp.models;

import java.time.LocalDate;

public class Customer {
	private LocalDate createdDate;
	private double currentBalance;

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public void fundTransfer(double amount) {
		currentBalance-= amount;
	}
}
