package utama;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.*;

import data.Inventory;
import data.PlayerAttributes;
import data.User;

public class MapRender {
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		User help = new User("","",0,0);
		private HashMap<Integer, char[][]> userMaps = new HashMap<>();
		
	    private static final int MAP_SIZE = 300;
	    private static final int PATTERN_SIZE = 3;
	    private static final int MAX_COINS = 200;

	    private static final char GRASS_UPPER = 'V';
	    private static final char GRASS_LOWER = 'v';
	    private static final char WALL = '#';
	    private static final char EMPTY = ' ';
	    private static final char COIN = 'O'; // buat simbol koin
	    private static final char PLAYER = 'X'; // buat simbol player

	    private static final char[][] GRASS1= {
	            {GRASS_UPPER, EMPTY, GRASS_UPPER},
	            {GRASS_LOWER, GRASS_UPPER, GRASS_LOWER},
	            {GRASS_LOWER, GRASS_LOWER, GRASS_UPPER}
	    };
	    
	    private static final char[][] GRASS2= {
	            {GRASS_UPPER, GRASS_UPPER, GRASS_LOWER},
	            {GRASS_LOWER, GRASS_LOWER, GRASS_UPPER},
	            {EMPTY, EMPTY, EMPTY}
	    };
	    
	    private static final char[][] GRASS3= {
	            {GRASS_LOWER, EMPTY, GRASS_UPPER},
	            {GRASS_UPPER, GRASS_UPPER, EMPTY},
	            {GRASS_LOWER, GRASS_LOWER, EMPTY},
	    };
	    
	    private static final char[][] GRASS4= {
	            {GRASS_UPPER, EMPTY, EMPTY},
	            {GRASS_LOWER, EMPTY, GRASS_UPPER},
	            {GRASS_UPPER, GRASS_UPPER, GRASS_UPPER},
	    };

	    private static final char[][] WALL1 = {
	    		{WALL, WALL, WALL},
	    		{EMPTY, EMPTY, EMPTY},
	    		{WALL, WALL, WALL}
	    };
	    
	    private static final char[][] WALL2 = {
	    		{WALL, EMPTY, WALL},
	    		{WALL, WALL, WALL},
	    		{EMPTY, WALL, EMPTY}
	    		
	    };
	    
	    private static final char[][] WALL3 = {
	    		{WALL, WALL, WALL},
	    		{WALL, EMPTY, WALL},
	    		{EMPTY, EMPTY, EMPTY}
	    };
	    
	    private static final char[][] WALL4 = {
	    		{WALL, WALL, WALL},
	    		{WALL, WALL, EMPTY},
	    		{EMPTY, WALL, EMPTY}
	    };

	    private static final char[][] EMPTY_PATTERN = {
	    		{EMPTY, EMPTY, EMPTY},
	            {EMPTY, EMPTY, EMPTY},
	            {EMPTY, EMPTY, EMPTY}
	    };
	    
