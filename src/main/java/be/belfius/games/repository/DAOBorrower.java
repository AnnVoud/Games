package be.belfius.games.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import be.belfius.games.domain.Borrower;
import be.belfius.games.exceptions.RepoException;

public class DAOBorrower {
	
	// OPTION 3 : INFO FIRST BORROWER
	public Borrower showFirstBorrower() throws RepoException{
		Borrower borrower=new Borrower();
		try (Statement s = new DAOGameRepository().connectGameDB().createStatement()) {
			
			s.execute("select * from borrower where id = 1");
			
			ResultSet rs = s.getResultSet();
			
			if (rs != null) {
				rs.next();
				
				borrower.setId(rs.getInt("id"));
				borrower.setBorrower_name(rs.getString("borrower_name"));
				borrower.setStreet(rs.getString("street"));
				borrower.setHouse_number(rs.getString("house_number"));
				borrower.setBus_number(rs.getString("bus_number"));
				borrower.setPostcode(rs.getInt("postcode"));
				borrower.setCity(rs.getString("city"));
				borrower.setTelephone(rs.getString("telephone"));
				borrower.setEmail(rs.getString("email"));
			}
			
			s.close();
		
		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return borrower;
	}

	// OPTION 7A : INFO BORROWER FOR LIST BORROWED GAMES
	public Borrower showBorrowerbyId(int borrowerId) throws RepoException {
		Borrower borrower=new Borrower();
		
		try (PreparedStatement s = new DAOGameRepository().connectGameDB().prepareStatement("Select * from borrower where id = ?")) {			
			s.setInt(1, borrowerId);
			
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				borrower.setId(rs.getInt("id"));
				borrower.setBorrower_name(rs.getString("borrower_name"));
				borrower.setStreet(rs.getString("street"));
				borrower.setHouse_number(rs.getString("house_number"));
				borrower.setBus_number(rs.getString("bus_number"));
				borrower.setPostcode(rs.getInt("postcode"));
				borrower.setCity(rs.getString("city"));
				borrower.setTelephone(rs.getString("telephone"));
				borrower.setEmail(rs.getString("email"));
			}
			
			s.close();
			
		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		
		return borrower;		
	}

	// OPTION 7B : BORROWEDGAMES BY BORROWER - LIST OF BORROWERS
	// OPTION 9 : LIST OF BORROWERS - SEARCH ON (PART OF) NAME
	public ArrayList<Borrower> getAllBorrowers(String inputName) throws RepoException {
		ArrayList<Borrower> foundBorrowers = new ArrayList<Borrower>();

		try (PreparedStatement s = new DAOGameRepository().connectGameDB().prepareStatement("Select * from borrower where borrower_name like ? order by borrower_name")) {			

			String temp = "%" + inputName.toUpperCase() + "%";
			s.setString(1, temp);

			ResultSet rs = s.executeQuery();
			
			while (rs.next()) {
				Borrower borrower = new Borrower();
				borrower.setId(rs.getInt("id"));
				borrower.setBorrower_name(rs.getString("borrower_name"));
				borrower.setStreet(rs.getString("street"));
				borrower.setHouse_number(rs.getString("house_number"));
				borrower.setBus_number(rs.getString("bus_number"));
				borrower.setPostcode(rs.getInt("postcode"));
				borrower.setCity(rs.getString("city"));
				borrower.setTelephone(rs.getString("telephone"));
				borrower.setEmail(rs.getString("email"));
				foundBorrowers.add(borrower);
			}

			s.close();

		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return foundBorrowers;
	}

	// OPTION 7B : BORROWEDGAMES BY BORROWER - SEARCH BORROWER BY NAME
	public Borrower findBorrowerbyName(String myInputString) throws RepoException {
		Borrower borrower=new Borrower();
		
		try (PreparedStatement s = new DAOGameRepository().connectGameDB().prepareStatement("Select * from borrower where UPPER(borrower_name) like ?")) {
			myInputString="%" + myInputString.toUpperCase() + "%";			
			s.setString(1, myInputString);
			
			ResultSet rs = s.executeQuery();
			if (rs.next()) {
				borrower.setId(rs.getInt("id"));
				borrower.setBorrower_name(rs.getString("borrower_name"));
				borrower.setStreet(rs.getString("street"));
				borrower.setHouse_number(rs.getString("house_number"));
				borrower.setBus_number(rs.getString("bus_number"));
				borrower.setPostcode(rs.getInt("postcode"));
				borrower.setCity(rs.getString("city"));
				borrower.setTelephone(rs.getString("telephone"));
				borrower.setEmail(rs.getString("email"));
			}
			
			s.close();
			
		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		
		return borrower;		

	}

}

