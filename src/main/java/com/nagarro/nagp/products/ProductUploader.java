package com.nagarro.nagp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class ProductUploader extends DataBaseConnectivity {
	public static PreparedStatement preparedStatement = null;
	public static String productCount ="";

	public static void updateDatabse() throws IOException, SQLException, ClassNotFoundException {
		 
		List<String> produtList = new ArrayList<>();
		File directoryPath = new File("C:\\NAGPAssignment");
		// List of all files and directories
		File[] filesList = directoryPath.listFiles();
		
		/*
		 * verify if any Tshirts files exists in the location 
		 */
		if(filesList.length>0) {
			/* iterate over the files */
		for (File file : filesList) {
			//System.out.println("File name: " + file.getName());
			FileInputStream fis = new FileInputStream(file);

			XSSFWorkbook workbook = new XSSFWorkbook(fis);

			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIt = sheet.iterator();

			while (rowIt.hasNext()) {

				Row currentRow = rowIt.next();
				int rowNumber = currentRow.getRowNum();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellIterator = currentRow.cellIterator();

				Cell cell = cellIterator.next();

				
				/*
				 * split the each row based on pipe separator and store each item in string
				 * array
				 */
				String[] strArray = cell.toString().split("\\|");

				produtList.add(cell.toString().replace("|", " "));

				try {
					preparedStatement = con.prepareStatement("select count(*) from Tshirts where ID=?");
					preparedStatement.setNString(1, strArray[0]);
					rs = preparedStatement.executeQuery();
 
					while ( rs.next()) {

						productCount =   rs.getString(1)   ; // first column int
						
						int p= Integer.parseInt(productCount);
						/* to insert product details against a ID if the details are already not available or table is empty */
						if ( ! (p==1)) {
							
						
							preparedStatement = con.prepareStatement("Insert into Tshirts values (?,?,?,?,?,?,?,?)");
							preparedStatement.setNString(1, strArray[0]);
							preparedStatement.setString(2, strArray[1]);
							preparedStatement.setString(3, strArray[2]);
							preparedStatement.setString(4, strArray[3]);
							preparedStatement.setString(5, strArray[4]);
							preparedStatement.setString(6, strArray[5]);
							preparedStatement.setString(7, strArray[6]);
							preparedStatement.setString(8, strArray[7]);

							preparedStatement.executeUpdate();
						}
						
						else {
							/* to update product details against a ID if the details are changed any time */
							preparedStatement = con.prepareStatement("update  Tshirts set  NAME=?,COLOUR=?,GENDER_RECOMMENDATION=?,SIZE=?,PRICE=?,RATING=?,AVAILABILITY=?  where ID=?");
							preparedStatement.setNString(1, strArray[1]);
							preparedStatement.setString(2, strArray[2]);
							preparedStatement.setString(3, strArray[3]);
							preparedStatement.setString(4, strArray[4]);
							preparedStatement.setString(5, strArray[5]);
							preparedStatement.setString(6, strArray[6]);
							preparedStatement.setString(7, strArray[7]); 
							preparedStatement.setString(8, strArray[0]);
							preparedStatement.executeUpdate();
							
							
						}

					}

				} catch (SQLServerException e) {
				 System.out.println(e.getMessage() );

				}

			
				 

				workbook.close();
				fis.close();

				preparedStatement.close();

			}

			
		}
	 	System.out.println("Total items in all the sheets currently are: " + produtList.size());
		} 
		
		else {
			System.out.println("No files exists in the specified location for further update,search from already uploaded files.");
			 
			 	
		}
	

	}

}
