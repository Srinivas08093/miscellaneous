package com.IntrestCaliculater;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Appu {
	private double principleAmount;
	private double intrestRate;
	private Date dateOfMoneyTaken;
	private String personName;
	private double caliculatedIntrest;
	private double totalAmount;

	
	public String getDateOfMoneTakenInString() {
		
		return dateOfMoneyTaken ==null?"no date":new SimpleDateFormat("MM/dd/yyyy").format(dateOfMoneyTaken);
	}

	public double getCaliculatedIntrest() {
		return caliculatedIntrest;
	}

	public void setCaliculatedIntrest(double caliculatedIntrest) {
		this.caliculatedIntrest = caliculatedIntrest;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getPrincipleAmount() {
		return principleAmount;
	}

	public void setPrincipleAmount(double principleAmount) {
		this.principleAmount = principleAmount;
	}

	public double getIntrestRate() {
		return intrestRate;
	}

	public void setIntrestRate(double intrestRate) {
		this.intrestRate = intrestRate;
	}

	public Date getDateOfMoneyTaken() {
		return dateOfMoneyTaken;
	}

	public void setDateOfMoneyTaken(Date dateOfMoneyTaken) {
		this.dateOfMoneyTaken = dateOfMoneyTaken;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Override
	public String toString() {
		return "Appu [principleAmount=" + principleAmount + ", intrestRate=" + intrestRate + ", dateOfMoneyTaken="
				+ dateOfMoneyTaken + ", personName=" + personName + ", caliculatedIntrest=" + caliculatedIntrest
				+ ", totalAmount=" + totalAmount + "]";
	}

}
