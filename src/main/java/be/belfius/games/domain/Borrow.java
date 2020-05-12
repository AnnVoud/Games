package be.belfius.games.domain;

import java.sql.Date;

public class Borrow {
	private int id;
	private int game_id;
	private int borrower_id;
	private Date borrow_date;
	private Date return_date;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGame_id() {
		return game_id;
	}
	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}
	public int getBorrower_id() {
		return borrower_id;
	}
	public void setBorrower_id(int borrower_id) {
		this.borrower_id = borrower_id;
	}
	public Date getBorrow_date() {
		return borrow_date;
	}
	public void setBorrow_date(Date borrow_date) {
		this.borrow_date = borrow_date;
	}
	public Date getReturn_date() {
		return return_date;
	}
	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}
	
	@Override
	public String toString() {
		return "Borrow [id=" + id + ", game_id=" + game_id + ", borrower_id=" + borrower_id + ", borrow_date="
				+ borrow_date + ", return_date=" + return_date + "]";
	}

	
}
