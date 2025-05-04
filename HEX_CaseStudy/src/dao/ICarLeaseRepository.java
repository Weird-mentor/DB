package dao;

import java.util.List;

import entity.*;


public interface ICarLeaseRepository {
    // Car
    boolean addCar(Vehicle car);
    boolean removeCar(int carID);
    List<Vehicle> listAvailableCars();
    List<Vehicle> listRentedCars();
    Vehicle findCarById(int carID);

    // Customer
    boolean addCustomer(Customer customer);
    boolean removeCustomer(int customerID);
    List<Customer> listCustomers();
    List<Payment> getPaymentHistory(int customerID);
    Customer findCustomerById(int customerID);

    // Lease
    Lease createLease(int customerID, int carID, String startDate, String endDate, String type);
    void endLease(int leaseID);
    List<Lease> listLeases();
    List<Lease> listActiveLeases();
    List<Lease> listLeaseHistory();
    Lease findLeaseById(int leaseID);

    // Payment
    void recordPayment(int leaseID, double amount);
    double getTotalRevenue();
}