	    public void gameStart(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
	    	char[][] map = userMaps.get(userId);
	    	
	        displayMap(userId, userdata.get(userId).getPosX(), userdata.get(userId).getPosY());
	        
	        boolean errorMessage = false;
	        while(true) {
	        	if(errorMessage) System.out.println("![Enter a valid move ('e' to exit)]");
	        	System.out.print(">> ");
	        	errorMessage = false;
	        	
	            char move = scan.next().charAt(0);
	            scan.nextLine();
	            int newX = userdata.get(userId).getPosX();
	            int newY = userdata.get(userId).getPosY();
	            
	            switch(move) {
	            	case 'W':
	                case 'w':
	                	int w = userdata.get(userId).getPosY()-1;
	                    newY = Math.max(0, w);	              
	                    break;
	                case 'A':
	                case 'a':
	                	int a = userdata.get(userId).getPosX()-1;
	                    newX = Math.max(0, a);	                    
	                    break;
	                case 'S':
	                case 's':
	                	int s = userdata.get(userId).getPosY()+1;	
	                	newY = Math.min(MAP_SIZE - 1, s);	 
	                    break;
	                case 'D':
	                case 'd':
	                	int d = userdata.get(userId).getPosX()+1;
	                    newX = Math.min(MAP_SIZE - 1, d);	                    
	                    break;
	                case 'I':
	                case 'i':
	                	saveData(userId, userdata, bag);
	            		help.space();
	            		UserItem show = new UserItem(userId, userdata, information, bag);
	                	break;
	                case 'Z':
	                case 'z':
	                	saveData(userId, userdata, bag);
	            		help.space();
	                	Shop buy = new Shop(userId, userdata, information);
	                	break;
	                case 'E':
	                case 'e':
	                	System.out.println("\nExiting and saving your game...\n");
	                	saveData(userId, userdata, bag);
	                	saving1(userId, userdata, information);
	                	System.out.print("Press enter to continue...");
	            		scan.nextLine();
	            		help.space();
	            		Games user = new Games(userId, userdata, information, bag);
	            		break;
	                default:
	                    errorMessage = true;
	                    continue;
	            }
	            
	            if(map[newY][newX] != WALL && newY != 1 && newY != 299 && newX != 1 && newX != 299) {
	            	userdata.get(userId).setPosX(newX);
	            	userdata.get(userId).setPosY(newY);
	            	
	            	if(map[userdata.get(userId).getPosY()][userdata.get(userId).getPosX()] == 'V' ||
	            			map[userdata.get(userId).getPosY()][userdata.get(userId).getPosX()] == 'v') {
	                	int monsterEvent = rand.nextInt(100)+1;
	                	if(monsterEvent <= 30) {
	                		help.space();
		            		Combat battle = new Combat(userId, userdata, information, bag);
	                	}
	                }
	            	
	                if(map[userdata.get(userId).getPosY()][userdata.get(userId).getPosX()] == 'O') {
	                	int coinAdd = information.get(userId).getMoney();
	                	information.get(userId).setMoney(coinAdd+5);
		                map[userdata.get(userId).getPosY()][userdata.get(userId).getPosX()] = EMPTY;
	                }
	                
	                help.space();
		            displayMap(userId, userdata.get(userId).getPosX(), userdata.get(userId).getPosY());
		            
		            System.out.printf("%s's Information:\n", information.get(userId).getName());
		            System.out.println("====================================");
		            System.out.printf( "| Health             :   %.2f    |\n", information.get(userId).getHealth());
		            System.out.printf( "| Money              :   %d       |\n", information.get(userId).getMoney());
		            System.out.printf( "| Mana               :   %.2f     |\n", information.get(userId).getMana());
		            System.out.printf( "| Base Damage        :   %.2f     |\n", information.get(userId).getDamage());
		            System.out.println("====================================");
	            }else {
	            	errorMessage = true;
	            }
	        }
	    }

	    private char[][] generateMap(int userId) {
	        char[][] map = new char[MAP_SIZE][MAP_SIZE];

	        for (int y = 0; y < MAP_SIZE; y += PATTERN_SIZE ) {
	            for (int x = 0; x < MAP_SIZE; x += PATTERN_SIZE ) {

	              char[][] pattern;
	              if (( x>=149 && x <= 151 && y>=149 && y<=151)||(x > 0 && (map[y][x - 1] == GRASS_UPPER || map[y][x - 1] == GRASS_LOWER || map[y][x - 1] == WALL)) ||
	                 (y > 0 && (map[y - 1][x] == GRASS_UPPER || map[y - 1][x] == GRASS_LOWER || map[y - 1][x] == WALL))) {
	                  pattern = EMPTY_PATTERN;
	              }else {
	                   int num = rand.nextInt(100) + 1;
	                   if(num == 1 || num == 2) {
	                       pattern = GRASS1;
	                   }else if (num == 3 || num == 4) {
	                       pattern = GRASS2;
	                   }else if (num == 5 || num == 6) {
	                       pattern = GRASS3;
	                   }else if (num == 7 || num == 8) {
	                       pattern = GRASS4;
	                   }else if (num == 9 || num == 10) {
	                       pattern = WALL1;
	                   }else if (num == 11 || num == 12) {
	                       pattern = WALL2;
	                   }else if (num == 13 || num == 14) {
	                       pattern = WALL3;
	                   }else if (num == 15 || num == 16) {
                        pattern = WALL4;	
                       }else {
	                        pattern = EMPTY_PATTERN;
	                   }
	                }

	                for(int dy = 0; dy < PATTERN_SIZE; dy++) {
	                    for(int dx = 0; dx < PATTERN_SIZE; dx++) {
	                        if(x + dx < MAP_SIZE && y + dy < MAP_SIZE) {
	                            map[y + dy][x + dx] = pattern[dy][dx];
	                        }
	                    }
	                }
	            }
	        }
	        return map;
	    }

