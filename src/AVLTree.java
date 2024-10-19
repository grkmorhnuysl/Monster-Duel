import java.util.HashMap;
import java.util.Objects;
import java.util.Comparator;

public class AVLTree<T> {
    private AVLNode root;
    private Comparator<Monster> comparator;

    public AVLTree(Comparator<Monster> comparator) {
        this.comparator = comparator;
    }

    private int height(AVLNode node) {
        return node == null ? 0 : node.getHeight();
    }

    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.getLeft()) - height(node.getRight());
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.getLeft();
        AVLNode T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);
        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);

        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.getRight();
        AVLNode T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        x.setHeight(Math.max(height(x.getLeft()), height(x.getRight())) + 1);
        y.setHeight(Math.max(height(y.getLeft()), height(y.getRight())) + 1);

        return y;
    }

    private AVLNode insert(AVLNode node, Monster monster) {
        if (node == null) {
            return new AVLNode(monster);
        }

        if (comparator.compare(monster, node.getMonster()) < 0) {
            node.setLeft(insert(node.getLeft(), monster));
        } else {
            node.setRight(insert(node.getRight(), monster));
        }

        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));

        int balance = getBalance(node);

        if (balance > 1 && comparator.compare(monster, node.getLeft().getMonster()) < 0) {
            return rightRotate(node);
        }

        if (balance < -1 && comparator.compare(monster, node.getRight().getMonster()) > 0) {
            return leftRotate(node);
        }

        if (balance > 1 && comparator.compare(monster, node.getLeft().getMonster()) > 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }

        if (balance < -1 && comparator.compare(monster, node.getRight().getMonster()) < 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }

        return node;
    }

    public void insert(Monster monster) {
        root = insert(root, monster);
    }

    public void inOrder(AVLNode node, HashMap<STRINT, Monster> result, String feature) {
        if (node != null) {
            inOrder(node.getLeft(), result, feature);
            if (Objects.equals(feature, "name")) {
                result.put(new STRINT(node.getMonster().getName()), node.getMonster());
            }
            if (Objects.equals(feature, "id")) {
                result.put(new STRINT(node.getMonster().getId()), node.getMonster());
            }
            if (Objects.equals(feature, "health")) {
                result.put(new STRINT(node.getMonster().getHealth()), node.getMonster());
            }
            if (Objects.equals(feature, "attack")) {
                result.put(new STRINT(node.getMonster().getAttack()), node.getMonster());
            }
            if (Objects.equals(feature, "defense")) {
                result.put(new STRINT(node.getMonster().getDefense()), node.getMonster());
            }
            if (Objects.equals(feature, "level")) {
                result.put(new STRINT(node.getMonster().getLevel()), node.getMonster());
            }
            inOrder(node.getRight(), result, feature);
        }
    }

    public HashMap<STRINT, Monster> inOrder(String feature) {
        HashMap<STRINT, Monster> result = new HashMap<>();
        inOrder(root, result, feature);
        return result;
    }
}
