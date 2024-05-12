package utama;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.File;

import data.Inventory;
import data.Player;
import data.PlayerAttributes;
import data.User;

public class Login {
	Scanner scan =  new Scanner(System.in);
	Random rand = new Random();
	public String tempEmail, tempPass;
	ArrayList<User> userdata = new ArrayList<>();
	ArrayList<PlayerAttributes> information = new ArrayList<>();
	public int userIdx, i, x=0;
	User help = new User("","",0,0);
	
	public Login(ArrayList<Inventory> bag) {
		help.space();
		read_file(bag);
		menu(bag);
	}
	
	public void read_file(ArrayList<Inventory> bag) {
		String file = "src/file/Userdata.txt"; //tolong diubah sesuai path file Userdata.txt nya untuk function read_file dan register
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			String line;
			try{
				while((line = br.readLine())!= null) {
					String[] temp = line.split("#");
					tempEmail = temp[0];
					tempPass = temp[1];
					double tempdamage = Double.parseDouble(temp[2]);
					double tempmana = Double.parseDouble(temp[3]);
					double temphealth = Double.parseDouble(temp[4]);
					int tempmoney = Integer.parseInt(temp[5]);
					
					User newAccount = new User(tempEmail, tempPass, 150, 150);
					userdata.add(newAccount);
					
					String[] nama = tempEmail.split("@");
					String tampilanNama = nama[0];
		        	
					PlayerAttributes info = new PlayerAttributes(tampilanNama, tempdamage, tempmana, temphealth, tempmoney);
					information.add(info);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void menu(ArrayList<Inventory> bag) {
		System.out.println("Login Menu\n~~~~~~~~~~");
		help.sleep();
		System.out.println("1. Login");
		System.out.println("2. Register");
		System.out.println("3. Exit");
		System.out.print(">> ");
		
		int option = scan.nextInt();
		scan.nextLine();
		if(option == 1) {
			help.space();
			login(bag);
		}else if (option == 2) {
			help.space();
			register(bag);
		}else if(option == 3) {
			System.out.println("\nThanks for playing my game!");
			saving();
			help.sleep();
			System.exit(0);
		}else{
			help.space();
			menu(bag);
		}
	}
		
	public void login(ArrayList<Inventory> bag) {
		System.out.println("Input 'Exit' to quit from login menu\n");	
		System.out.print("Input new email : ");
		tempEmail = scan.next();
		scan.nextLine();
		if(tempEmail.equalsIgnoreCase("Exit")) {
			help.space();
			menu(bag);
		}
			
		System.out.print("Input new password : ");
		tempPass = scan.next();
		scan.nextLine();
		if(tempPass.equalsIgnoreCase("Exit")) {
			help.space();
			menu(bag);
		}
			
		i=0;
		for(User in: userdata) {
			if(userdata.get(i).getEmail().equals(tempEmail) && userdata.get(i).getPassword().equals(tempPass)) {
				userIdx = i;
				
				System.out.println("Logging in...\n");
				System.out.print("Press enter to continue...");
				scan.nextLine();
				help.space();
				Games user = new Games(userIdx, userdata, information, bag);
				break;
			}
			i++;
		}
			
		System.out.println("Incorrect credential\n");
		System.out.print("Press enter to continue...");
		scan.nextLine();
		help.space();
		login(bag);
	}
		
	public void register(ArrayList<Inventory> bag) {
		System.out.println("Register Menu\n~~~~~~~~~~~~~");
		System.out.println("Input 'Exit' to quit from register menu\n");
		System.out.print("Input new email : ");
		tempEmail = scan.next();
		scan.nextLine();
		if(tempEmail.equalsIgnoreCase("Exit")) {
			help.space();
			menu(bag);
		}
			
		System.out.print("Input new password : ");
		tempPass = scan.next();
		scan.nextLine();
		if(tempPass.equalsIgnoreCase("Exit")) {
			help.space();
			menu(bag);
		}
			
		int idxA = tempEmail.indexOf('@');
		if(idxA == -1) {
			System.out.println("Email must have one @\n\n");
			System.out.print("Press enter to continue...");
			scan.nextLine();
			help.space();
			register(bag);
		}
		
		if(idxA != tempEmail.lastIndexOf('@')){
			System.out.println("Email must only contain one @\n\n");
			System.out.print("Press enter to continue...");
			scan.nextLine();
			help.space();
			register(bag);
		}
			
		String username = tempEmail.substring(0, idxA);
		if(username.trim().isEmpty()) {
	       	System.out.println("Email must have a name in it\n\n");
			System.out.print("Press enter to continue...");		
			scan.nextLine();
			help.space();
			register(bag);
		}
			
		String domain = tempEmail.substring(idxA);
		if(domain.equals("@gmail.com") || domain.equals("@yahoo.com")){
		}else{
			System.out.println("Email must have a valid domain\n\n");
			System.out.print("Press enter to continue...");
			scan.nextLine();
			help.space();
			register(bag);
		}
		
		if(!userdata.isEmpty()) {
			for(User in: userdata) {
				if(userdata.get(i).getEmail().equals(tempEmail)) {
					System.out.println("Email already existed\n\n");
					System.out.print("Press enter to continue...");
					scan.nextLine();
					help.space();
					register(bag);
				}
				i++;
			}
		}
		
		int len = tempPass.length();
		if(len < 8 || len > 30) {
			System.out.println("Password must be between 8-30\n\n");
			System.out.print("Press enter to continue...");
			scan.nextLine();
			help.space();
			register(bag);
		}
		
		System.out.println("Register Sucess!\n\n");
		User newAccount = new User(tempEmail, tempPass, 150, 150);
		userdata.add(newAccount);
		
		String[] nama = tempEmail.split("@");
		String tampilanNama = nama[0];
		
		PlayerAttributes info = new PlayerAttributes(tampilanNama, 30.00 , 30.00 , 300.00 , 100);
		information.add(info);
		
		String file = "src/file/Userdata.txt"; //tolong diubah sesuai path file Userdata.txt nya untuk function read_file dan register
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))){
			String line = tempEmail.concat("#").concat(tempPass);
			bw.write("\n"+line+"#30#30#300#100");
			bw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		System.out.print("Press enter to continue...");
		scan.nextLine();
		help.space();
		menu(bag);	
	}
	
	public void saving() {
	    String file = "src/file/Userdata.txt";
	    if(!userdata.isEmpty()) {
	    	try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
		        for(int i = 0; i < userdata.size(); i++) {
		            User user = userdata.get(i);
		            PlayerAttributes info = information.get(i);
	
		            String line = user.getEmail() +"#"+ user.getPassword() +"#" + info.getDamage() +"#"+
		                          info.getMana() +"#"+ info.getHealth() +"#"+ info.getMoney();
		            bw.write(line);
		            
		            if(i != userdata.size()-1)
		            	bw.newLine();
		        }
		    }catch (IOException e) {
		        e.printStackTrace();
		    }
	    }   
	}
}

