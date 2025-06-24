package um.edu.uy.tads;


import um.edu.uy.interfaces.MyList;

public class MyLinkedList<T> implements MyList<T> {

    private Node<T> head;

    @Override
    public void add(T elemento) {
        Node<T> nuevo = new Node<T>(elemento);
        if (head == null) {
            head = nuevo;
        }
        else {
            Node<T> ultimo = this.getUltimo();
            ultimo.setProximo(nuevo);
        }
    }

    @Override
    public void remove(int indiceDelElemento) {
        int largo = obtenerLargo();
        //Si la lista tiene solo un elemento la vacio
        if (largo == 1){
            this.setHead(null);
            return;
        }
        //si quiero remover el primer elemento cambia la cabeza por el sgundo
        if (indiceDelElemento==0){
            this.setHead(head.getProximo());
            return;
        }

        Node<T> nodeAnterior = this.obtenerNodo(indiceDelElemento - 1);
        Node<T> nodeARemover = this.obtenerNodo(indiceDelElemento);

        // If removing the last node
        if (nodeARemover.getProximo() == null) {
            nodeAnterior.setProximo(null);
        } else {
            nodeAnterior.setProximo(nodeARemover.getProximo());
        }

    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= this.obtenerLargo()) {
            throw new IndexOutOfBoundsException("El indice no existe en la lista");
        }
        Node<T> nodeABuscar = head;
        for (int i = 0; i < index; i++) {
            nodeABuscar = nodeABuscar.getProximo();
        }
        return nodeABuscar.getElemento();
    }

    public int obtenerLargo() {
        if (head == null) {
            return 0;
        }
        int largo = 0;
        Node<T> node = head;
        if (node.getProximo() == null) {
            return 1;
        }
        while (node != null) {
            largo++;
            node = node.getProximo();
        }
        return largo;
    }

    public boolean existeElemento(T elemento) {
        Node<T> elementoComparado = head;
        while (elementoComparado != null) {
            if (elementoComparado.getElemento().equals(elemento)) {
                return true;
            }
            elementoComparado = elementoComparado.getProximo();
        }
        return false;
    }

    public void addFirst(T value){
        Node<T> nuevoHead = new Node(value);
        nuevoHead.setProximo(head);
        this.setHead(nuevoHead);
    }

    public void addLast(T value){
        Node<T> nuevoCola = new Node<T>(value);
        if (head == null) {
            head = nuevoCola;
        } else {
            Node<T> ultimo = getUltimo();
            ultimo.setProximo(nuevoCola);
        }
    }

    public Node<T> getHead() {
        return head;
    }

    private void setHead(Node<T> value) {
        this.head = value;
    }

    public Node<T> getUltimo() {
        Node<T> ultimo = head;
        if (head == null){
            return null;
        }
        while (ultimo.getProximo() != null) {
            ultimo = ultimo.getProximo();
        }
        return ultimo;
    }

    private Node obtenerNodo(int indice){
        if (indice == 0){
            return head;
        }
        if (indice > this.obtenerLargo()){
            return null;
        }
        Node nodeABuscar = this.getHead();
        for (int i = 0; i < indice; i++) {
            nodeABuscar = nodeABuscar.getProximo();
        }
        return nodeABuscar;
    }

    @Override
    public String toString() {
        if (this.head == null) {
            return "Está vacía.";
        }

        StringBuilder sb = new StringBuilder();
        Node<T> actual = this.head;

        while (actual != null) {
            sb.append(actual.getElemento()).append(" ");
            actual = actual.getProximo();
        }

        sb.append("null");
        return sb.toString();
    }

    public boolean isEmpty(){
        if(head == null){
            return true;
        }
        return false;
    }



}