	    public void placeCoins(int userId) {
	        char[][] map = userMaps.get(userId);
	        
	        int coinsPlaced = 0;
	        int maxCoins = Math.min(MAX_COINS, MAP_SIZE * MAP_SIZE); 
	        while(coinsPlaced <= maxCoins) {  
	            int x = rand.nextInt(MAP_SIZE);
	            int y = rand.nextInt(MAP_SIZE);

	            if(map[y][x] == EMPTY && (x > 1 && x < 299) && (y > 1 && y < 299)){
	                map[y][x] = COIN;
	                coinsPlaced++;
	            }
	        }
	    }

	    public void displayMap(int userId, int playerX, int playerY) {
	        char[][] map = userMaps.get(userId);
	        if (map == null) {
	            System.out.println("Map is not detected!\n");
	            return;
	        }

	        int startX = Math.max(0, playerX - 17);
	        int startY = Math.max(0, playerY - 7);
	        int endX = Math.min(MAP_SIZE, startX + 35);
	        int endY = Math.min(MAP_SIZE, startY + 15);

	        System.out.print("\u001B[38;5;202m");
	        for (int i = 0; i < 37; i++) {
	            System.out.print("\u2588");
	        }
	        System.out.println("\u001B[0m");

	        for(int y = startY; y < endY; y++){
	            System.out.print("\u001B[38;5;202m\u2588\u001B[0m");
	            for(int x = startX; x < endX; x++) {
	                if(x == playerX && y == playerY) {
	                    System.out.print("\u001B[34m" + PLAYER + "\u001B[0m");
	                }else {
	                    switch (map[y][x]) {
	                        case GRASS_UPPER:
	                        case GRASS_LOWER:
	                            System.out.print("\u001B[32m" + map[y][x] + "\u001B[0m");
	                            break;
	                        case WALL:
	                            System.out.print("\u001B[31m" + map[y][x] + "\u001B[0m");
	                            break;
	                        case COIN:
	                            System.out.print("\u001B[33m" + map[y][x] + "\u001B[0m");
	                            break;
	                        default:
	                            System.out.print(map[y][x]);
	                    }
	                }
	            }
	            System.out.print("\u001B[38;5;202m\u2588\u001B[0m\n");
	        }

	        System.out.print("\u001B[38;5;202m");
	        for (int i = 0; i < 37; i++) {
	            System.out.print("\u2588");
	        }
	        System.out.println("\u001B[0m");
	        System.out.println();
	    }
	
	    public MapRender(int userId, ArrayList<User> userdata, ArrayList<PlayerAttributes> information, ArrayList<Inventory> bag) {
	        char[][] map = null;

	        try{
	            FileInputStream fIn = new FileInputStream(userId + ".ser");
	            ObjectInputStream in = new ObjectInputStream(fIn);
	            map = (char[][]) in.readObject();
	            in.close();
	            fIn.close();
	        }catch (IOException | ClassNotFoundException e) {
	            System.out.println();
	        }

	        if(map == null) {
	            map = generateMap(userId);
	            userMaps.put(userId, map);
	            placeCoins(userId);
	            userdata.get(userId).setPosX(MAP_SIZE / 2);
	            userdata.get(userId).setPosY(MAP_SIZE / 2);
	        }
	        userMaps.put(userId, map);
	        gameStart(userId, userdata, information, bag);
	    }
	    
	    public void saveData(int userId, ArrayList<User> userdata, ArrayList<Inventory> bag) {
	        try{
	            FileOutputStream fOut = new FileOutputStream(userId + ".ser");
	            ObjectOutputStream out = new ObjectOutputStream(fOut);
	            out.writeObject(userMaps.get(userId));
	            
	            out.close();
	            fOut.close();
	        }catch (IOException e) {
	            e.printStackTrace();
	        }
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
}
