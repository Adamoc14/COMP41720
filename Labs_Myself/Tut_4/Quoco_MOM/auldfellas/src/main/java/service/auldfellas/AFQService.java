package service.auldfellas; // Imports


import service.core.AbstractQuotationService;
import service.core.ClientInfo;
import service.core.Quotation;

/**
  * Implementation of the AuldFellas insurance quotation service.
  * @author Rem
  *
 */

 public class AFQService extends AbstractQuotationService {

 	// All references are to be prefixed with an AF (e.g. AF001000)
 	public static final String PREFIX = "AF";
 	public static final String COMPANY = "Auld Fellas Ltd.";

 	/*
 	  Quote generation:
 	  30% discount for being male
 	  2% discount per year over 60
 	  20% discount for less than 3 penalty points
 	  50% penalty (i.e. reduction in discount) for more than 60 penalty points
 	 */

 	/**
 	 * Generates Quotation for the AuldFellas Service using the client's info
 	 * @return Quotation
 	 */
 	public Quotation generateQuotation(ClientInfo client_info) {

 		// Create an initial quotation between 600 and 1200
 		double price = generatePrice(600, 600);

 		// Automatic 30% discount for being male
 		int discount = (client_info.gender == ClientInfo.MALE) ? 30:0;

 		// Automatic 2% discount per year over 60...
 		discount += (client_info.age > 60) ? (2*(client_info.age-60)) : 0;

 		// Add a points discount
 		discount += getPointsDiscount(client_info.points);

 		// Generate the quotation and send it back
 		return new Quotation(COMPANY, generateReference(PREFIX), (price * (100-discount)) / 100);

 	}

 	/**
 	 * Calculates the discount based on penalty points received
 	 * @return integer representing discount received
 	*/

 	private int getPointsDiscount(int penalty_points_received) {

 		// 20% discount for less than 3 penalty points
 		if (penalty_points_received < 3) return 20;

 		// No Discount for 3 - 6 penalty points
 		if (penalty_points_received <= 6) return 0;

 		// 50% added on for anything over
 		return -50;
 	}

}
