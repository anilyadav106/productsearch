package nagp.products;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.nagarro.nagp.DataBaseConnectivity;
import com.nagarro.nagp.FindItems;
import com.nagarro.nagp.ProductUploader;

public class RunTest {
	protected static ResultSet rs = null;
	static PreparedStatement st = null;
	static Scanner scan = null;
	  
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		DataBaseConnectivity.connectDatabase();
		ProductUploader.updateDatabse();
		System.out.println("****Dear User, Welcome to our shopping portal****");
		scan = new Scanner(System.in);
		System.out.println("Please enter T shirt color :");
		String color = scan.next();

		System.out.println("Please enter size of product -->Valid list of size are --> (S,M,L,XL,XXL");
		String size = scan.next();

		System.out.println("Please enter gender -->Choose as M for Male, F for Female, U for Unisex");
		String gender = scan.next();

		System.out.println(
				"Please enter output preference -->Valid list of sorting are --> Choose as P for Price ,R for Rating ,PR for first Price then Rating");
		String sortPreference  = scan.next();
    

		FindItems.searchProduct(color, size, gender, sortPreference);

		scan.close();

	}

}
