/**
 * 
 * ThesaurusDao class provides database access
 * 
 * Uses JDBC for connection with database
 * @author Sumanth lakshminarayana(1000830230)
 *
 */

package com.client.server;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ThesaurusDao
{
	/*
	 * Method which returns JDBC connection object to your MYSQL DB.
	 */
	private Connection getConnection() throws Exception
	{   //Declaration of database properties
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/";
		String db = "thesaurus";
		String driver = "com.mysql.jdbc.Driver";
		String user = "root";
		String pass = "";
		try
		{
			Class.forName(driver);
			con = DriverManager.getConnection(url + db, user, pass);
		}
		catch (ClassNotFoundException e)
		{
			throw new Exception("Some problem with database!");
		}
		catch (SQLException e)
		{
			throw new Exception("Some problem with database!");
		}
		return con;
	}

	/*
	 * This method returns the Synonyms for input word
	 */
	public StringBuilder getSynonyms(String selectedWord) throws Exception
	{
		StringBuilder synonym = null ;
		Statement st = null;
		ResultSet res;
		try
		{
			
			st = getConnection().createStatement();
			if(selectedWord.contains("'") || selectedWord.contains("\""))
			{
				selectedWord=selectedWord.replace("'", "");
				selectedWord=selectedWord.replace("\"", "");
			}
			//Querying to search the synonyms in database for the selected word
			 
			res = st.executeQuery("SELECT * FROM  dictionary where input_word ='"+ selectedWord + "'");
			while (res.next())
				synonym = new StringBuilder(res.getString("value"));
		}
		catch (Exception s)
		{
			throw new Exception("Error with database!");
		}
		finally
		{
			try
			{
				if(st!=null)
					st.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return synonym;
	}
}