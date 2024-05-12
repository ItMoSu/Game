package utama;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import data.Inventory;
import data.PlayerAttributes;
import data.User;

public class UserItem {
	Scanner scan =  new Scanner(System.in);
	User help = new User("","",0,0);
	
	
	public UserItem(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
		readFile(userId, userdata, information, bag);
		showInventory(userId, userdata, information, bag);
	}
	
	public void readFile(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
		String files = "src/file/Useritem.txt";
	    
	    try (BufferedReader br = new BufferedReader(new FileReader(files))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split("#");
	            String id = data[0];
	            String name = data[1];
	            String type = data[2];
	            int price = Integer.parseInt(data[3]);
	            double damageOrDeflect = Double.parseDouble(data[4]);
	            int manaOrMaxUse = Integer.parseInt(data[5]);
	            int useLeft = Integer.parseInt(data[6]);
	            
	            int check = Integer.parseInt(data[7]);
	            boolean sameornot = true;
	            if(!bag.isEmpty()) {
		            for(int i=0; i<bag.size(); i++) {
		            	if(bag.get(i).getId().equals(id)) {
		            		sameornot = false;
		            		break;
		            	}
		            }
	            }
	            if(check == userId && sameornot){
	            	Inventory items = new Inventory(id, name, price, type, damageOrDeflect, manaOrMaxUse, useLeft, check);
		            bag.add(items);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void showInventory(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
	    if(bag.isEmpty()) {
	    	System.out.println("INVENTORY\n");
		    System.out.println(information.get(userId).getName()+"'s Items");
		    System.out.println("==================================================================================");
		    System.out.println("| ID        | Name            | Type       | Price | Damage | Max Use | Use Left |");
		    System.out.println("==================================================================================");
		    System.out.println("|			Your Inventory is still empty!				|");
		    System.out.println("==================================================================================\n");
		    
		    System.out.print("Press enter to continue...");
		    scan.nextLine();
		    help.space();
			MapRender back = new MapRender(userId, userdata, information, bag);
	    }else {
	    	System.out.println("INVENTORY\n");
		    System.out.println(information.get(userId).getName()+"'s Items");
		    System.out.println("==================================================================================");
		    System.out.println("| ID        | Name            | Type       | Price | Damage | Max Use | Use Left |");
		    System.out.println("==================================================================================");

		    for(int i=0; i < bag.size(); i++) {
		    	if(userId == bag.get(i).getOwner()) {
		    		System.out.printf("| %-9s | %-15s | %-10s | %-5d | %-7.2f | %-7d | %-7d |\n", 
	                          bag.get(i).getId(), 
	                          bag.get(i).getName(), 
	                          bag.get(i).getType(), 
	                          bag.get(i).getPrice(), 
	                          bag.get(i).getDamage(), 
	                          bag.get(i).getMaxOrMana(),
	                          bag.get(i).getUseLeft());
		    	}
		    }
		    
		    System.out.println("==================================================================================\n");
		    
		    System.out.print("Press enter to continue...");
		    scan.nextLine();
		    help.space();
			MapRender back = new MapRender(userId, userdata, information, bag);
	    }
	}
}