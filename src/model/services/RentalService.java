package model.services;

import java.time.Duration;

import entities.CarRental;
import entities.Invoice;

public class RentalService {
	
	private Double pricePerHour;
	private Double pricePerDay;
	
	private BrazilTaxService taxService;
	
	public RentalService(Double pricePerHour, Double pricePerDay, BrazilTaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes / 60;
		
		double basicPaymente;
		if (hours <= 12.0) {
			basicPaymente = pricePerHour * Math.ceil(hours);
		}
		else {
			basicPaymente = pricePerDay * Math.ceil(hours / 24.0);
		}
		
		double tax = taxService.tax(basicPaymente);
		
		carRental.setInvoice(new Invoice(basicPaymente, tax));
		
	}
	
}
