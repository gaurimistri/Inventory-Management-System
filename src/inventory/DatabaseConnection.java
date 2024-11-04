package inventory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	public static Connection connection=null;
	private static final String URL="jdbc:mysql://localhost:3306/inventory";
	private static final String username="root";
	private static final String password="Gaurimistri@2003";
	public static Connection getConnectiondetail()
	{
		try {
			if(connection==null||connection.isClosed())
			{
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection=DriverManager.getConnection(URL,username,password);
				
			}catch(ClassNotFoundException| SQLException e)
			{
				   e.printStackTrace();
			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	 public static void closeConnection() {
	        if (connection != null) {
	            try {
	                connection.close();
	                connection = null; 
	                } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

}
