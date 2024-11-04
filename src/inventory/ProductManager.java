package inventory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class ProductManager {
	
	public static void showProductMenu(Scanner scanner) {
        
        while (true) {
            System.out.println("\n====== Product Management ======");
            System.out.println("1. Add Product");
            System.out.println("2. View Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Go Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    viewProduct();
                    break;
                case 3:
                    updateProduct(scanner);
                    break;
                case 4:
                    deleteProduct(scanner);
                    break;
                case 5:
                	DatabaseConnection.closeConnection();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
        
        public static void addProduct(Scanner scanner) {
           
            System.out.print("Enter product name: ");
            String name = scanner.nextLine();
            System.out.print("Enter category: ");
            String category = scanner.nextLine();
            System.out.print("Enter initial stock quantity: ");
            int stock = scanner.nextInt();
            System.out.print("Enter price: ");
            double price = scanner.nextDouble();
           
            String query = "INSERT INTO products (product_name, category, stock_qua, price) VALUES (?, ?, ?, ?)";
            try (Connection connection = DatabaseConnection.getConnectiondetail();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, category);
                preparedStatement.setInt(3, stock);
                preparedStatement.setDouble(4, price);
                preparedStatement.executeUpdate();
                System.out.println("Product added successfully!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        public static void viewProduct()
        {
        	String query="SELECT*FROM products";
        	try(Connection connection=DatabaseConnection.getConnectiondetail();
        			Statement statement=connection.createStatement();ResultSet rs=statement.executeQuery(query))
        	{
        		System.out.println("Product list :");
        		while(rs.next())
        		{
        			System.out.println("ID: " + rs.getInt("product_id") + ", Name: " + rs.getString("product_name")
                    + ", Category: " + rs.getString("category") + ", Price: " + rs.getDouble("price")
                    + ", Stock: " + rs.getInt("stock_qua"));
        		}
        	}catch(SQLException e)
        	{
        		e.printStackTrace();
        	}
        	
        }
        
        public static void updateProduct(Scanner scanner)
        {
          
           System.out.println("Enter product ID to update :");
           int product_id=scanner.nextInt();
           System.out.println("Enter product name");
           String product_name=scanner.next();
           System.out.println("Enter new stock quantity :");
           int stock=scanner.nextInt();
           System.out.print("Enter new price: ");
           double price = scanner.nextDouble();
           
           String query="UPDATE products SET product_name=?,stock_qua=?,price=? WHERE product_id = ? ";
           try(Connection connection=DatabaseConnection.getConnectiondetail();
        		   PreparedStatement preparedStatement=connection.prepareStatement(query))
           {
        	   preparedStatement.setString(1, product_name);
        	   preparedStatement.setInt(2, stock);
        	   preparedStatement.setDouble(3, price);
        	   preparedStatement.setInt(4,product_id);
        	   preparedStatement.executeUpdate();
        	   System.out.println("product updated successfully!!");
           }catch(SQLException e)
           {
        	   e.printStackTrace();
        	   
           }
        }
        
        public static void deleteProduct(Scanner scanner) {
            System.out.print("Enter product ID to delete: ");
            int productId = scanner.nextInt();

            String query = "DELETE FROM products WHERE product_id = ?";
            try (Connection connection = DatabaseConnection.getConnectiondetail();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setInt(1, productId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Product deleted successfully!");
                } else {
                    System.out.println("Product not found. Please check the ID and try again.");
                }

            } catch (SQLException e) {
                System.out.println("Error during product deletion. Please ensure the ID is correct.");
                e.printStackTrace();
            }
        }

        

}