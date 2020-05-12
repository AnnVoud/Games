package be.belfius.games;

import be.belfius.games.exceptions.RepoException;
import be.belfius.games.services.GameService;

public class GamesApplication {
	public static GameService myGameService;
	
	public static void main(String[] args) {
		
		System.out.println("Welcome to our GAME-applic : VanOudenhove_Ann_Games");
		
		try {
			myGameService=new GameService();
		} catch (RepoException e) {
			e.getMessage();
		}
		new Menu().loopMenu(myGameService);
		
	}

}
