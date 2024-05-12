package utama;
import java.util.ArrayList;
import java.util.Scanner;

import data.Inventory;
import data.User;

public class Main {
	Scanner scan =  new Scanner(System.in);
	User help = new User("","",0,0);
	ArrayList<Inventory> Hi = new ArrayList<>();
	
	public void welcoming() {
		System.out.println("\n\nWelcome To DOTW\r\n");
		help.sleep();
		System.out.print("Press enter to continue...");
		scan.nextLine();
		System.out.println("\n");
		Login user = new Login(Hi);
	}
	
	public Main() {
		welcoming();
	}

	public static void main(String[] args) {
		new Main();
	}
}
