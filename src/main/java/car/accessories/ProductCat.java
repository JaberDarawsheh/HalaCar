package car.accessories;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProductCat {
    UserLoginPage userLogin = new UserLoginPage();
    ConnectDB connec = new ConnectDB();
    private static final Logger logger = Logger.getLogger(ProductCat.class.getName());
    protected boolean productFoundFlag;
    protected boolean flagIFoundSearchedProduct;
    protected boolean flagYesproductFound; 
	public boolean systemFoundCatalog()throws SQLException {
		String numberOfProduct = "SELECT COUNT(*) FROM `productCatalog` WHERE `isAvilable`=1;";
		connec.testConn();

		boolean productFoundFlag = false;

		try (java.sql.Statement stmt = connec.getConnection().createStatement();
		     ResultSet rs = stmt.executeQuery(numberOfProduct)) {

		    if (rs.next()) {
		        int rowCount = rs.getInt(1);
		        productFoundFlag = rowCount > 0;
		    }

		} catch (SQLException e) {
			Logger logger = Logger.getLogger(ProductCat.class.getName());
		    logger.log(Level.SEVERE, "An error occurred", e);
		}
		return productFoundFlag;
	}

	
	
	
	public void showProductsCatalogToUser() throws SQLException {
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
	                    int columnWidth = 18;
	                    String formattedValue = String.format("%-" + columnWidth + "s", columnValue);           
	                    rowData.append(formattedValue);

	                    if (i < columnCount) {
	                        rowData.append("|"); 
	                    }
	                }
	                logger.log(Level.INFO, rowData.toString());
	            }
	        }
	    }
	}

	public void showProductsCatalogToAdmin(Admin d) throws SQLException {
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
	
	public void searchAndShowFilteredResult(String someProduct, String price) throws SQLException {
	    connec.testConn();
	    String query = "SELECT * FROM productcatalog WHERE `productName` = ? AND `productPrice` = ?";
	    
	    try (PreparedStatement preparedStatement = connec.getConnection().prepareStatement(query)) {
	        preparedStatement.setString(1, someProduct);
	        preparedStatement.setString(2, price);
	        
	        boolean foundResults = false; 	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            
	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String name = resultSet.getString("productName"); 
	                String productPrice = resultSet.getString("productPrice"); 
	                String productType = resultSet.getString("productType");
	                String productAvailable = resultSet.getString("isAvilable");
	                
	                String splitCol="      |      " ;
	                logger.log(Level.INFO, String.format("Product id: %s%sProduct name: %s%sProduct price: %s%sProduct type: %s%sProduct available: %s%s",
	                	    id, splitCol, name, splitCol, productPrice, splitCol, productType, splitCol, productAvailable, splitCol));

	                foundResults = true;
	            }
	        }
	        
	        if (!foundResults) {
	            logger.info("You searched for something that didn't exist!");
	        }
	    }
	}


	public boolean  searchAboutSomeProduct(String strangeProduct) throws SQLException
	{
		String querySt = "SELECT COUNT(*) FROM ProductCatalog WHERE `productNam` = ?";
		connec.testConn();

		try (PreparedStatement preparedStatement = connec.getConnection().prepareStatement(querySt)) {
		    preparedStatement.setString(1, strangeProduct); 
		    try (ResultSet resultSet = preparedStatement.executeQuery()) {
		        if (resultSet.next()) {
		            int count = resultSet.getInt(1);
		            return count > 0; 
		        }
		    }
		} catch (SQLException e) {
			Logger logger = Logger.getLogger(ProductCat.class.getName());
		    logger.log(Level.SEVERE, "An error occurred", e); 
		}

		return false;

	}

	public boolean isproductFoundFlagFound() {
		return productFoundFlag;
	}
	public void setFlagFound(boolean productFound_flag) {
		this.productFoundFlag = productFound_flag;
	}


}
