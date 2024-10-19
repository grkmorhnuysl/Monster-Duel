public class Monster {
    private int id;
    private String name;
    private int health;
    private int attack;
    private int defense;
    private int level;

    Monster(int id, String name, int health, int attack, int defense, int level) {
        this.id = id;
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.level = level;
    }

    public void levelUp() {
        setDefense(defense + 2);
        setAttack(attack + 1);
        setHealth(health + 3);
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDefense() {
        return defense;
    }

    public int getLevel() {
        return level;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
