package com.nagarro.nagp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FindItems {

	protected static ResultSet rs = null;
	protected static ResultSet rs1 = null;
	private static PreparedStatement st = null;

	public static void searchProduct(String color, String size, String gender, String selectPreference)
			throws SQLException {

		
		/* Trigger select query's order by column based on preference selected */
		
	 if(selectPreference.equalsIgnoreCase("P")) {
		 st = DataBaseConnectivity.con.prepareStatement(
					"select * from Tshirts where colour=? and SIZE=? and GENDER_RECOMMENDATION=? order by PRICE ASC");
			st.setString(1, color.toUpperCase());
			st.setString(2, size.toUpperCase());
			st.setString(3, gender.toUpperCase());
			// st.setString(4, selectPreference.toUpperCase()); 
	 }else if 
	 (selectPreference.equalsIgnoreCase("R")) {
		 st = DataBaseConnectivity.con.prepareStatement(
					"select * from Tshirts where colour=? and SIZE=? and GENDER_RECOMMENDATION=? order by RATING ASC");
			st.setString(1, color.toUpperCase());
			st.setString(2, size.toUpperCase());
			st.setString(3, gender.toUpperCase());
			// st.setString(4, selectPreference.toUpperCase()); 
	 }
	 else if 
	 (selectPreference.equalsIgnoreCase("PR")) {
		 st = DataBaseConnectivity.con.prepareStatement(
					"select * from Tshirts where colour=? and SIZE=? and GENDER_RECOMMENDATION=? order by PRICE,RATING ASC");
			st.setString(1, color.toUpperCase());
			st.setString(2, size.toUpperCase());
			st.setString(3, gender.toUpperCase());
			// st.setString(4, selectPreference.toUpperCase()); 
	 }
	 else   {
		 st = DataBaseConnectivity.con.prepareStatement(
					"select * from Tshirts where colour=? and SIZE=? and GENDER_RECOMMENDATION=?  ");
			st.setString(1, color.toUpperCase());
			st.setString(2, size.toUpperCase());
			st.setString(3, gender.toUpperCase());
			// st.setString(4, selectPreference.toUpperCase()); 
	 }

		rs = st.executeQuery();
		FindItems.convertResultSetToList(rs);

		rs1 = st.executeQuery();

		if (rs1.next()) {

			System.out.println("****Happy shopping ! We wish to see you back****");
		}

		else {

			System.out.println("Sorry!, No items found .Please refine your search criteria to show matching results.");
		}
		
		DataBaseConnectivity.con.close();
	}

	public static void convertResultSetToList(ResultSet rs) throws SQLException {
		
		ResultSetMetaData metaData = rs.getMetaData();
		int noColumns = metaData.getColumnCount();

		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		while (rs.next()) {
			
			HashMap<String, Object> rsRow = new HashMap<String, Object>(noColumns);
			
			
			for (int i = 1; i <= noColumns; ++i) {
				rsRow.put(metaData.getColumnName(i), rs.getObject(i));
			}
			list.add(rsRow);
			
			 
		}
		
		list.stream().forEach(item->System.out.println(item));
		 
		 
	
	}
	
	
}
