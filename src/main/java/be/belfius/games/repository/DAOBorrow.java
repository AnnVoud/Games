package be.belfius.games.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

import be.belfius.games.domain.Borrow;
import be.belfius.games.domain.Borrower;
import be.belfius.games.exceptions.RepoException;

public class DAOBorrow {
	
	public DAOBorrow () {
	}
		
	// OPTION 7A : LIST BORROWED GAMES
	public ArrayList<Borrow> showBorrowedGames() throws RepoException{

		ArrayList<Borrow> borrowList=new ArrayList<>();

		try (Statement s = new DAOGameRepository().connectGameDB().createStatement()) {
			
			s.execute("select B.* from borrow B, borrower P where B.borrower_id=P.id order by P.borrower_name, B.borrow_date");
			
			ResultSet rs = s.getResultSet();
			
			while (rs.next()) {
				Borrow borrow=new Borrow();
				
				borrow.setId(rs.getInt("id"));
				borrow.setGame_id(rs.getInt("game_id"));
				borrow.setBorrower_id(rs.getInt("borrower_id"));
				borrow.setBorrow_date(rs.getDate("borrow_date"));				
				borrow.setReturn_date(rs.getDate("return_date"));	
				borrowList.add(borrow);
			}
			
			s.close();
		
		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return borrowList;
	}
	
	// OPTION 7B : BORROWEDGAMES BY BORROWER 
	public ArrayList<Borrow> showBorrowedGamesByBorrower(Borrower myBorrower) throws RepoException {
		ArrayList<Borrow> borrowList=new ArrayList<>();
		
		try (PreparedStatement s = new DAOGameRepository().connectGameDB().prepareStatement("select B.* from borrow B, borrower P where (B.borrower_id=P.id) and (B.borrower_id = ?) order by P.borrower_name, B.borrow_date")) {
			
			s.setInt(1, myBorrower.getId());
			
			ResultSet rs = s.executeQuery();
			
			while (rs.next()) {
				Borrow borrow=new Borrow();
				
				borrow.setId(rs.getInt("id"));
				borrow.setGame_id(rs.getInt("game_id"));
				borrow.setBorrower_id(rs.getInt("borrower_id"));
				borrow.setBorrow_date(rs.getDate("borrow_date"));				
				borrow.setReturn_date(rs.getDate("return_date"));	
				borrowList.add(borrow);
			}
			
			s.close();
		
		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return borrowList;
	}
	
	//OPTION 10 - BORROWED GAMES TODAY
	public ArrayList<Borrow> showBorrowedGames(LocalDateTime today) throws RepoException{

		ArrayList<Borrow> borrowList=new ArrayList<>();
		try (Statement s = new DAOGameRepository().connectGameDB().createStatement()){			

			s.execute("select B.* from borrow B where B.borrow_date < now() AND return_date is null order by B.borrow_date");
			
			ResultSet rs = s.getResultSet();
			
			while (rs.next()) {
				Borrow borrow=new Borrow();
				
				borrow.setId(rs.getInt("id"));
				borrow.setGame_id(rs.getInt("game_id"));
				borrow.setBorrower_id(rs.getInt("borrower_id"));
				borrow.setBorrow_date(rs.getDate("borrow_date"));				
				borrow.setReturn_date(rs.getDate("return_date"));	
				borrowList.add(borrow);
			}
			
			s.close();
		
		} catch (SQLException e) {
			throw new RepoException(e.getMessage());
		}
		return borrowList;
	}
}
