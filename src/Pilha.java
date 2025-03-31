public class Pilha<T> {
    private LinkedList<T> pilha;

    public Pilha() {
        pilha = new LinkedList<>();
    }

    public void push(T value){
        pilha.addNode(value);
    }

    public T pop(){
        Node<T> topo = pilha.getTopo();
        if(topo == null) {
            return null;
        } else {
            /* topo.previous = topo;
            topo.next = null; */
            pilha.removeTopo();
            return topo.value;
        }
    }
    public boolean isEmpty() {
        return pilha.getPrimeiro() == null;
    }
}
