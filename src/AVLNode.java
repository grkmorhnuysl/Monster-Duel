public class AVLNode<T> {

    private int key;
    private String name;
    private T data;
    private AVLNode left;
    private AVLNode right;
    private int height = 1;
    private Monster monster;

    public void setLeft(AVLNode left) {
        this.left = left;
    }

    public void setRight(AVLNode right) {
        this.right = right;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public AVLNode getRight() {
        return right;
    }

    public AVLNode getLeft() {
        return left;
    }

    public T getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public int getKey() {
        return key;
    }

    public AVLNode(Monster monster) {
        this.monster = monster;
        this.key = monster.getAttack();
        this.name = monster.getName();
        left = null;
        right = null;
        height = 1;
    }
}
