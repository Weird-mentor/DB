package entity;


public class Lease {
    private int leaseID;
    private int vehicleID;
    private int customerID;
    private String startDate;
    private String endDate;
    private String type;
    private String status;

    public Lease() {
    }

    public Lease(int leaseID, int vehicleID, int customerID, String startDate, String endDate, String type, String status) {
        this.leaseID = leaseID;
        this.vehicleID = vehicleID;
        this.customerID = customerID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.status = status;
    }

    public int getLeaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
    	return "Lease { " +
    			"Lease ID = " + getLeaseID() +
    			", Vehicle ID = " + getVehicleID() +
    			", Customer ID = " + getCustomerID() +
    			", Start Date = '" + getStartDate() + '\'' +
    			", End Date = '" + getEndDate() + '\'' +
    			", Type = '" + getType() + '\'' +
    			", Status = '" + getStatus() + '\'' +
    			" }";
    }
}
