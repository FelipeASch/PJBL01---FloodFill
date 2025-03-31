public class Fila<T> {
    private LinkedList<T> fila;

    public Fila() {
        fila = new LinkedList<>();
    }

    public void enqueue(T value){
        fila.addNode(value);
    }
    public T dequeue(){
        Node<T> primeiro = fila.getPrimeiro();
        if(primeiro == null) {
            return null;
        }
        fila.removePrimeiro();
        return primeiro.value;
    }

    public boolean isEmpty() {
        return fila.getPrimeiro() == null;
    }
}
