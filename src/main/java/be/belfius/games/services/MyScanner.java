package be.belfius.games.services;
import java.util.Scanner;

public class MyScanner {
	
	static public Scanner scanner=new Scanner(System.in);
	
	public MyScanner() {
		super();
//		System.out.println("in constructor of MyScanner");
	}

	public int receiveInt(String iQuestion) {
		System.out.println(iQuestion);
		int iReturn = scanner.nextInt();
//		scanner.close();
		return iReturn;
	}
	
	public double receiveDouble(String iQuestion) {
			System.out.println(iQuestion);
			double iReturn = scanner.nextDouble();
//   		scanner.close();
			return iReturn;
	}
	
	public String receiveString(String iQuestion) {
			if (iQuestion != "")
			System.out.println(iQuestion);
			String iReturn = scanner.nextLine();
//			scanner.close();
			return iReturn;
	}
}

