package inventory;
import java.sql.*;
import java.util.Scanner;
import java.sql.Date;
public class TransactionManager {

    public static void showTransactionMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n====== Transaction Management ======");
            System.out.println("1. Add Transaction");
            System.out.println("2. View Transaction History");
            System.out.println("3. Go Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addTransaction(scanner);
                    break;
                case 2:
                    viewTransactions();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void addTransaction(Scanner scanner) {
        
        System.out.print("Enter product ID: ");
        int product_id = scanner.nextInt();
        System.out.print("Enter quantity : ");
        int stock_qua = scanner.nextInt();
        System.out.print("Enter date : ");
        String transaction_date=scanner.next();
       
        Date transaction_dt = Date.valueOf(transaction_date);  

        String query = "INSERT INTO transactions (product_id, quantity, transaction_date) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnectiondetail();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, product_id);
            preparedStatement.setInt(2, stock_qua);
            preparedStatement.setDate(3, transaction_dt); 
            preparedStatement.executeUpdate();
           
            updateProductStock(product_id,stock_qua);
            System.out.println("Transaction recorded successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProductStock(int product_id, int quantity) {
        String query = "UPDATE products SET stock_qua = stock_qua + ? WHERE product_id = ?";
       
        try (Connection connection = DatabaseConnection.getConnectiondetail();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, product_id);
            preparedStatement.executeUpdate();
            System.out.println("Product stock updated successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewTransactions() {
        String query = "SELECT * FROM transactions";
        try (Connection connection = DatabaseConnection.getConnectiondetail();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            System.out.println("Transaction History:");
            while (rs.next()) {
                System.out.println("Transaction ID: " + rs.getInt("transaction_id")
                        + ", Product ID: " + rs.getInt("product_id")
                        + ", Quantity: " + rs.getInt("quantity")
                        + ", Date: " + rs.getTimestamp("transaction_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   
}
