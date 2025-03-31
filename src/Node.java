public class Node<E> {
    E value;
    Node<E> next;
    Node<E> previous;

    public Node(E value) {
        this.value = value;
        this.next = null;
        this.previous = null;
    }
}
