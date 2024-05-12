package utama;
import java.util.Scanner;

import java.util.Random;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import data.Inventory;
import data.PlayerAttributes;
import data.User;

public class Games {
	public int userId;
	Scanner scan =  new Scanner(System.in);
	User help = new User("","",0,0);
	
	public Games(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag){
		this.userId = userId;
		help.space();
		game_menu(userdata, information, bag);
	}
	
	public void game_menu(ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
		System.out.println("Welcome To The Game\n~~~~~~~~~~~~~~~~~~~");
		help.sleep();
		System.out.println("1. Start Game");
		System.out.println("2. Game Guide");
		System.out.println("3. Exit Game");
		System.out.print(">> ");
		
		int option = scan.nextInt();
		scan.nextLine();
		if(option == 1) {
			help.space();
			MapRender play = new MapRender(userId, userdata, information, bag);
		}else if (option == 2) {
			help.space();
			game_guide(userdata, information, bag);
		}else if(option == 3) {
			help.space();
			deleteData(userId, userdata);
			Login back = new Login(bag);
		}else{
			help.space();
			game_menu(userdata, information, bag);
		}
	}
	
	public void game_guide(ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
		System.out.println("Game Guide\n\nIn the game you can collect coins as you move. "
				+ "You can also meet enemies while going through the grass in the game. "
				+ "If you have killed an enemy you will be rewarded money that you can use again to buy the item.\n");
		help.sleep();
		help.sleep();
		System.out.println("Character Informations\r\n" + 
				"  Coin : O\r\n" + 
				"  Grass : v | V\r\n" + 
				"  Character : X\r\n" + 
				"  Wall : #\r\n" + 
				"\r\n");
		help.sleep();
		help.sleep();
		help.sleep();
		System.out.println("Keybind Information\r\n" + 
				"  w     : Move character up\r\n" + 
				"  a     : Move character left\r\n" + 
				"  s     : Move character down\r\n" + 
				"  d     : Move character right\r\n" + 
				"  i     : Show all character's item\r\n" + 
				"  z     : Shop Menu\r\n" + 
				"  e     : Exit game and save your information\n");
		help.sleep();
		help.sleep();
		help.sleep();
		System.out.println("You can upgrade gears and restore HP in the store...\n"
				+ "Also when entering battle, there is a small chance to meet a very rare type of enemy!");
		help.sleep();
		help.sleep();
		
		System.out.print("Press enter to continue...");
		scan.nextLine();
		help.space();
		game_menu(userdata, information, bag);
	}
	
	public void deleteData(int userId, ArrayList<User> userdata) {
	    File file = new File(userId+ ".ser");
	        if(file.delete()){
	        	//data berhasil dihapus
	        	userdata.get(userId).setPosX(150);
	        	userdata.get(userId).setPosY(150);
	        }
	}
	
}
