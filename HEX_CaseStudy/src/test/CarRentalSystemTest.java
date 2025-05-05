package test;

import java.sql.Connection;

import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
 
import org.junit.jupiter.api.Test;
import org.junit.Test;

import dao.CarLeaseRepositoryImpl;
import entity.Customer;
import entity.Lease;
import entity.Payment;
import entity.Vehicle;
import util.DBConnUtil;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CarRentalSystemTest {

	static CarLeaseRepositoryImpl repo;
	static Connection testConnection;

	@BeforeAll
	public static void setup() throws Exception {
		testConnection = DBConnUtil.getConnection();
		repo = new CarLeaseRepositoryImpl(testConnection);
		System.out.println("Setup completed");
	}

	// 1. Add Car
	@Test
	@Order(1)
	public void testAddCar() throws Exception {
		System.out.println("\n[Test 1] Starting testAddCar");
		
		Vehicle car = new Vehicle(0, "TestMake", "TestModel", 2022, 500.0, "available", 5, 1.2);
		
		boolean result = repo.addCar(car);
		assertTrue(result);

		List<Vehicle> available = repo.listAvailableCars();
		boolean found = false;
		int carId = -1;
		
		for (Vehicle v : available) {
			if (v.getMake().equals("TestMake")) {
				carId = v.getVehicleID();
				found = true;
				break;
			}
		}
		
		assertTrue(found);
		
		//Cleanup
		repo.removeCar(carId);
		
		System.out.println("[Test 1 Passed] testAddCar completed.");
	}

	// 2. Remove Car
	@Test
	@Order(2)
	public void testRemoveCar() throws Exception {
		System.out.println("\n[Test 2] Starting testRemoveCar");
		
		Vehicle car = new Vehicle(0, "DeleteMake", "DeleteModel", 2021, 400.0, "available", 4, 1.0);
		repo.addCar(car);
		
		int carId = repo.listAvailableCars().getLast().getVehicleID();

		boolean result = repo.removeCar(carId);
		assertTrue(result);

		List<Vehicle> updatedList = repo.listAvailableCars();
		for (Vehicle v : updatedList) {
			assertNotEquals(carId, v.getVehicleID());
		}
		
		System.out.println("[Test 2 Passed] testRemoveCar completed.");
	}

	// 3. Add Customer
	@Test
	@Order(3)
	public void testAddCustomer() throws Exception {
		System.out.println("\n[Test 3] Starting testAddCustomer");
		
		Customer customer = new Customer(0, "Test", "User", "test@example.com", "9876543210");
		
		boolean result = repo.addCustomer(customer);
		assertTrue(result);

		List<Customer> list = repo.listCustomers();
		boolean found = false;
		int id = -1;
		
		for (Customer c : list) {
			if (c.getEmail().equals("test@example.com")) {
				id = c.getCustomerID();
				found = true;
				break;
			}
		}
		
		assertTrue(found);
		
		//Cleanup
		repo.removeCustomer(id);

		System.out.println("[Test 3 Passed] testAddCustomer completed.");
	}

	// 4. Remove Customer
	@Test
	@Order(4)
	public void testRemoveCustomer() throws Exception {
		System.out.println("\n[Test 4] Starting testRemoveCustomer");
		Customer customer = new Customer(0, "Del", "Cust", "del@example.com", "9998887770");
		
		repo.addCustomer(customer);
		int id = repo.listCustomers().getLast().getCustomerID();

		boolean result = repo.removeCustomer(id);
		assertTrue(result);

		List<Customer> updatedList = repo.listCustomers();
		for (Customer c : updatedList) {
			assertNotEquals(id, c.getCustomerID());
		}
		
		System.out.println("[Test 4 Passed] testRemoveCustomer completed.");
	}

	// 5. Create and End Lease
	@Test
	@Order(5)
	public void testCreateAndEndLease() throws Exception {
		System.out.println("\n[Test 5] Starting testCreateAndEndLease");
		
		Customer cust = new Customer(0, "Lease", "Guy", "leaseguy@example.com", "1112223333");
		repo.addCustomer(cust);
		
		int custId = repo.listCustomers().getLast().getCustomerID();

		Vehicle car = new Vehicle(0, "LeaseCar", "ModelX", 2023, 800, "available", 4, 1.5);
		repo.addCar(car);
		
		int carId = repo.listAvailableCars().getLast().getVehicleID();

		Lease lease = repo.createLease(custId, carId, "2025-01-01", "2025-01-10", "Daily");
		assertNotNull(lease);

		repo.endLease(lease.getLeaseID());

		boolean found = repo.listLeaseHistory().stream()
			.anyMatch(l -> l.getLeaseID() == lease.getLeaseID());

		assertTrue(found);

		// Cleanup
		testConnection.createStatement().executeUpdate("DELETE FROM lease WHERE leaseID = " + lease.getLeaseID());
		repo.removeCustomer(custId);
		repo.removeCar(carId);
		
		System.out.println("[Test 5 Passed] testCreateAndEndLease completed.");
	}

	// 6. Record Payment
	@Test
	@Order(6)
	public void testRecordPayment() throws Exception {
		System.out.println("\n[Test 6] Starting testRecordPayment");
		
		Customer cust = new Customer(0, "Pay", "User", "pay@example.com", "9998887776");
		repo.addCustomer(cust);
		
		int custId = repo.listCustomers().getLast().getCustomerID();

		Vehicle car = new Vehicle(0, "PayCar", "ModelZ", 2023, 900, "available", 4, 1.6);
		repo.addCar(car);
		
		int carId = repo.listAvailableCars().getLast().getVehicleID();
		Lease lease = repo.createLease(custId, carId, "2025-01-01", "2025-01-05", "Daily");
		repo.recordPayment(lease.getLeaseID(), 2000.0);

		boolean found = repo.getPaymentHistory(custId).stream()
			.anyMatch(p -> p.getAmount() == 2000.0);

		assertTrue(found);

		// Cleanup
		repo.endLease(lease.getLeaseID());
		testConnection.createStatement().executeUpdate("DELETE FROM payment WHERE leaseID = " + lease.getLeaseID());
		testConnection.createStatement().executeUpdate("DELETE FROM lease WHERE leaseID = " + lease.getLeaseID());
		repo.removeCustomer(custId);
		repo.removeCar(carId);
		
		System.out.println("[Test 6 Passed] testRecordPayment completed.");
	}

	// 7. Total Revenue
	@Test
	@Order(7)
	public void testGetTotalRevenue() {
		System.out.println("\n[Test 7] Starting testGetTotalRevenue");
		double total = repo.getTotalRevenue();
		System.out.println("Total Revenue: Rs." + total);
		
		assertTrue(total >= 0.0);
	}

	// 8. List Customers
	@Test
	@Order(8)
	public void testListCustomers() {
		System.out.println("\n[Test 8] Starting testListCustomers");
		List<Customer> customers = repo.listCustomers();
		System.out.println("Total customers: " + customers.size());
		
		assertNotNull(customers);
	}

	// 9. List Available Cars
	@Test
	@Order(9)
	public void testListAvailableCars() {
		System.out.println("\n[Test 9] Starting testListAvailableCars");
		List<Vehicle> cars = repo.listAvailableCars();
		System.out.println("Available cars: " + cars.size());
		
		assertNotNull(cars);
	}

	// 10. List Rented Cars
	@Test
	@Order(10)
	public void testListRentedCars() {
		System.out.println("\n[Test 10] Starting testListRentedCars");
		List<Vehicle> cars = repo.listRentedCars();
		System.out.println("Rented cars: " + cars.size());
		assertNotNull(cars);
	}

	// 11. List All Leases
	@Test
	@Order(11)
	public void testListLeases() {
		System.out.println("\n[Test 11] Starting testListLeases");
		List<Lease> leases = repo.listLeases();
		System.out.println("Total leases: " + leases.size());
		
		assertNotNull(leases);
	}

	// 12. List Active Leases
	@Test
	@Order(12)
	public void testListActiveLeases() {
		System.out.println("\n[Test 12] Starting testListActiveLeases");
		
		List<Lease> leases = repo.listActiveLeases();
		System.out.println("Active leases: " + leases.size());
		
		assertNotNull(leases);
	}

	// 13. Lease History
	@Test
	@Order(13)
	public void testListLeaseHistory() {
		System.out.println("\n[Test 13] Starting testListLeaseHistory");
		
		List<Lease> leases = repo.listLeaseHistory();
		System.out.println("Lease history records: " + leases.size());
		
		assertNotNull(leases);
	}

	// 14. Payment History of a Customer
	@Test
	@Order(14)
	public void testGetPaymentHistoryOfCustomer() {
		System.out.println("\n[Test 14] Starting testGetPaymentHistoryOfCustomer");
		
		List<Customer> customers = repo.listCustomers();
		if (!customers.isEmpty()) {
			int id = customers.getFirst().getCustomerID();
			List<Payment> payments = repo.getPaymentHistory(id);
			System.out.println("Payments for customer " + id + ": " + payments.size());
			
			assertNotNull(payments);
		} else {
			System.out.println("No customers found to test payment history.");
		}
	}
	
}
