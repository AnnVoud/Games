package be.belfius.games.exceptions;

public class RepoException extends Exception {
	String myRepoMessage;

	public RepoException(String message) {
		myRepoMessage=message;
	}

	public String getMyRepoMessage() {
		return myRepoMessage;
	}

	public void setMyRepoMessage(String myRepoMessage) {
		this.myRepoMessage = myRepoMessage;
	}

	public String getMessage() {
		return "Problem during accessing DB : " + myRepoMessage;
	}		
}
