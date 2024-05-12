package utama;

import java.util.*;
import java.io.*;

import data.EnemyData;
import data.Inventory;
import data.OffenItem;
import data.PlayerAttributes;
import data.User;

public class Combat{
    Random rand = new Random();
    Scanner scan = new Scanner(System.in);
    ArrayList<EnemyData> enemy = new ArrayList<>();
    User help = new User("","",0,0);
    boolean playerTurn = true;
    boolean errorMessage = false;
    public Combat(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
    	help.space();
    	help.sleep();
    	System.out.println("Wait a second...\n");
    	System.out.print("Press enter to continue...");
    	scan.nextLine();
    	help.space();
    	help.sleep();
    	System.out.println("Oh no! there's an enemy in the grass\n\nChoose your option:");
    	System.out.println("1. Fight it");
    	System.out.println("2. Escape");
    	if(errorMessage) System.out.println("![Please input a valid option]");
    	errorMessage = false;
    	System.out.print(">> ");
    	int option = scan.nextInt();
    	scan.nextLine();
    	if(option == 2) {
    		System.out.println("You have escaped the enemy!\n");
    		System.out.print("Press enter to continue...");
		    scan.nextLine();
		    help.space();
		    MapRender back = new MapRender(userId, userdata, information, bag);
    	}else if(option != 1 && option != 2){
    		errorMessage = true;
    		help.space();
    		new Combat(userId, userdata, information, bag);
    	}
    	
    	readFile(userId, userdata, information, bag);
    	help.space();
    	int eventPicker = rand.nextInt(100)+1;
    	int enemyId = 30;
    	if(eventPicker == 100) {
    		//untuk lawan boss
    		System.out.println("Wait...\nYou have accidentaly encounter an extremely rare enemy!\n\n");
    		System.out.print("Press enter to continue...");
		    scan.nextLine();
		    help.space();
		    System.out.println("Entering a Boss Fight!!!\n\n");
		    help.sleep();
		    help.sleep();
		    System.out.println("Get Ready...");
		    help.sleep();
		    help.space();
		    playerTurn = rand.nextBoolean();
		    startCombat(userId, enemyId, userdata, information, bag);
		    
		    int currMoney = information.get(userId).getMoney();
		    information.get(userId).setMoney(currMoney + 1000);
		    
		    saving1(userId, userdata, information, bag);
	        saving2(userId, userdata, information, bag);
			help.space();
			MapRender back = new MapRender(userId, userdata, information, bag);
    	}else {
    		enemyId = rand.nextInt(30);
    		
    		System.out.println("You have encounter a " + enemy.get(enemyId).getName() + "!");
    		help.sleep();
    		help.space();
    		playerTurn = rand.nextBoolean();
    		startCombat(userId, enemyId, userdata, information, bag);
    		
    		int currMoney = information.get(userId).getMoney();
		    information.get(userId).setMoney(currMoney + 50);
            
            saving1(userId, userdata, information, bag);
            saving2(userId, userdata, information, bag);
    		help.space();
    		MapRender back = new MapRender(userId, userdata, information, bag);
    	}
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
	            	Inventory items = new Inventory(id, name, price, type, damageOrDeflect, manaOrMaxUse, useLeft, userId);
		            bag.add(items);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    	
        String file = "src/file/enemy.txt";
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            int typeEnemy = 0;
            while ((line = br.readLine()) != null) {
                String[] enemyData = line.split("#");
                if (typeEnemy == 0) {
                    for (String name : enemyData) {
                        // Generate random values for strength type enemy
                        double randDamage = rand.nextDouble() * (30 - 20) + 20; // Random damage between 20 to 30
                        double randHealth = (rand.nextInt(11) + 200 )- randDamage; // Random health between 200 to 210
                        double randArmor = rand.nextInt(21) + 20; // Random armor between 20 to 40
                        
                        EnemyData newEnemy = new EnemyData(name, randDamage, 0, randHealth, randArmor, "Strength");
                        enemy.add(newEnemy);
                    }
                } else if (typeEnemy == 1) {
                    for (String name : enemyData) {
                        // Generate random values for intelligence type enemy
                        double randDamage = rand.nextDouble() * (20 - 10) + 10; // Random damage between 10 to 20
                        double randHealth = rand.nextInt(11) + 100; // Random health between 100 to 110
                        
                        EnemyData newEnemy = new EnemyData(name, randDamage, 0, randHealth, 0, "Intelligence");
                        enemy.add(newEnemy);
                    }
                } else if (typeEnemy == 2) {
                    for (String name : enemyData) {
                        // Generate random values for agility type enemy
                        double randDamage = rand.nextDouble() * (50 - 40) + 40; // Random damage between 40 to 50
                        double randHealth = (rand.nextInt(11) + 100) - randDamage; // Random health between 100 to 110
                        double randCritical = rand.nextInt(3) + 1; // Random critical between 1 to 3
                        
                        EnemyData newEnemy = new EnemyData(name, randDamage, 0, randHealth, randCritical, "Agility");
                        enemy.add(newEnemy);
                    }
                }
                typeEnemy++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        EnemyData newEnemy = new EnemyData("Ancient Collosal Armoured Wyvern", 150, 150, 1000, 123, "Strength");
        enemy.add(newEnemy);
    }

    public void startCombat(int userId, int enemyId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
        
        while(information.get(userId).getHealth() > 0 && enemy.get(enemyId).getHealth() > 0) {
            if(playerTurn) {
                help.space();
                help.sleep();
                statusPlayer(userId, enemyId, userdata, information, bag);
                help.sleep();
                playerTurn(userId, enemyId, userdata, information, bag);
            }else {
                help.space();
                help.sleep();
                statusEnemy(userId, enemyId, userdata, information, bag);
                help.sleep();
                monsterTurn(userId, enemyId, userdata, information, bag);
                playerTurn = !playerTurn;
            }
            playerTurn = !playerTurn;
        }

        if(information.get(userId).getHealth() > 0) {
        	help.space();
            System.out.println("Congratulations! You have defeated the " + enemy.get(enemyId).getName() + "!\nYou have collected a few money from the loot\n\n");
            enemy.clear();
            System.out.print("Press enter to continue...");
    	    scan.nextLine();
    	    help.space();
        }else {
        	help.space();
            System.out.println("Game over! The " + enemy.get(enemyId).getName() + " have defeated you.\nYou have lost a few money from the fight\n\n");
            
            int currMoney = information.get(userId).getMoney();
            if(currMoney >= 50) {
            	information.get(userId).setMoney(currMoney - 100);
            }else if(currMoney <= 50 && currMoney >= 0){
            	information.get(userId).setMoney(-50);
            }
            
            enemy.clear();
            System.out.print("Press enter to continue...");
    	    scan.nextLine();
    	    help.space();
        }
    }
    
    public void statusPlayer(int userId, int enemyId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
    	System.out.printf("%s's Information:\n", information.get(userId).getName());
        System.out.println("====================================");
        System.out.printf( "| Health             :   %.2f    |\n", information.get(userId).getHealth());
        System.out.printf( "| Money              :   %d       |\n", information.get(userId).getMoney());
        System.out.printf( "| Mana               :   %.2f     |\n", information.get(userId).getMana());
        System.out.printf( "| Base Damage        :   %.2f     |\n", information.get(userId).getDamage());
        System.out.println("====================================");
        
        System.out.print("\n\nPress enter to continue...");
	    scan.nextLine();
	    help.space();
    }
    
    public void statusEnemy(int userId, int enemyId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
        System.out.println(enemy.get(enemyId).getName()+"'s Information:");
        System.out.println("====================================");
        System.out.printf("| Health             :   %.2f     |\n",enemy.get(enemyId).getHealth());
        System.out.printf("| Damage             :   %.2f      |\n", enemy.get(enemyId).getDamage());
        System.out.printf("| Armor/Crit         :   %.2f      |\n", enemy.get(enemyId).getArmorCrit());
        System.out.printf("| Type               :   %s       |\n", enemy.get(enemyId).getType());
        System.out.println("====================================");

        System.out.print("\n\nPress enter to continue...");
        scan.nextLine();
        help.space();
    }
    
    boolean messageError = false;
    private void playerTurn(int userId, int enemyId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
        System.out.println(information.get(userId).getName() + "'s turn\n");
        System.out.println("Choose your option!");
        System.out.println("1. Pure Attack");
        System.out.println("2. Attack With Item");
        System.out.println("3. Store Mana");
        System.out.println("4. Escape the fight (10% chance successing)");
        if (messageError) System.out.println("![Invalid choice. Try again]");
        messageError = false;
        System.out.print(">> ");
        int choice = scan.nextInt();
        scan.nextLine();

        switch(choice) {
            case 1:
            	help.space();
                pureAttack(userId, enemyId, userdata, information, bag);
                break;
            case 2:
            	help.space();
                attackWithItem(userId, enemyId, userdata, information, bag);
                break;
            case 3:
            	help.space();
                storeMana(userId, enemyId, userdata, information, bag);
                break;
            case 4:
            	help.space();
                int escapeChance = rand.nextInt(100)+1;
                if(escapeChance <= 10) {
                	System.out.println("Phew.. that was a close call!\nYou've successfully escaped from the enemy!\n");
                	System.out.print("Press enter to continue...");
            	    scan.nextLine();
            	    help.space();
            	    MapRender back = new MapRender(userId, userdata, information, bag);
                }else {
                	System.out.println("Oh no... you accidentally fell off while escaping!\nYou've failed to escape from the enemy!\n");
                	System.out.print("Press enter to continue...");
            	    scan.nextLine();
            	    help.space();
                }
                break;
            default:
            	messageError = true;
                help.space();
                playerTurn(userId, enemyId, userdata, information, bag);
                break;
        }
    }

    private void pureAttack(int userId, int enemyId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
    	System.out.println("ATTACKING\n\nAttacking "+ enemy.get(enemyId).getName());
    	
    	double totalDamage = 0;
    	if(enemy.get(enemyId).getType().equals("Strength")) {
    		totalDamage = information.get(userId).getDamage() - enemy.get(enemyId).getArmorCrit();
    		System.out.println("Enemy is a Strength type... reduced receiving damage!\n");
    	}else {
    		totalDamage = information.get(userId).getDamage();
    	}
    	
    	System.out.println(enemy.get(enemyId).getName()+ " got "+ totalDamage + " damage.");
    	double currHealth = enemy.get(userId).getHealth() - totalDamage;
    	enemy.get(userId).setHealth(currHealth);
    	if(currHealth <= 0) {
    		help.space();
    		startCombat(userId, enemyId, userdata, information, bag);
    	}
    	
    	System.out.print("Press enter to continue...");
	    scan.nextLine();
	    help.space();
    }

    private void attackWithItem(int userId, int enemyId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
    	System.out.println("ATTACKING\n\nAttacking "+ enemy.get(enemyId).getName());
    	
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
		    playerTurn(userId, enemyId, userdata, information, bag);
	    }else {
	    	System.out.println("INVENTORY\n");
		    System.out.println(information.get(userId).getName()+"'s Items");
		    System.out.println("==================================================================================");
		    System.out.println("| ID        | Name            | Type       | Price | Damage | Max Use | Use Left |");
		    System.out.println("==================================================================================");

		    for(int i=0; i < bag.size(); i++) {
		        System.out.printf("| %-9s | %-15s | %-10s | %-5d | %-7.2f | %-7d | %-7d |\n", 
		                          bag.get(i).getId(), 
		                          bag.get(i).getName(), 
		                          bag.get(i).getType(), 
		                          bag.get(i).getPrice(), 
		                          bag.get(i).getDamage(), 
		                          bag.get(i).getMaxOrMana(),
		                          bag.get(i).getUseLeft());
		    }
		    
		    System.out.println("==================================================================================\n");
		    
		    System.out.print("Input Item's ID ('Exit' to cancel) : ");
	        String itemId = scan.next();
	        scan.nextLine();
	        if(itemId.equalsIgnoreCase("Exit")) {
				help.space();
				playerTurn(userId, enemyId, userdata, information, bag);
			}
	        
	        boolean itemFound = false;
	        for(Inventory item : bag) {
	        	if(item.getId().equalsIgnoreCase(itemId)) {
	                itemFound = true;
	                	for(int i=0; i< bag.size(); i++) {
	                		if(bag.get(i).getId().equalsIgnoreCase(itemId) && userId == bag.get(i).getOwner() && bag.get(i).getUseLeft() > 0){
	    	                    if(bag.get(i).getType().equals("Offensive")) {
	    	                    	System.out.println("\nAttacking "+ enemy.get(enemyId).getName()+" with "+bag.get(i).getName());
	    	                    	
	    	                    	int tempUseLeft = bag.get(i).getUseLeft();
	    	                    	bag.get(i).setUseLeft(tempUseLeft - 1);
	    	                    	System.out.println(bag.get(i).getName()+" was used. There is "+ bag.get(i).getUseLeft()+" to this item.\n");
	    	                    	
	    	                    	double currentHp = enemy.get(enemyId).getHealth() - bag.get(i).getDamage();
	    	                    	enemy.get(enemyId).setHealth(currentHp);
	    	                    	if(currentHp <= 0) {
	    	                    		help.space();
	    	                    		startCombat(userId, enemyId, userdata, information, bag);
	    	                    	}
	    	                    	
	    	                    	System.out.println(enemy.get(enemyId).getName() +" received "+ bag.get(i).getDamage() + " amount of damage.");
	    	                    }else if(bag.get(i).getType().equals("Spell")) {
	    	                    	if(information.get(userId).getMana() - bag.get(i).getMaxOrMana() >= 0) {
	    	                    		System.out.println("\nAttacking "+ enemy.get(enemyId).getName()+" with "+bag.get(i).getName());
		    	                    	
		    	                    	double manaLeft = information.get(userId).getMana() - bag.get(i).getMaxOrMana();
		    	                    	information.get(userId).setMana(manaLeft);
		    	                    	System.out.println(bag.get(i).getName()+" was used. There is "+ bag.get(i).getUseLeft()+" to this item.\n");
		    	                    	
		    	                    	double currentHp = enemy.get(enemyId).getHealth() - bag.get(i).getDamage();
		    	                    	enemy.get(enemyId).setHealth(currentHp);
		    	                    	if(currentHp <= 0) {
		    	                    		help.space();
		    	                    		startCombat(userId, enemyId, userdata, information, bag);
		    	                    	}
		    	                    	
		    	                    	System.out.println(enemy.get(enemyId).getName() +" received "+ bag.get(i).getDamage() + " amount of damage.");
	    	                    	}else {
	    	                    		System.out.println("You don't have enough mana to do this action!\n");
	    	                    		System.out.print("Press enter to continue...");
	    	                  	    	scan.nextLine();
	    	                  	    	help.space();
	    	                  	    	attackWithItem(userId, enemyId, userdata, information, bag);
	    	                    	}
	    	                    }
	                		}
	                	}
	        	}
	       }
	       if(!itemFound) {
	           	System.out.println("Item not found.\n");
	           	System.out.print("Press enter to continue...");
	   	    	scan.nextLine();
	   	    	help.space();
	   	    	attackWithItem(userId, enemyId, userdata, information, bag);
	       }
	        
	       	System.out.print("Press enter to continue...");
  	    	scan.nextLine();
  	    	help.space();
  	    	attackWithItem(userId, enemyId, userdata, information, bag);
	    }
    }

