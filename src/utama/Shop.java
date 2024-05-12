package utama;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.*;

import data.PlayerAttributes;
import data.SpellItem;
import data.DefenItem;
import data.Inventory;
import data.OffenItem;
import data.User;

public class Shop {
	Scanner scan = new Scanner(System.in);
	Random rand = new Random();
	User help = new User("","",0,0);
	
	ArrayList<OffenItem> shop1 = new ArrayList<>();
	ArrayList<DefenItem> shop2 = new ArrayList<>();
	ArrayList<SpellItem> shop3 = new ArrayList<>();
	ArrayList<Inventory> bag = new ArrayList<>();
	
	boolean readed = false;
	public Shop(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information) {
		if(!readed) readFile(userId, userdata, information);
		menu(userId, userdata, information);
	}
	
	public void readFile(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information) {
		String file = "src/file/item.txt"; //tolong diubah sesuai path file .txt nya untuk function readFile
		
		readed = true;
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			String line;
			try{
				while((line = br.readLine())!= null) {
					String[] temp = line.split("#");
					String id = temp[0];
					String name = temp[1];
					String checking = temp[2];
					int price = Integer.parseInt(temp[3]);
					int damage = Integer.parseInt(temp[4]);
					int max_use = Integer.parseInt(temp[5]);
					
		        	if(checking.equals("offensive")) {
		        		OffenItem item1 = new OffenItem(id, name, price, damage, max_use);
		        		shop1.add(item1);
		        	}else if(checking.equals("defensive")) {
		        		DefenItem item2 = new DefenItem(id, name, price, damage, max_use);
		        		shop2.add(item2);
		        	}else if(checking.equals("spell")) {
		        		SpellItem item3 = new SpellItem(id, name, price, damage, max_use);
		        		shop3.add(item3);
		        	}	
		        }
			}catch(IOException e) {
				e.printStackTrace();
			}
			br.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
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
	            int tempId = Integer.parseInt(data[7]); 
	            
	            Inventory items = new Inventory(id, name, price, type, damageOrDeflect, manaOrMaxUse, useLeft, tempId);
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	public void menu(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information) {
		System.out.println("Shop Menu\n~~~~~~~~~");
		help.sleep();
		System.out.println("1. Buy Offensive Item");
		System.out.println("2. Buy Defensive Item");
		System.out.println("3. Buy Spells Item");
		System.out.println("4. Replenish Life (Health+)");
		System.out.println("5. Upgrade Gears (Damage+)");
		System.out.println("6. Exit");
		System.out.print(">> ");
		
		int option = scan.nextInt();
		scan.nextLine();
		if(option == 1) {
			help.space();
			buy_offensive(userId, userdata, information);
		}else if (option == 2) {
			help.space();
			buy_defensive(userId, userdata, information);
		}else if(option == 3) {
			help.space();
			buy_spells(userId, userdata, information);
		}else if(option == 4) {
			help.space();
			ReplenishLife(userId, userdata, information);
		}else if(option == 5) {
			help.space();
			UpgradeGears(userId, userdata, information);
		}else if(option == 6) {
			saving1(userId, userdata, information);
			saving2(userId);
			help.space();
			MapRender back = new MapRender(userId, userdata, information, bag);
		}else{
			help.space();
			menu(userId, userdata, information);
		}
	}
	
		public void ReplenishLife(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information) {
		   	System.out.println("HEALTH+\n\nWould you like to spend money to increase your life?");
		    System.out.println("1. Increase 15 health for 30 money");
		    System.out.println("2. Increase 50 health for 70 money");
		    System.out.println("3. Increase 170 health for 200 money");
		    System.out.println("4. Go back");
		    System.out.print(">> ");
		    
		    int option = scan.nextInt();
		    scan.nextLine();
		    if(option >= 1 && option <= 3) {
		        int cost = 0;
		        double healthIncrease = 0;
		        switch (option) {
		            case 1:
		                cost = 30;
		                healthIncrease = 15;
		                break;
		            case 2:
		                cost = 70;
		                healthIncrease = 50;
		                break;
		            case 3:
		                cost = 200;
		                healthIncrease = 170;
		                break;
		        }
		        if(information.get(userId).getMoney() >= cost) {
		            double currentHealth = information.get(userId).getHealth();
		            information.get(userId).setHealth(currentHealth + healthIncrease);
		            int remainingMoney = information.get(userId).getMoney() - cost;
		            information.get(userId).setMoney(remainingMoney);
		            System.out.println("Health is increased by " + healthIncrease + ".\n");
		        }else {
		            System.out.println("You don't have enough money.\n");
		        }
		    }else if (option == 4) {
		        help.space();
		        menu(userId, userdata, information);
		    }else {
		        System.out.println("Invalid option.\n");
		    }
		    System.out.print("Press enter to continue...");
		    scan.nextLine();
		    help.space();
		    ReplenishLife(userId, userdata, information);
		}

		public void UpgradeGears(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information) {
		    System.out.println("DAMAGE+\n\nWould you like to spend money to increase your base damage?");
		    System.out.println("1. Increase 5 damage for 30 money");
		    System.out.println("2. Increase 20 damage for 100 money");
		    System.out.println("3. Increase 50 damage for 220 money");
		    System.out.println("4. Go back");
		    System.out.print(">> ");
		    
		    int option = scan.nextInt();
		    scan.nextLine();
		    if(option >= 1 && option <= 3) {
		        int cost = 0;
		        double damageIncrease = 0;
		        switch (option) {
		            case 1:
		                cost = 30;
		                damageIncrease = 5;
		                break;
		            case 2:
		                cost = 100;
		                damageIncrease = 20;
		                break;
		            case 3:
		                cost = 220;
		                damageIncrease = 50;
		                break;
		        }
		        if(information.get(userId).getMoney() >= cost) {
		            double currentDamage = information.get(userId).getDamage();
		            information.get(userId).setDamage(currentDamage + damageIncrease);
		            int remainingMoney = information.get(userId).getMoney() - cost;
		            information.get(userId).setMoney(remainingMoney);
		            System.out.println("Base damage is increased by " + damageIncrease + ".\n");
		        }else {
		            System.out.println("You don't have enough money.\n");
		        }
		    }else if (option == 4) {
		        help.space();
		        menu(userId, userdata, information);
		    }else {
		        System.out.println("Invalid option.\n");
		    }
		    System.out.print("Press enter to continue...");
		    scan.nextLine();
		    help.space();
		    UpgradeGears(userId, userdata, information);
		}

	
	public void buy_offensive(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information) {
		System.out.println("OFFENSIVE\n\nYour money is "+information.get(userId).getMoney());
		
		System.out.println("=========================================================================");
		System.out.println("|ID\t\t |Name\t\t|Type\t\t|Price\t|Damage\t|Max Use|");
		System.out.println("=========================================================================");
		for(int i=0; i<shop1.size(); i++) {
			System.out.println("|"+shop1.get(i).getId()+"  \t |"+shop1.get(i).getName()+"\t|Offensive \t|"+shop1.get(i).getPrice()+"\t|" 
					+shop1.get(i).getDamage()+"\t|"+shop1.get(i).getMaxUse()+"\t|");
			
		}
		System.out.println("=========================================================================\n");

		System.out.print("Input Item's ID ('Exit' to cancel) : ");
        String itemId = scan.next();
        scan.nextLine();
        if(itemId.equalsIgnoreCase("Exit")) {
			help.space();
			menu(userId, userdata, information);
		}
        
        boolean itemFound = false;
        for(OffenItem item : shop1) {
        	if(item.getId().equalsIgnoreCase(itemId)) {
                itemFound = true;
                if(information.get(userId).getMoney() >= item.getPrice()) {
                	if(!bag.isEmpty()) {
                		for(int i=0; i< bag.size(); i++) {
                			if(bag.get(i).getId().equalsIgnoreCase(itemId) && userId == bag.get(i).getOwner()){
    	                    	bag.get(i).setUseLeft(item.getMaxUse());
    	                    	int uang = information.get(userId).getMoney() - item.getPrice();
    		                    information.get(userId).setMoney(uang);
    	                        System.out.println("You have the item and its a offensive type, resetting its usage amount.\n");
    	                        break;
                			}
                		}
                	}else {
                		Inventory items = new Inventory(item.getId(), item.getName(), item.getPrice(), "Offensive", item.getDamage(), item.getMaxUse(), item.getMaxUse(), userId);
	                    bag.add(items);
	                    int uang = information.get(userId).getMoney() - item.getPrice();
	                    information.get(userId).setMoney(uang);
	                    System.out.println("Item purchased successfully.\n");
	                }
             }else {
                  System.out.println("You don't have enough money.\n");
             }
             break;
          }
        }
        if(!itemFound) {
            System.out.println("Item not found.\n");
        }
        
        System.out.print("Press enter to continue...");
	    scan.nextLine();
        help.space();
        menu(userId, userdata, information);
	}
	
	public void buy_defensive(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information) {
	    System.out.println("DEFENSIVE\n");
	    System.out.println("Your money is " + information.get(userId).getMoney());
	    System.out.println("========================================================================");
	    System.out.println("| ID        | Name            | Type       | Price | Deflect | Max Use |");
	    System.out.println("========================================================================");

	    for (int i = 0; i < shop2.size(); i++) {
	        System.out.printf("| %-9s | %-15s | %-10s | %-5d | %-7d | %-7d |\n", 
	                          shop2.get(i).getId(), 
	                          shop2.get(i).getName(), 
	                          "Defensive", 
	                          shop2.get(i).getPrice(), 
	                          shop2.get(i).getDeflect(), 
	                          shop2.get(i).getMaxUse());
	    }
	    
	    System.out.println("========================================================================\n");
	    
	    System.out.print("Input Item's ID ('Exit' to cancel) : ");
	    String itemId = scan.next();
	    scan.nextLine();
	    if(itemId.equalsIgnoreCase("Exit")) {
	        help.space();
	        menu(userId, userdata, information);
	    }
	    
	    boolean itemFound = false;
        for(DefenItem item : shop2) {
        	if(item.getId().equalsIgnoreCase(itemId)) {
                itemFound = true;
                if(information.get(userId).getMoney() >= item.getPrice()) {
                	if(!bag.isEmpty()) {
                		for(int i=0; i< bag.size(); i++) {
                			if(bag.get(i).getId().equalsIgnoreCase(itemId) && userId == bag.get(i).getOwner()){
    	                    	bag.get(userId).setUseLeft(item.getMaxUse());
    	                    	int uang = information.get(userId).getMoney() - item.getPrice();
    		                    information.get(userId).setMoney(uang);
    	                    	System.out.println("You have the item and its a defensive type, resetting its usage amount.\n");
    	                        break;
                			}
                		}
                	}else {
                		Inventory items = new Inventory(item.getId(), item.getName(), item.getPrice(), "Defensive", item.getDeflect(), item.getMaxUse(), item.getMaxUse(), userId);
	                    bag.add(items);
	                    int uang = information.get(userId).getMoney() - item.getPrice();
	                    information.get(userId).setMoney(uang);
	                    System.out.println("Item purchased successfully.\n");
	                }    
             }else {
                  System.out.println("You don't have enough money.\n");
             }
             break;
          }
        }
        if(!itemFound) {
            System.out.println("Item not found.\n");
        }
	    
	    System.out.print("Press enter to continue...");
	    scan.nextLine();
	    help.space();
	    menu(userId, userdata, information);
	}

	public void buy_spells(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information) {
	    System.out.println("SPELLS\n");
	    System.out.println("Your money is " + information.get(userId).getMoney());
	    System.out.println("========================================================================");
	    System.out.println("| ID        | Name             | Type       | Price | Damage | Mana    |");
	    System.out.println("========================================================================");

	    for (int i = 0; i < shop3.size(); i++) {
	        System.out.printf("| %-9s | %-16s | %-10s | %-5d | %-6d | %-7d |\n", 
	                          shop3.get(i).getId(), 
	                          shop3.get(i).getName(), 
	                          "Spell", 
	                          shop3.get(i).getPrice(), 
	                          shop3.get(i).getDamage(), 
	                          shop3.get(i).getMana());
	    }
	    
	    System.out.println("========================================================================\n");
	    
	    System.out.print("Input Item's ID ('Exit' to cancel) : ");
	    String itemId = scan.next();
	    scan.nextLine();
	    if(itemId.equalsIgnoreCase("Exit")) {
	        help.space();
	        menu(userId, userdata, information);
	    }
	    	
	    boolean itemFound = false;
	    for(SpellItem item : shop3) {
        	if(item.getId().equalsIgnoreCase(itemId)) {
                itemFound = true;
                if(information.get(userId).getMoney() >= item.getPrice()) {
                	if(!bag.isEmpty()) {
                		for(int i=0; i< bag.size(); i++) {
                			if(bag.get(i).getId().equalsIgnoreCase(itemId) && userId == bag.get(i).getOwner()){
    	                    	System.out.println("Item is a spell type, You can't have more than one spell item.\n");
    	                        break;
                			}
                		}
                	}else {
                		Inventory items = new Inventory(item.getId(), item.getName(), item.getPrice(), "Spell", item.getDamage(), item.getMana(), item.getMana(), userId);
	                    bag.add(items);
	                    int uang = information.get(userId).getMoney() - item.getPrice();
	                    information.get(userId).setMoney(uang);
	                    System.out.println("Item purchased successfully.\n");
	                }       
             }else {
                  System.out.println("You don't have enough money.\n");
             }
             break;
          }
	    }
        if(!itemFound) {
            System.out.println("Item not found.\n");
        }
	    
	    System.out.print("Press enter to continue...");
	    scan.nextLine();
	    help.space();
	    menu(userId, userdata, information);
	    
	}
	
	public void saving1(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information) {
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
	
	public void saving2(int userId) {
	    String file = "src/file/Useritem.txt";
	    try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
	        for(int i = 0; i < bag.size(); i++) {
	            Inventory item = bag.get(i);

	            String line = "";
	            if (item.getType().equalsIgnoreCase("spell")) {
	                line = item.getId() + "#" + item.getName() + "#" + item.getType() + "#" +
	                        item.getPrice() + "#" + item.getDamage() + "#" + item.getMaxOrMana() + "#" + item.getUseLeft() + "#" + userId;
	            } else if (item.getType().equalsIgnoreCase("offensive") || item.getType().equalsIgnoreCase("defensive")) {
	                line = item.getId() + "#" + item.getName() + "#" + item.getType() + "#" +
	                        item.getPrice() + "#" + item.getDamage() + "#" + item.getMaxOrMana() + "#" + item.getUseLeft() + "#" + userId;
	            }
	            bw.write(line);

	            if (i != bag.size()-1)
	                bw.newLine();
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
