package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.*;
import exception.*;
import util.DBConnUtil;


public class CarLeaseRepositoryImpl implements ICarLeaseRepository {
	private Connection connection;
	
	public CarLeaseRepositoryImpl() {
        this.connection = DBConnUtil.getConnection();
    }
	
	public CarLeaseRepositoryImpl(Connection conn) { // For JUnit
	    this.connection = conn;
	}

    // ---------------------------------------------------Vehicle------------------------------------------------------------------
    @Override
    public boolean addCar(Vehicle car) {    	
        try {
        	String query = "INSERT INTO Vehicle (make, model, year, dailyRate, status, passengerCapacity, engineCapacity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        	
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, car.getMake());
            ps.setString(2, car.getModel());
            ps.setInt(3, car.getYear());
            ps.setDouble(4, car.getDailyRate());
            ps.setString(5, car.getStatus());
            ps.setInt(6, car.getPassengerCapacity());
            ps.setDouble(7, car.getEngineCapacity());
            
            ps.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeCar(int carID) {
        try {
            String query = "DELETE FROM Vehicle WHERE vehicleID = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setInt(1, carID);
            
            if (ps.executeUpdate() == 0) {
                throw new CarNotFoundException("Car with ID: (" + carID + ") is not found.");
            } else {
            	return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (CarNotFoundException e) {
        	e.printStackTrace();
        	return false;
        }
    }

    @Override
    public List<Vehicle> listAvailableCars() {
        List<Vehicle> availableCars = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM Vehicle WHERE status = 'available'";
            
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                availableCars.add(mapToVehicle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return availableCars;
    }

    @Override
    public List<Vehicle> listRentedCars() {
        List<Vehicle> rentedCars = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM Vehicle WHERE status = 'notAvailable'";
            
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                rentedCars.add(mapToVehicle(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rentedCars;
    }

    @Override
    public Vehicle findCarById(int carID) {
        try {
            String query = "SELECT * FROM Vehicle WHERE vehicleID = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setInt(1, carID);
            
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapToVehicle(rs);
            } else {
                throw new CarNotFoundException("Car with ID " + carID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CarNotFoundException e) {
        	e.printStackTrace();
        }
        
        return null;
    }

    // ----------------------------------------------------------------Customer---------------------------------------------------------------
    @Override
    public boolean addCustomer(Customer customer) {
        try {
            String query = "INSERT INTO Customer (firstName, lastName, email, phoneNumber) VALUES (?, ?, ?, ?)";
            
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPhoneNumber());
            
            ps.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeCustomer(int customerID) {
        try {
            String query = "DELETE FROM Customer WHERE customerID = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setInt(1, customerID);
            
            if (ps.executeUpdate() == 0) {
                throw new CustomerNotFoundException("Customer with ID " + customerID + " not found.");
            } else {
            	return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (CustomerNotFoundException e) {
        	e.printStackTrace(); 
        	return false;
        }
    }

    @Override
    public List<Customer> listCustomers() {
        List<Customer> customers = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM Customer";
            
            Statement stmt = connection.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                customers.add(new Customer(
                    rs.getInt("customerID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phoneNumber")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customers;
    }

    @Override
    public Customer findCustomerById(int customerID) {
        try {
            String query = "SELECT * FROM Customer WHERE customerID = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setInt(1, customerID);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Customer(
                    rs.getInt("customerID"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phoneNumber")
                );
            } else {
                throw new CustomerNotFoundException("Customer with ID " + customerID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CustomerNotFoundException e) {
        	e.printStackTrace();        	
        }
        
        return null;
    }

    //------------------------------------------------------------ Lease--------------------------------------------------------------------
    @Override
    public Lease createLease(int customerID, int vehicleID, String startDate, String endDate, String type) {  
    	try {
            String checkQuery = "SELECT status FROM Vehicle WHERE vehicleID = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkQuery);
            
            checkStmt.setInt(1, vehicleID);

            ResultSet checkRs = checkStmt.executeQuery();
            if (checkRs.next()) {
                String status = checkRs.getString("status");
                
                if ("notAvailable".equalsIgnoreCase(status)) {
                    System.out.println("The selected car is not available.");
                    return null;
                }
            } else {
                System.out.println("Vehicle not found.");
                return null;
            }

            String insertQuery = "INSERT INTO Lease (vehicleID, customerID, startDate, endDate, type, status) VALUES (?, ?, ?, ?, ?, 'active')";
            PreparedStatement insertStmt = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setInt(1, vehicleID);
            insertStmt.setInt(2, customerID);
            insertStmt.setString(3, startDate);
            insertStmt.setString(4, endDate);
            insertStmt.setString(5, type);
            
            insertStmt.executeUpdate();

            String updateStatusQuery = "UPDATE Vehicle SET status = 'notAvailable' WHERE vehicleId = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateStatusQuery);
            
            updateStmt.setInt(1, vehicleID);
            
            updateStmt.executeUpdate();

            ResultSet rs = insertStmt.getGeneratedKeys();
            if (rs.next()) {
                int leaseID = rs.getInt(1);
                return new Lease(leaseID, vehicleID, customerID, startDate, endDate, type, "active");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    @Override
    public Lease findLeaseById(int leaseID) {
        try {
            String query = "SELECT * FROM Lease WHERE leaseID = ?";
            
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setInt(1, leaseID);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Lease(
                    rs.getInt("leaseID"),
                    rs.getInt("customerID"),
                    rs.getInt("vehicleID"),
                    rs.getString("startDate"),
                    rs.getString("endDate"),
                    rs.getString("type"),
                    rs.getString("status")
                );
            } else {
                throw new LeaseNotFoundException("Lease with ID " + leaseID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (LeaseNotFoundException e) {
        	e.printStackTrace();        	
        }
        
        return null;
    }
    
    @Override
    public void endLease(int leaseId) {
        try {
        	String query = "UPDATE Lease SET status = 'inactive' WHERE leaseId = ?";
        	
        	PreparedStatement stmt = connection.prepareStatement(query);
            
            stmt.setInt(1, leaseId);
            
            stmt.executeUpdate();
            
            System.out.println("Lease ID " + leaseId + " marked as 'inactive'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkAndEndLeases() {
        try {
        	String query = "SELECT leaseID, endDate FROM Lease WHERE status = 'active'";

        	Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            LocalDate today = LocalDate.now();

            while (rs.next()) {
                int leaseID = rs.getInt("leaseID");
                LocalDate endDate = LocalDate.parse(rs.getString("endDate"));

                if (endDate.isBefore(today)) {
                    endLease(leaseID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Lease> listLeases() {    	
        List<Lease> leases = new ArrayList<>();

        try {
        	String query = "SELECT * FROM Lease";

        	Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                leases.add(new Lease(
                    rs.getInt("leaseID"),
                    rs.getInt("vehicleID"),
                    rs.getInt("customerID"),
                    rs.getString("startDate"),
                    rs.getString("endDate"),
                    rs.getString("type"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return leases;
    }

    @Override
    public List<Lease> listActiveLeases() {
    	checkAndEndLeases();
    	
        List<Lease> leases = new ArrayList<>();

        try {
        	String query = "SELECT * FROM Lease WHERE status = 'active'";

        	Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                leases.add(new Lease(
                    rs.getInt("leaseID"),
                    rs.getInt("vehicleID"),
                    rs.getInt("customerID"),
                    rs.getString("startDate"),
                    rs.getString("endDate"),
                    rs.getString("type"),
                    rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return leases;
    }

    @Override
    public List<Lease> listLeaseHistory() {
        List<Lease> leaseHistory = new ArrayList<>();
        
        try {
            String query = "SELECT * FROM Lease WHERE status = 'inactive'";
            
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                leaseHistory.add(mapToLease(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return leaseHistory;
    }

    // --------------------------------------------------------Payment----------------------------------------------------------------
    @Override
    public void recordPayment(int leaseID, double amount) {
        try {
            String query = "INSERT INTO Payment (leaseID, amount, paymentDate) VALUES (?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(query);
            java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

            ps.setInt(1, leaseID);
            ps.setDouble(2, amount);
            ps.setDate(3, currentDate);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public double getTotalRevenue() {
        double totalRevenue = 0;
        
        try {
        	String query = "SELECT SUM(amount) FROM Payment";

        	Statement stmt = connection.createStatement();
            
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                totalRevenue = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return totalRevenue;
    }

    @Override
    public List<Payment> getPaymentHistory(int customerID) {
        List<Payment> payments = new ArrayList<>();
        
        try {
        	String query = "SELECT p.paymentID, p.leaseID, p.amount, p.paymentDate FROM Payment p JOIN Lease l ON p.leaseID = l.leaseID WHERE l.customerID = ?";
        	
            PreparedStatement ps = connection.prepareStatement(query);
            
            ps.setInt(1, customerID);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                payments.add(new Payment(
                    rs.getInt("paymentID"),
                    rs.getInt("leaseID"),
                    rs.getDate("paymentDate"),
                    rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        
        return payments;
    }
    

    // Helper Methods to map ResultSet to Lease
    private Lease mapToLease(ResultSet rs) throws SQLException {
        return new Lease(
            rs.getInt("leaseID"),
            rs.getInt("customerID"),
            rs.getInt("VehicleID"),
            rs.getString("startDate"),
            rs.getString("endDate"),
            rs.getString("type"),
            rs.getString("status")
        );
    }
    
    // Helper method to map ResultSet to Vehicle
    private Vehicle mapToVehicle(ResultSet rs) throws SQLException {
        return new Vehicle(
            rs.getInt("vehicleID"),
            rs.getString("make"),
            rs.getString("model"), 
            rs.getInt("year"),
            rs.getDouble("dailyRate"),
            rs.getString("status"), 
            rs.getInt("passengerCapacity"),
            rs.getDouble("engineCapacity")
        );
    }
}
