package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class CreditCard implements Serializable{

	@Id
	@XmlID
	private String cardNumber;
	private Date dueDate;
	private double balance;
	private double limit;
	private CardType cardType;
	private Status status;
	
	@XmlIDREF
	@ManyToOne
	private User owner;
	
	private static final double DEFAULT_BALANCE = 10000;
	private static final double DEFAULT_LIMIT = 24000;
	
	public enum Status{ACTIVE,EXPIRED}
	
	public CreditCard() {
		
	}
	
	public CreditCard(String number, Date dueDate, double balance, double limit) {
		this.cardNumber = number;
		this.dueDate = dueDate;
		this.balance = balance;
		this.limit = limit;
		cardType = CardType.detect(number);
		refreshStatus();
	}
	
	public CreditCard(String number, Date dueDate) {
		this.cardNumber = number;
		this.dueDate = dueDate;
		this.balance = DEFAULT_BALANCE;
		this.limit = DEFAULT_LIMIT;
		cardType = CardType.detect(number);
		refreshStatus();
	}
		
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public double getLimit() {
		return limit;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public User getOwner() {
		return owner;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public boolean charge(Double amount) {
		if(amount > 0) {
			this.balance += amount;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean pay(Double amount) {
		if(amount > 0 && amount <= this.balance) {
			this.balance -= amount;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void refreshStatus() {
		if(Calendar.getInstance().getTime().compareTo(dueDate) > 0) {
			status = Status.EXPIRED;
		}
		else {
			status = Status.ACTIVE;
		}
	}
	
	public String toString() {
		SimpleDateFormat df = new SimpleDateFormat("MM/yyyy");
		return("Number: " + cardNumber + " Type: " + cardType.toString() + " Due date: " + df.format(dueDate)  + " Status: " + status.toString() + " Balance: " + balance + " Limit: " + limit);
	}
	
}
