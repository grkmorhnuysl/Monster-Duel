import java.util.*;

public class Player {
    private final String name;
    private HashMap<STRINT, Monster> playerMonsters = new HashMap<>();

    Player(String name) {
        this.name = name;
    }

    public void addMonster(Monster monster) {
        playerMonsters.put(new STRINT(monster.getName()), monster);
    }

    public void deadMonster(String named) {
        Iterator<HashMap.Entry<STRINT, Monster>> iterator = playerMonsters.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<STRINT, Monster> entry = iterator.next();
            if (Objects.equals(named, entry.getValue().getName())) {
                iterator.remove();
                break;
            }
        }
    }

    public String getName() {
        return name;
    }

    public HashMap<STRINT, Monster> getPlayerMonsters() {
        return playerMonsters;
    }

    public void orderMonster(String attribute) {
        Comparator<Monster> comparator;

        switch (attribute.toLowerCase()) {
            case "id":
                comparator = Comparator.comparingInt(Monster::getId);
                break;
            case "name":
                comparator = Comparator.comparing(Monster::getName);
                break;
            case "health":
                comparator = Comparator.comparingInt(Monster::getHealth);
                break;
            case "attack":
                comparator = Comparator.comparingInt(Monster::getAttack);
                break;
            case "defense":
                comparator = Comparator.comparingInt(Monster::getDefense);
                break;
            case "level":
                comparator = Comparator.comparingInt(Monster::getLevel);
                break;
            default:
                throw new IllegalArgumentException("Unknown attribute: " + attribute);
        }

        AVLTree<Monster> avlTree = new AVLTree<>(comparator);

        // Insert monsters into AVL tree
        playerMonsters.forEach((key, value) -> avlTree.insert(value));

        // Retrieve sorted monsters and update playerMonsters map
        playerMonsters = avlTree.inOrder(attribute);
    }
}
