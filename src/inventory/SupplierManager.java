package inventory;
import java.sql.*;
import java.util.Scanner;

public class SupplierManager {
	public void showSupplierMenu(Scanner scanner) {
       
        while (true) {
            System.out.println("\n====== Supplier Management ======");
            System.out.println("1. Add Supplier");
            System.out.println("2. View Suppliers");
            System.out.println("3. Update Supplier");
            System.out.println("4. Delete Supplier");
            System.out.println("5. Go Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addSupplier(scanner);
                    break;
                case 2:
                    viewSuppliers();
                    break;
                case 3:
                    updateSupplier(scanner);
                    break;
                case 4:
                    deleteSupplier(scanner);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public void addSupplier(Scanner scanner) {
    	 System.out.print("Enter contact details: ");
         int contact_info = scanner.nextInt();
        System.out.print("Enter supplier name: ");
        String supplier_name = scanner.nextLine();
       

        String query = "INSERT INTO suppliers (supplier_name, contact_info) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnectiondetail();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, supplier_name);
            preparedStatement.setInt(2, contact_info);
            preparedStatement.executeUpdate();
            System.out.println("Supplier added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void updateSupplier(Scanner scanner) {
        
        System.out.print("Enter supplier ID: ");
        int supplier_id= scanner.nextInt();
        scanner.nextLine(); 
        System.out.print("Enter new contact details: ");
        String newContactDetails = scanner.nextLine();

        String query = "UPDATE suppliers SET contact_info = ? WHERE supplier_id = ?";
        try (Connection connection = DatabaseConnection.getConnectiondetail();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, newContactDetails);
            preparedStatement.setInt(2, supplier_id);
            preparedStatement.executeUpdate();
            System.out.println("Supplier updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void deleteSupplier(Scanner scanner) {
        
        System.out.print("Enter supplier ID to delete: ");
        int supplier_id = scanner.nextInt();

        String query = "DELETE FROM suppliers WHERE supplier_id = ?";
        try (Connection connection = DatabaseConnection.getConnectiondetail();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, supplier_id);
            preparedStatement.executeUpdate();
            System.out.println("Supplier deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void viewSuppliers() {
        String query = "SELECT * FROM suppliers";
        try (Connection connection = DatabaseConnection.getConnectiondetail();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            System.out.println("Supplier List:");
            while (rs.next()) {
                System.out.println("Supplier ID: " + rs.getInt("supplier_id")
                        + ", Name: " + rs.getString("supplier_name")
                        + ", Contact Details: " + rs.getString("contact_info"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}
