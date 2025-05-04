package main;

import java.util.List;
import java.util.Scanner;

import dao.*;
import entity.*;


public class CarRentalSer {
	private static final Scanner sc = new Scanner(System.in);
    private static final ICarLeaseRepository repo = new CarLeaseRepositoryImpl();
	
   // --------------------------------------------VEHICLE------------------------------------------------------------
	protected static void addCar() {
        System.out.println("\nAdd New Car");
        System.out.print("Make: ");
        String make = sc.nextLine();
        
        System.out.print("Model: ");
        String model = sc.nextLine();
        
        System.out.print("Year: ");
        int year = sc.nextInt();
        
        System.out.print("Daily Rate: ");
        double dailyRate = sc.nextDouble();
        sc.nextLine();
        
        System.out.print("Passenger Capacity: ");
        int capacity = sc.nextInt();
        
        System.out.print("Engine Capacity: ");
        double engine = sc.nextDouble();
        sc.nextLine();

        Vehicle car = new Vehicle(0, make, model, year, dailyRate, "available", capacity, engine);
        
        try {
        	System.out.println(repo.addCar(car) ? "Car added successfully!" : "Error Adding a new Car.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
	
	protected static void removeCar() {
		System.out.print("Enter Car ID to Remove: ");
		int carID = sc.nextInt();
		sc.nextLine();
		
		System.out.print("\nAre you sure you want to remove this car ? (yes / no)");
		String choice = sc.nextLine();
		
		try {
			if (choice.equals("yes") || choice.equals("y")) {
				System.out.println(repo.removeCar(carID) ? "Car removed successfully!" : "Error Removing the Car.");
			} else {
				System.out.print("\n\"Removing Car\" operation aborted.");
			}
		} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
	}

    protected static void listAvailableCars() {
    	try {
    		List<Vehicle> cars = repo.listAvailableCars();
    		
	        System.out.println("\nAvailable Cars:");
	        for (Vehicle car : cars) {
	            System.out.println(car);
	        }
    	} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    protected static void listRentedCars() {
    	try {
	    	List<Vehicle> cars = repo.listRentedCars();
	    	
	    	System.out.println("\nRented Cars:");
	    	for (Vehicle car : cars) {
	    		System.out.println(car);
	    	}
    	} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

//--------------------------------------------CUSTOMER------------------------------------------------------------
    protected static void addCustomer() {
        System.out.println("\nAdd New Customer");
        
        System.out.print("First Name: ");
        String firstName = sc.nextLine();
        
        System.out.print("Last Name: ");
        String lastName = sc.nextLine();
        
        System.out.print("Email: ");
        String email = sc.nextLine();
        
        System.out.print("Phone Number: ");
        String phone = sc.nextLine();

        Customer customer = new Customer(0, firstName, lastName, email, phone);
        
        try {
        	
        	if(repo.addCustomer(customer))
        		System.out.println("Customer added successfully!");
        	else
        		System.out.println("Error Adding New Customer.");
        	
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    protected static void removeCustomer() {
		System.out.print("Enter Customer ID to Remove: ");
		int customerID = sc.nextInt();
		sc.nextLine();
		
		System.out.print("\nAre you sure to remove this customer ? (yes / no)");
		String choice = sc.nextLine();
		
		try {
			if (choice.equals("yes") || choice.equals("y")) {
				System.out.println(repo.removeCustomer(customerID) ? "Customer removed successfully!" : "Error Removing Customer");

			}
	        	 else {
				System.out.print("\n\"Removing Customer Denied");
			}
		} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
	}

    protected static void listCustomers() {
    	try {
	        List<Customer> customers = repo.listCustomers();
	        System.out.println("\nList of Customers:");
	        for (Customer customer : customers) {
	            System.out.println(customer);
	        }
    	} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    protected static void getPaymentHistory() {
    	try {
	    	System.out.print("Enter Customer ID to Get Payment Records: ");
			int customerID = sc.nextInt();
			sc.nextLine();
	    	
	    	List<Payment> payments = repo.getPaymentHistory(customerID);
	    	
	    	System.out.println("\nPayment History (of Customer ID : " + customerID + "):");
	    	for (Payment payment : payments) {
	    		System.out.println(payment);
	    	}
    	} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

 // --------------------------------------------LEASE------------------------------------------------------------
    protected static void createLease() {
        System.out.println("\nCreate Lease");
        System.out.print("Customer ID: ");
        int customerID = sc.nextInt();
        
        System.out.print("Car ID: ");
        int carID = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Start Date (YYYY-MM-DD): ");
        String startDate = sc.nextLine();
        
        System.out.print("End Date (YYYY-MM-DD): ");
        String endDate = sc.nextLine();
        
        System.out.print("Lease Type (Daily/Monthly): ");
        String type = sc.nextLine();

        try {
            Lease lease = repo.createLease(customerID, carID, startDate, endDate, type);
            System.out.println("Lease created: " + lease);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    protected static void cancelLease() {
    	System.out.print("Enter Lease ID to cancel/close: ");
    	int leaseID = sc.nextInt();
		sc.nextLine();
		
		System.out.print("\nAre you sure you want to Close / Cancel this lease ? (yes / no)");
		String choice = sc.nextLine();
		
		try {
			if (choice.equals("yes") || choice.equals("y")) {
				repo.endLease(leaseID);
				System.out.println("Lease successfully canceled/closed.");
			} else {
				System.out.print("\n\"Lease Cancel/Close\" operation aborted.");
			}
		} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
	}
    
    protected static void listLeases() {
    	try {
	        List<Lease> leases = repo.listLeases();
	        
	        System.out.println("\nLeases:");
	        for (Lease lease : leases) {
	            System.out.println(lease);
	        }
    	} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    protected static void listActiveLeases() {
    	try {
	        List<Lease> leases = repo.listActiveLeases();
	        
	        System.out.println("\nActive Leases:");
	        for (Lease lease : leases) {
	            System.out.println(lease);
	        }
    	} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    protected static void listLeaseHistory() {
    	try {
	    	List<Lease> leases = repo.listLeaseHistory();
	    	
	    	System.out.println("\nLease History:");
	    	for (Lease lease : leases) {
	    		System.out.println(lease);
	    	}
    	} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
 // --------------------------------------------PAYMENTS------------------------------------------------------------
    protected static void recordPayment() {
        System.out.println("\nRecord Payment");
        System.out.print("Lease ID: ");
        int leaseID = sc.nextInt();
        sc.nextLine();
        
        System.out.print("Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        try {
            repo.recordPayment(leaseID, amount);
            System.out.println("Payment recorded successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    protected static void totalRevenue() {
    	try {
	        double revenue = repo.getTotalRevenue();
	        System.out.println("\nTotal Revenue: Rs." + revenue);
    	} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    
}
