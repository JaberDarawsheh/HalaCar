package CarAccessiores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductCat {
    UserLoginPage userLogin = new UserLoginPage();
    connectDB connec = new connectDB();
    private static final Logger logger = Logger.getLogger(ProductCat.class.getName());
    protected boolean productFound_flag;
    protected boolean flag_iFoundsearchedProduct;
    protected boolean flagYesproductFound;
    
	public boolean system_is_found_catalog()throws SQLException {
		String numberOfProduct="SELECT COUNT(*) FROM `productCatalog` WHERE `isAvilable`=1;";
		connec.testConn();
		java.sql.Statement stmt = connec.getConnection().createStatement();
		ResultSet rs=((java.sql.Statement) stmt).executeQuery(numberOfProduct);
		if(rs.next()) {
			int rowCount=rs.getInt(1);

			productFound_flag=rowCount>0;
		}
		return productFound_flag;
	}

	public void show_products_catalog_toUser(UserLoginPage userlogin) throws SQLException {
	    connec.testConn();
	    logger.log(Level.INFO, "|        id       |   product name   |   product type   |   product price  |   product img    |   availability   |");
	    String query = "SELECT * FROM productcatalog";

	    try (PreparedStatement preparedStatement = connec.getConnection().prepareStatement(query)) {

	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            ResultSetMetaData metaData = resultSet.getMetaData();
	            int columnCount = metaData.getColumnCount();

	            while (resultSet.next()) {
	                StringBuilder rowData = new StringBuilder();

	                for (int i = 1; i <= columnCount; i++) {
	                    String columnValue = resultSet.getString(i);

	                    // Define a fixed width for each column and left-align the text
	                    int columnWidth = 18;
	                    String formattedValue = String.format("%-" + columnWidth + "s", columnValue);
	                    
	                    rowData.append(formattedValue);

	                    if (i < columnCount) {
	                        rowData.append("|"); // Add a vertical bar as a separator between columns
	                    }
	                }

	                logger.log(Level.INFO, rowData.toString());
	            }
	        }
	    }
	}

	public void show_products_catalog_toAdmin(admin d) throws SQLException {
		connec.testConn();
		logger.log(Level.INFO, "|        id       |   product name   |   product type   |   product price  |   product img    |   availability   |");
		String query = "SELECT * FROM productcatalog";

		try (PreparedStatement preparedStatement = connec.getConnection().prepareStatement(query)) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				ResultSetMetaData metaData = resultSet.getMetaData();
				int columnCount = metaData.getColumnCount();

				while (resultSet.next()) {
					StringBuilder rowData = new StringBuilder();

					for (int i = 1; i <= columnCount; i++) {
						String columnValue = resultSet.getString(i);

						// Define a fixed width for each column and left-align the text
						int columnWidth = 18;
						String formattedValue = String.format("%-" + columnWidth + "s", columnValue);

						rowData.append(formattedValue);

						if (i < columnCount) {
							rowData.append("|"); // Add a vertical bar as a separator between columns
						}
					}

					logger.log(Level.INFO, rowData.toString());
				}
			}
		}

	}
	public void search_and_show_filtered_result(String someProduct, String price) throws SQLException {
	    connec.testConn();
	    String query = "SELECT * FROM productcatalog WHERE `productName` = ? AND `productPrice` = ?";
	    
	    try (PreparedStatement preparedStatement = connec.getConnection().prepareStatement(query)) {
	        preparedStatement.setString(1, someProduct);
	        preparedStatement.setString(2, price);
	        
	        boolean foundResults = false; // Track if any results were found
	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            
	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String name = resultSet.getString("productName"); 
	                String productPrice = resultSet.getString("productPrice"); 
	                String ProductType = resultSet.getString("productType");
	                String ProductAvailable = resultSet.getString("isAvilable");
	                
	                logger.log(Level.INFO, "Product id: " + id + "      |      " +
	                                       "Product name: " + name + "      |      " +
	                                       "Product price: " + productPrice + "      |      " +
	                                       "Product type: " + ProductType + "      |      " +
	                                       "Product available: " + ProductAvailable + "      |      ");
	                foundResults = true;
	            }
	        }
	        
	        if (!foundResults) {
	            logger.info("You searched for something that didn't exist!");
	        }
	    }
	}


	public boolean  search_about_some_product(String strangeProduct) throws SQLException
	{
		String query_st="SELECT COUNT(*) FROM ProductCatalog WHERE `productName` = ?";
		connec.testConn();
		        try (PreparedStatement preparedStatement = connec.getConnection().prepareStatement(query_st)){
		   
		            try (ResultSet resultSet = preparedStatement.executeQuery()) {
		                preparedStatement.setString(1, strangeProduct);
		                    if (resultSet.next()) {
		                        int count = resultSet.getInt(1);
		                        return count > 0; // Product exists if count is greater than 0
		                    }
		                }
		        } catch (SQLException e) 
		        {
		            e.printStackTrace();
		        }     
		        return false; 
	}

	public boolean isproductFound_flagFound() {
		return productFound_flag;
	}
	public void setFlag_found(boolean productFound_flag) {
		this.productFound_flag = productFound_flag;
	}
}