    private void storeMana(int userId, int enemyId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
    	System.out.println("Storing mana.....\nAdded 10.00 Mana\n");
    	
    	double addedMana = information.get(userId).getMana();
    	information.get(userId).setMana(addedMana + 10);
    	
    	System.out.print("Press enter to continue...");
	    scan.nextLine();
	    help.space();
    }

    private void monsterTurn(int userId, int enemyId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
        System.out.println("MONSTER'S TURN\n\n");
        
        System.out.println("Monster is going to attack\nDo you want to use your defensive item?");
        System.out.print("YES | NO : ");
        String valid = scan.next();
        scan.nextLine();
        if(valid.equalsIgnoreCase("Yes")) {
			help.space();
			if(bag.isEmpty()) {
		    	System.out.println("INVENTORY\n");
			    System.out.println(information.get(userId).getName()+"'s Items");
			    System.out.println("==================================================================================");
			    System.out.println("| ID        | Name            | Type       | Price | Defens | Max Use | Use Left |");
			    System.out.println("==================================================================================");
			    System.out.println("|			Your Inventory is still empty!				|");
			    System.out.println("==================================================================================\n");
			    
			    System.out.print("Press enter to continue...");
			    scan.nextLine();
			    help.space();
			    monsterTurn(userId, enemyId, userdata, information, bag);
		    }else {
		    	System.out.println("INVENTORY\n");
			    System.out.println(information.get(userId).getName()+"'s Items");
			    System.out.println("==================================================================================");
			    System.out.println("| ID        | Name            | Type       | Price | Defens | Max Use | Use Left |");
			    System.out.println("==================================================================================");

			    for(int i=0; i < bag.size(); i++) {
			        System.out.printf("| %-9s | %-15s | %-10s | %-5d | %-7.2f | %-7d | %-7d |\n", 
			                          bag.get(i).getId(), 
			                          bag.get(i).getName(), 
			                          bag.get(i).getType(), 
			                          bag.get(i).getPrice(), 
			                          bag.get(i).getDamage(), 
			                          bag.get(i).getMaxOrMana(),
			                          bag.get(i).getUseLeft());
			    }
			    
			    System.out.println("==================================================================================\n");
			    
			    System.out.print("Input Item's ID ('Exit' to cancel) : ");
		        String itemId = scan.next();
		        scan.nextLine();
		        if(itemId.equalsIgnoreCase("Exit")) {
					help.space();
					monsterTurn(userId, enemyId, userdata, information, bag);
				}
		        
		        boolean itemFound = false;
		        for(Inventory item : bag) {
		        	if(item.getId().equalsIgnoreCase(itemId)) {
		                itemFound = true;
		                	for(int i=0; i< bag.size(); i++) {
		                		if(bag.get(i).getId().equalsIgnoreCase(itemId) && userId == bag.get(i).getOwner() && bag.get(i).getUseLeft() > 0){
		    	                    if(bag.get(i).getType().equals("Defensive")) {
		    	                    	System.out.println("\nDeflected "+ enemy.get(enemyId).getName()+"'s attack with "+bag.get(i).getName());
		    	                    	
		    	                    	int tempUseLeft = bag.get(i).getUseLeft();
		    	                    	bag.get(i).setUseLeft(tempUseLeft - 1);
		    	                    	System.out.println(bag.get(i).getName()+" was used. There is "+ bag.get(i).getUseLeft()+" to this item.\n\n");
		    	                    	
		    	                    	String enemyType = enemy.get(enemyId).getType();
		    	                        switch(enemyType) {
		    	                            case "Strength":
		    	                            	 	System.out.println("Strength type monster is attacking. (Just a defensive type)\n\n");
		    	                            	 	System.out.println(enemy.get(enemyId).getName()+" attack with a Base damage of "+ enemy.get(enemyId).getDamage());
		    	                            	 	
		    	                            	 	double damageTaken = enemy.get(enemyId).getDamage() - bag.get(i).getDamage();
		    		    	                    	System.out.println("Received only "+ damageTaken + " amount of damage.");
		    		    	                    	
		    		    	                    	double currentHp = information.get(userId).getHealth() - damageTaken;
		    		    	                    	information.get(userId).setHealth(currentHp);
		    		    	                    	if(currentHp <= 0) {
		    		    	                    		help.space();
		    		    	                    		startCombat(userId, enemyId, userdata, information, bag);
		    		    	                    	}

		    	                            	    System.out.print("\n\nPress enter to continue...");
		    	                            	    scan.nextLine();
		    	                            	    help.space();
		    	                	                startCombat(userId, enemyId, userdata, information, bag);
		    	                                break;
		    	                            case "Intelligence":
		    	                	            	System.out.println("Intelligence type monster is attacking. (Can cast a skill)\n\n");
		    	                	            	double randDamaged = rand.nextInt(20) + 30;
		    	                	            	double totalDmg = enemy.get(enemyId).getDamage() + randDamaged;
		    	                	            	System.out.println(enemy.get(enemyId).getName()+" attack with a Skill damage of "+ totalDmg);
		    			    	                	
		    	                	            	double andDmg = totalDmg - bag.get(i).getDamage();
		    	                	            	System.out.println("Received only "+ totalDmg + " amount of damage.");
		    		    	                    	
		    		    	                    	double currentHape = information.get(userId).getHealth() - andDmg;
		    		    	                    	information.get(userId).setHealth(currentHape);
		    		    	                    	if(currentHape <= 0) {
		    		    	                    		help.space();
		    		    	                    		startCombat(userId, enemyId, userdata, information, bag);
		    		    	                    	}
		    	                	
		    	                	                System.out.print("\n\nPress enter to continue...");
		    	                	                scan.nextLine();
		    	                	                help.space();
		    	                	                startCombat(userId, enemyId, userdata, information, bag);
		    	                                break;
		    	                            case "Agility":
		    	                	            	System.out.println("Agility type monster is attacking. (Can do critical attack)\n\n");
		    	                	            	double totalDamaged = enemy.get(enemyId).getDamage() * enemy.get(enemyId).getArmorCrit();
		    	                	            	System.out.println(enemy.get(enemyId).getName()+" attack with a Critical damage of "+ totalDamaged);
		    	                	            	
		    	                	            	double dmgTaken = totalDamaged - - bag.get(i).getDamage();
		    	                	            	System.out.println("Received only "+ dmgTaken + " amount of damage.");
		    		    	                    	
		    		    	                    	double currentHps = information.get(userId).getHealth() - dmgTaken;
		    		    	                    	information.get(userId).setHealth(currentHps);
		    		    	                    	if(currentHps <= 0) {
		    		    	                    		help.space();
		    		    	                    		startCombat(userId, enemyId, userdata, information, bag);
		    		    	                    	}
		    	                	
		    	                	                System.out.print("\n\nPress enter to continue...");
		    	                	                scan.nextLine();
		    	                	                help.space();
		    	                	                startCombat(userId, enemyId, userdata, information, bag);
		    	                                break;
		    	                            default:
		    	                                System.out.println("Invalid enemy type!");
		    	                                help.space();
		    	                                break;
		    	                        }
		    	                    }
		                		}
		                	}
		        	}
		       }
		       if(!itemFound) {
		           	System.out.println("Item not found.\n");
		           	System.out.print("Press enter to continue...");
		   	    	scan.nextLine();
		   	    	help.space();
		   	    	attackWithItem(userId, enemyId, userdata, information, bag);
		       }
		    }			
		}else if(valid.equalsIgnoreCase("No")) {
			help.space();
			System.out.println("MONSTER'S TURN\n\n");
			
			String enemyType = enemy.get(enemyId).getType();
            switch(enemyType) {
                case "Strength":
                	 	System.out.println("Strength type monster is attacking. (Just a defensive type)\n\n");
                	 	System.out.println(enemy.get(enemyId).getName()+" attack with a Base damage of "+ enemy.get(enemyId).getDamage());
                	 	
                	 	double damageTaken = enemy.get(enemyId).getDamage();
                    	System.out.println("You received "+ damageTaken + " amount of damage.");
                    	
                    	double currentHp = information.get(userId).getHealth() - damageTaken;
                    	information.get(userId).setHealth(currentHp);
                    	if(currentHp <= 0) {
                    		help.space();
                    		startCombat(userId, enemyId, userdata, information, bag);
                    	}

                	    System.out.print("\n\nPress enter to continue...");
                	    scan.nextLine();
                	    help.space();
    	                startCombat(userId, enemyId, userdata, information, bag);
                    break;
                case "Intelligence":
    	            	System.out.println("Intelligence type monster is attacking. (Can cast a skill)\n\n");
    	            	double randDamaged = rand.nextInt(20) + 30;
    	            	double totalDmg = enemy.get(enemyId).getDamage() + randDamaged;
    	            	System.out.println(enemy.get(enemyId).getName()+" attack with a Skill damage of "+ totalDmg);
	                	
    	            	System.out.println("You received "+ totalDmg + " amount of damage.");
                    	
                    	double currentHape = information.get(userId).getHealth() - totalDmg;
                    	information.get(userId).setHealth(currentHape);
                    	if(currentHape <= 0) {
                    		help.space();
                    		startCombat(userId, enemyId, userdata, information, bag);
                    	}
    	
    	                System.out.print("\n\nPress enter to continue...");
    	                scan.nextLine();
    	                help.space();
    	                startCombat(userId, enemyId, userdata, information, bag);
                    break;
                case "Agility":
    	            	System.out.println("Agility type monster is attacking. (Can do critical attack)\n\n");
    	            	double totalDamaged = enemy.get(enemyId).getDamage() * enemy.get(enemyId).getArmorCrit();
    	            	System.out.println(enemy.get(enemyId).getName()+" attack with a Critical damage of "+ totalDamaged);
    	
    	            	System.out.println("You received "+ totalDamaged + " amount of damage.");
                    	
                    	double currentHps = information.get(userId).getHealth() - totalDamaged;
                    	information.get(userId).setHealth(currentHps);
                    	if(currentHps <= 0) {
                    		help.space();
                    		startCombat(userId, enemyId, userdata, information, bag);
                    	}
    	
    	                System.out.print("\n\nPress enter to continue...");
    	                scan.nextLine();
    	                help.space();
    	                startCombat(userId, enemyId, userdata, information, bag);
                    break;
                default:
                    System.out.println("Invalid enemy type!");
                    help.space();
                    break;
            }
		}else {
			help.space();
			monsterTurn(userId, enemyId, userdata, information, bag);
		}
    }
    
	public void saving1(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
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
	
	public void saving2(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
	    String file = "src/file/Useritem.txt";
	    try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
	        for(int i = 0; i < bag.size(); i++) {
	           if(bag.get(i).getUseLeft() > 0) {
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
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}

