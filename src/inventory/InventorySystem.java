package inventory;
import java.util.Scanner;

public class InventorySystem {
    public static Scanner scanner=new Scanner(System.in);
    public static void main(String args[]) {
        
        ProductManager product = new ProductManager();
        SupplierManager supplier = new SupplierManager();
        TransactionManager transaction = new TransactionManager();
        
        try {
            while (true) {
                System.out.println("===============INVENTORY MANAGEMENT SYSTEM=============");
                System.out.println("1. Manage Products");
                System.out.println("2. Manage Supplier");
                System.out.println("3. Manage Transaction");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        product.showProductMenu(scanner);
                        break;
                    case 2:
                        supplier.showSupplierMenu(scanner);
                        break;
                    case 3:
                        transaction.showTransactionMenu(scanner);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        return; 
                    default:
                        System.out.println("You have entered an invalid choice.");
                }
            }
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); 
        } finally {
            scanner.close(); 
        }
    }
}
