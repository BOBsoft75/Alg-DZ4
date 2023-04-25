// Программа для реализации операции вставки в красно-черном дереве.
import java.util.Scanner;

class node {

  node left, right;
  int data;

// красный ==> true, черный ==> false
  boolean color;

  node(int data) {
    this.data = data;
    left = null;
    right = null;

// Новый создаваемый узел всегда красного цвета.
    color = true;
  }
}

public class RedBlackTree {

  private static node root = null;

// Функция для поворота узла влево.
  node rotateLeft(node myNode) {
    System.out.printf("поворот влево\n");
    node child = myNode.right;
    node childLeft = child.left;

    child.left = myNode;
    myNode.right = childLeft;

    return child;
  }

// Функция для поворота узла вправо.
  node rotateRight(node myNode) {
    System.out.printf("поворот вправо\n");
    node child = myNode.left;
    node childRight = child.right;

    child.right = myNode;
    myNode.left = childRight;

    return child;
  }

  // Функция для проверки красного ли цвета нода.
  boolean isRed(node myNode) {
    if (myNode == null) {
      return false;
    }
    return (myNode.color == true);
  }

// Функция для изменения цвета двух узлов.
  void swapColors(node node1, node node2) {
    boolean temp = node1.color;
    node1.color = node2.color;
    node2.color = temp;
  }

// вставка в левостороннее Красно-черное дерево.
  node insert(node myNode, int data) {
    if (myNode == null) {
      return new node(data);
    }
    if (data < myNode.data) {
      myNode.left = insert(myNode.left, data);
    } else if (data > myNode.data) {
      myNode.right = insert(myNode.right, data);
    } else {
      return myNode;
    }

// когда правый дочерний элемент красный, а левый дочерний элемент черный или не существует.
    if (isRed(myNode.right) && !isRed(myNode.left)) {
// Повернуть узел влево
      myNode = rotateLeft(myNode);
// Поменять местами цвета дочернего узла всегда должен быть красным
      swapColors(myNode, myNode.left);
    }

// когда левый ребенок и левый внук красные
    if (isRed(myNode.left) && isRed(myNode.left.left)) {
// Повернуть узел вправо
      myNode = rotateRight(myNode);
      swapColors(myNode, myNode.right);
    }

// когда и левый, и правый дочерние элементы красные.
    if (isRed(myNode.left) && isRed(myNode.right)) {
// Инвертировать цвет узла это левый и правый дети.
      myNode.color = !myNode.color;

// Изменить цвет на черный.
      myNode.left.color = false;
      myNode.right.color = false;
    }

    return myNode;
  }

  // Обход по порядку
  void inorder(node node) {
    if (node != null)
    {
      inorder(node.left);
      char c = '░';
      if (node.color == false)
        c = '█';
      System.out.print(node.data + ""+c+" ");
      inorder(node.right);
    }
  }

  public static void main(String[] args) {

    RedBlackTree node = new RedBlackTree();
    try (Scanner scan = new Scanner(System.in)) {
      char ch;
      do {
        System.out.println("Введите целое число:");

        int num = scan.nextInt();
        root = node.insert(root, num);

        node.inorder(root);
        System.out.println("\nВы хотите продолжить? (y/n)");
        ch = scan.next().charAt(0);
      } while (ch == 'Y' || ch == 'y');
    }
  }
}