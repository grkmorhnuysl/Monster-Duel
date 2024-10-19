import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scn = new Scanner(System.in);
        HashMap<String, Monster> currentMonsters = initMonsters();

        if (currentMonsters == null) {
            System.out.println("Error: Failed to initialize monsters.");
            return;
        } else if (currentMonsters.isEmpty()) {
            System.out.println("Warning: No monsters loaded.");
            return;
        }

        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");

        int i = 0;
        for (String k : currentMonsters.keySet()) {
            System.out.println(k);
        }

        while (i != 20) {
            if (i % 2 == 0) {
                pickMonster(scn, currentMonsters, player1);
            } else {
                pickMonster(scn, currentMonsters, player2);
            }
            i++;
        }

        battlePhase(scn, player1, player2, currentMonsters);
    }

    private static void pickMonster(Scanner scn, HashMap<String, Monster> currentMonsters, Player player) {
        boolean controlled = true;
        while (controlled) {
            System.out.println(player.getName() + " picking a monster...");
            String mnsName = scn.nextLine();
            if (Objects.equals(mnsName, "list")) {
                currentMonsters.keySet().forEach(System.out::println);
            } else if (currentMonsters.get(mnsName) == null) {
                System.out.println("We don't have this monster");
            } else {
                player.addMonster(currentMonsters.get(mnsName));
                currentMonsters.remove(mnsName);
                System.out.println(mnsName + " picked");
                controlled = false;
            }
        }
    }

    private static void battlePhase(Scanner scn, Player player1, Player player2, HashMap<String, Monster> currentMonsters) {
        boolean battle = true;
        int p1win = 0, p1strike = 0, p2win = 0, p2strike = 0;

        while (battle) {
            Monster mnsp1 = selectMonster(scn, player1);
            Monster mnsp2 = selectMonster(scn, player2);

            while (true) {
                if (mnsp1.getAttack() > mnsp2.getDefense()) {
                    mnsp2.setHealth(mnsp2.getHealth() + (mnsp2.getDefense() - mnsp1.getAttack()));
                }
                if (mnsp2.getAttack() > mnsp1.getDefense()) {
                    mnsp1.setHealth(mnsp1.getHealth() + (mnsp1.getDefense() - mnsp2.getAttack()));
                }
                if (mnsp1.getHealth() <= 0 && mnsp2.getHealth() <= 0) {
                    System.out.println("Draw - Both monsters died.");
                    System.out.println(mnsp2.getName() + " died");
                    player2.deadMonster(mnsp2.getName());
                    System.out.println(mnsp1.getName() + " died");
                    player1.deadMonster(mnsp1.getName());
                    p2win++;
                    p1win++;
                    break;
                } else if (mnsp2.getHealth() <= 0) {
                    System.out.println(player1.getName() + " wins the round.");
                    System.out.println(mnsp2.getName() + " died");
                    player2.deadMonster(mnsp2.getName());
                    currentMonsters.put(mnsp2.getName(), mnsp2);
                    mnsp1.levelUp();
                    p1win++;
                    p1strike++;
                    p2strike = 0;
                    break;
                } else if (mnsp1.getHealth() <= 0) {
                    System.out.println(player2.getName() + " wins the round.");
                    System.out.println(mnsp1.getName() + " died");
                    player1.deadMonster(mnsp1.getName());
                    currentMonsters.put(mnsp1.getName(), mnsp1);
                    mnsp2.levelUp();
                    p2win++;
                    p2strike++;
                    p1strike = 0;
                    break;
                }
            }

            if (p1strike == 3) {
                System.out.println("Game Over. " + player1.getName() + " wins with a strike.");
                battle = false;
            } else if (p2strike == 3) {
                System.out.println("Game Over. " + player2.getName() + " wins with a strike.");
                battle = false;
            } else if (player1.getPlayerMonsters().isEmpty()) {
                System.out.println("Game Over. " + player2.getName() + " wins by eliminating all monsters.");
                battle = false;
            } else if (player2.getPlayerMonsters().isEmpty()) {
                System.out.println("Game Over. " + player1.getName() + " wins by eliminating all monsters.");
                battle = false;
            }
        }
    }

    private static Monster selectMonster(Scanner scn, Player player) {
        AtomicReference<Monster> selectedMonster = new AtomicReference<>();
        AtomicBoolean selecting = new AtomicBoolean(true);
        while (selecting.get()) {
            System.out.println(player.getName() + " selecting a monster (or enter id, name, health, attack, defense, level to order):");
            String input = scn.nextLine();
            player.getPlayerMonsters().forEach((key, value) -> {
                if (Objects.equals(value.getName(), input)) {
                    selectedMonster.set(value);
                    selecting.set(false);
                }
            });
            if (selecting.get() && (input.equals("id") || input.equals("name") || input.equals("attack") ||
                                    input.equals("defense") || input.equals("health") || input.equals("level"))) {
                player.orderMonster(input);
                System.out.println(player.getName() + "'s Monsters:");
                player.getPlayerMonsters().forEach((key, value) -> {
                    System.out.println(value.getId() + " " + value.getName() + " " + value.getHealth() + " " + value.getAttack() + " " + value.getDefense() + " " + value.getLevel());
                });
            } else if (selecting.get()) {
                System.out.println("Invalid selection, try again.");
            }
        }
        return selectedMonster.get();
    }

    private static HashMap<String, Monster> initMonsters() throws IOException {
        HashMap<String, Monster> monsters1 = new HashMap<>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("monsters.txt"));
            int fileIndex = 0;
            String line = reader.readLine();
            int id = 0;
            String name = "";
            int health = 0;
            int attack = 0;
            int defense = 0;
            int level = 0;
            while (line != null) {
                if (fileIndex % 6 == 0) {
                    id = Integer.parseInt(line);
                } else if (fileIndex % 6 == 1) {
                    name = line;
                } else if (fileIndex % 6 == 2) {
                    health = Integer.parseInt(line);
                } else if (fileIndex % 6 == 3) {
                    attack = Integer.parseInt(line);
                } else if (fileIndex % 6 == 4) {
                    defense = Integer.parseInt(line);
                } else if (fileIndex % 6 == 5) {
                    level = Integer.parseInt(line);
                    Monster mns = new Monster(id, name, health, attack, defense, level);
                    monsters1.put(name, mns);
                }
                fileIndex++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading monsters file: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number from file: " + e.getMessage());
            return null;
        }

        return monsters1;
    }
}
