package entity;

public class Invoice {
	
	private String number;
	private double amount;
	private String currency;
	public Invoice() {}
	
	@Override
	public String toString() {
		return "Invoice [number=" + number + ", amount=" + amount + ", currency=" + currency + "]";
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Invoice(String number, double amount, String currency) {
		super();
		this.number = number;
		this.amount = amount;
		this.currency = currency;
	}
	
	

}
