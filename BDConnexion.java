package e_learning;

import java.sql.*;
public class BDConnexion {

	public BDConnexion() {
		
	}
	
	public Statement etablirConnexion() throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/e_learning", "root", "");
		System.out.println("Connected With the database successfully ");
	    Statement state  = connection.createStatement();
		return state;
	}
	
	public ResultSet getData(Statement state, String requete) throws SQLException {
		ResultSet rs = state.executeQuery(requete);
		ResultSetMetaData resultMeta = rs.getMetaData();	
		return rs;
	}
	
	public void setData(Statement state, String requete) throws SQLException {	
		state.executeUpdate(requete);
	}

	public static void main(String[] args) {
		   try {
			   BDConnexion bd = new BDConnexion();
			   Statement state = bd.etablirConnexion();
			   
			   //bd.setData(state, "INSERT INTO `professeur`(`loginP`, `passwordP`, `matiere`) VALUES('khalid', '123', 'français')");
			   ResultSet res = bd.getData(state, "SELECT loginP FROM professeur where id_prof = 1");

				while(res.next()) {
					System.out.println(res.getString("loginP"));
				}
		   } catch (SQLException e) {
			   System.out.println("Error while connecting to the database");
			   e.getMessage();
			   e.getErrorCode();
		   }
	}
}
