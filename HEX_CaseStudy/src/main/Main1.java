package main;

import java.util.Scanner;

public class Main1 extends CarRentalSer {
    public static void main(String[] args) {
    	Scanner sc = new Scanner(System.in);
    	System.out.println("╔════════════════════════╗");
    	System.out.println("║   CAR RENTAL SYSTEM    ║");
    	System.out.println("╚════════════════════════╝");

        int choice, subChoice;
        
        try {
	        do {
	        	System.out.println("╔══════════════════════════════╗");
	        	System.out.println("║          MAIN MENU           ║");
	        	System.out.println("╠══════════════════════════════╣");
	        	System.out.println("║ What do you want to work on ?║");
	        	System.out.println("║ 1. Vehicle                   ║");
	        	System.out.println("║ 2. Customer                  ║");
	        	System.out.println("║ 3. Lease                     ║");
	        	System.out.println("║ 4. Payment                   ║");
	        	System.out.println("║ 5. Exit                      ║");
	        	System.out.println("╚══════════════════════════════╝");
	        	System.out.print("Enter your choice: ");

	            choice = sc.nextInt();
	            sc.nextLine();
	            
                switch (choice) {
                    case 1:
                    	System.out.println("╔══════════════════════════════╗");
                    	System.out.println("║        VEHICLE MENU          ║");
                    	System.out.println("╠══════════════════════════════╣");
                    	System.out.println("║1. 	Add Car				     ║");
                    	System.out.println("║2. 	Remove Car             ║");
                    	System.out.println("║3. 	List Available Cars    ║");
                    	System.out.println("║4. 	List Rented Cars       ║");
                    	System.out.println("║5. 	Go Back to Main Menu   ║");
                    	System.out.println("╚══════════════════════════════╝");
                    	System.out.print("Enter your choice: ");
                    	subChoice = sc.nextInt();
                    	sc.nextLine();
                    	
                    	switch(subChoice) {
                    		case 1:
                    			addCar();
                    			break;
                    		case 2:
                    			removeCar();
                    			break;
                    		case 3:
                    			listAvailableCars();
                    			break;
                    		case 4:
                    			listRentedCars();
                    			break;
                    		case 5:
                    			break;
                    		default:
                    			System.out.println("Invalid choice! Returning to Main Menu");
                    	}
                    	break;
                    	
                    case 2:
                    	System.out.println("╔══════════════════════════════╗");
                    	System.out.println("║        CUSTOMER MENU         ║");
                    	System.out.println("╠══════════════════════════════╣");
                    	System.out.println("║ 1. Add Customer              ║");
                    	System.out.println("║ 2. Remove Customer           ║");
                    	System.out.println("║ 3. List Customers            ║");
                    	System.out.println("║ 4. Go Back to Main Menu      ║");
                    	System.out.println("╚══════════════════════════════╝");
                    	System.out.print("Enter your choice: ");

                    	System.out.print("Enter your choice: ");
                    	subChoice = sc.nextInt();
                    	sc.nextLine();
                    	
                    	switch(subChoice) {
                    		case 1:
                    			addCustomer();
                    			break;
                    		case 2:
                    			removeCustomer();
                    			break;
                    		case 3:
                    			listCustomers();
                    			break;
                    		case 4:
                    			break;
                    		default:
                    			System.out.println("Invalid choice! Returning to Main Menu");
                    	}
                    	break;
                    	
                    case 3:
                    	System.out.println("╔══════════════════════════════╗");
                    	System.out.println("║          LEASE MENU          ║");
                    	System.out.println("╠══════════════════════════════╣");
                    	System.out.println("║ 1. Create Lease              ║");
                    	System.out.println("║ 2. Close Lease               ║");
                    	System.out.println("║ 3. List All Leases           ║");
                    	System.out.println("║ 4. List Active Leases        ║");
                    	System.out.println("║ 5. List Lease History        ║");
                    	System.out.println("║ 6. Go Back to Main Menu      ║");
                    	System.out.println("╚══════════════════════════════╝");
                    	System.out.print("Enter your choice: ");

                    	subChoice = sc.nextInt();
                    	sc.nextLine();
                    	
                    	switch(subChoice) {
                    		case 1:
                    			createLease();
                    			break;
                    		case 2:
                    			cancelLease();
                    			break;
                    		case 3:
                    			listLeases();
                    			break;
                    		case 4:
                    			listActiveLeases();
                    			break;
                    		case 5:
                    			listLeaseHistory();
                    			break;
                    		case 6:
                    			break;
                    		default:
                    			System.out.println("Invalid choice! Returning to Main Menu");
                    	}
                    	break;
                    case 4:
                    	System.out.println("╔══════════════════════════════╗");
                    	System.out.println("║         PAYMENT MENU         ║");
                    	System.out.println("╠══════════════════════════════╣");
                    	System.out.println("║ 1. Record Payment            ║");
                    	System.out.println("║ 2. Total Revenue             ║");
                    	System.out.println("║ 3. Go Back to Main Menu      ║");
                    	System.out.println("╚══════════════════════════════╝");
                    	System.out.print("Enter your choice: ");

                    	subChoice = sc.nextInt();
                    	sc.nextLine();
                    	
                    	switch(subChoice) {
                    		case 1:
                    			recordPayment();
                    			break;
                    		case 2:
                    			totalRevenue();
                    			break;
                    		case 3:
                    			break;
                    		default:
                    			System.out.println("Invalid choice! Returning to Main Menu");
                    	}
                    	break;
                    case 5:
                    	break;
                    default:
                    	System.out.println("Invalid choice! Please try again.");
                }
	        } while (choice != 5);
        } catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
        }
        
        sc.close();
        System.out.println("\nThank You !!!");
    }
}
