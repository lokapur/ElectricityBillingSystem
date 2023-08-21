package com.mbl.ebs;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ElectricityBillingSystem {
	
	// Database credentials
    static final String url = "jdbc:mysql://localhost/electicitybillingsystem";
    static final String user = "root";
    static final String password = "root";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        System.out.print("Enter units consumed: ");
        int unitsConsumed = scanner.nextInt();

        // Calculate the total bill
        double billAmount = calculateBill(unitsConsumed);

        // Save the bill details in the database
        saveBillDetails(customerId, unitsConsumed, billAmount);

        System.out.println("Bill generated successfully!");
        System.out.println("Total bill amount: Rs." + billAmount);

        scanner.close();
    }

    private static double calculateBill(int unitsConsumed) {
        // logic to calculate the bill amount based on units consumed
        // Example: Assuming the cost per unit is $0.10
        double costPerUnit = 4.75;
        return unitsConsumed * costPerUnit;
    }

    private static void saveBillDetails(int customerId, int unitsConsumed, double billAmount) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Open a connection to the database
            conn = DriverManager.getConnection(url,user,password);

            // Prepare the SQL statement
            String sql = "INSERT INTO bills (customer_id, units_consumed, bill_amount) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql);

            // Set the parameter values
            stmt.setInt(1, customerId);
            stmt.setInt(2, unitsConsumed);
            stmt.setDouble(3, billAmount);

            // Execute the SQL statement
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the statement and connection
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
