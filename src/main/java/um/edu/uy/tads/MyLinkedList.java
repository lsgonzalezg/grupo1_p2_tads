package um.edu.uy.tads;


public class MyLinkedList<T> implements MyList<T> {

    private Nodo<T> head;

    @Override
    public void add(T elemento) {
        Nodo<T> nuevo = new Nodo<T>(elemento);
        if (head == null) {
            head = nuevo;
        }
        else {
            Nodo<T> ultimo = this.getUltimo();
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

        Nodo<T> nodoAnterior = this.obtenerNodo(indiceDelElemento - 1);
        Nodo<T> nodoARemover = this.obtenerNodo(indiceDelElemento);

        // If removing the last node
        if (nodoARemover.getProximo() == null) {
            nodoAnterior.setProximo(null);
        } else {
            nodoAnterior.setProximo(nodoARemover.getProximo());
        }

    }

    @Override
    public T get(int index) throws IndexOutOfBoundsException {
        if (index >= this.obtenerLargo()) {
            throw new IndexOutOfBoundsException("El indice no existe en la lista");
        }
        Nodo<T> nodoABuscar = head;
        for (int i = 0; i < index; i++) {
            nodoABuscar = nodoABuscar.getProximo();
        }
        return nodoABuscar.getElemento();
    }

    public int obtenerLargo() {
        if (head == null) {
            return 0;
        }
        int largo = 0;
        Nodo<T> nodo = head;
        if (nodo.getProximo() == null) {
            return 1;
        }
        while (nodo != null) {
            largo++;
            nodo = nodo.getProximo();
        }
        return largo;
    }

    public boolean existeElemento(T elemento) {
        Nodo<T> elementoComparado = head;
        while (elementoComparado != null) {
            if (elementoComparado.getElemento().equals(elemento)) {
                return true;
            }
            elementoComparado = elementoComparado.getProximo();
        }
        return false;
    }

    public void addFirst(T value){
        Nodo<T> nuevoHead = new Nodo(value);
        nuevoHead.setProximo(head);
        this.setHead(nuevoHead);
    }

    public void addLast(T value){
        Nodo<T> nuevoCola = new Nodo<T>(value);
        if (head == null) {
            head = nuevoCola;
        } else {
            Nodo<T>ultimo = getUltimo();
            ultimo.setProximo(nuevoCola);
        }
    }

    public Nodo<T> getHead() {
        return head;
    }

    private void setHead(Nodo<T> value) {
        this.head = value;
    }

    public Nodo<T> getUltimo() {
        Nodo<T> ultimo = head;
        if (head == null){
            return null;
        }
        while (ultimo.getProximo() != null) {
            ultimo = ultimo.getProximo();
        }
        return ultimo;
    }

    private Nodo obtenerNodo(int indice){
        if (indice == 0){
            return head;
        }
        if (indice > this.obtenerLargo()){
            return null;
        }
        Nodo nodoABuscar = this.getHead();
        for (int i = 0; i < indice; i++) {
            nodoABuscar = nodoABuscar.getProximo();
        }
        return nodoABuscar;
    }

    @Override
    public String toString() {
        if (this.head == null) {
            return "Está vacía.";
        }

        StringBuilder sb = new StringBuilder();
        Nodo<T> actual = this.head;

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

