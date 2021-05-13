package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode, StateAbb, StateNme FROM country ORDER BY StateAbb";
		List<Country> result = new ArrayList<Country>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				System.out.format("%d %s %s\n", rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}

	public void loadAllCountry(Map<Integer, Country> countrys, int anno) {
		// TODO Auto-generated method stub
		String sql = "SELECT  DISTINCT c.ccode, c.StateAbb, c.StateNme  "
				+ "FROM country c ,contiguity cg "
				+ "WHERE cg.state1no=c.CCode "
				+ "AND cg.year<=? "
				+ "AND cg.conttype=1 "
				+ "ORDER BY StateAbb";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country( rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				countrys.put(c.getId(),c);
			}
			
			conn.close();
			return ;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

	public void loadAllBorder(Set<Border> borders, Map<Integer, Country> countrys, int anno) {
		String sql = "SELECT  DISTINCT cg.state1no as c1,cg.state2no as c2 "
				+ "FROM contiguity cg "
				+ "WHERE cg.year<=? "
				+ "AND cg.conttype=1 "
				+ "ORDER BY cg.state1ab";
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Border b= new Border(countrys.get(rs.getInt("c1")),countrys.get(rs.getInt("c2")));
				borders.add(b);
			}
			
			conn.close();
			return ;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
}
