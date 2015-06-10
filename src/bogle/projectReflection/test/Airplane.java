package bogle.projectReflection.test;

public class Airplane {

	private String airliner;
	private String model;
	private int year; 
	private double price;
	private double coachPrice;
	private double businessPrice;
	
	public void setAairliner(String name) {
		this.airliner = name;
	}
	
	public String getAirliner() {
		return this.airliner;
	}
	
	public void setModel(String name) {
		this.model = name;
	}
	
	public String getModel() {
		return this.model;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getYear() {
		return this.year;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public double getPrice() {
		return this.price;
	}

	public double getCoachPrice() {
		return coachPrice;
	}

	public void setCoachPrice(double coachPrice) {
		this.coachPrice = coachPrice;
	}

	public double getBusinessPrice() {
		return businessPrice;
	}

	public void setBusinessPrice(double businessPrice) {
		this.businessPrice = businessPrice;
	}
}
