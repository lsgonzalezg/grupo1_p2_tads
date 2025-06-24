package um.edu.uy.tads;
import um.edu.uy.exceptions.EmptyTreeException;
import um.edu.uy.interfaces.MyBinariTreeCompleto;


public class MyBinaryTreeCompleateImpl<K extends Comparable<K>,T> implements MyBinariTreeCompleto<K,T> {
    private NodoHeap<K,T> root;
    public MyBinaryTreeCompleateImpl() {
        this.root = null;
    }

    public NodoHeap<K, T> getRoot() {
        return root;
    }

    public void setRoot(NodoHeap<K, T> root) {
        this.root = root;
    }

    @Override
    public void insert(K key, T data) {
        if (root == null){
            NodoHeap<K,T> nuevoNodo = new NodoHeap<>(key,data);
            this.root = nuevoNodo;
        }
        else{
            MyLinkedList<NodoHeap<K,T>> porNivel = porNivel();
            int lugarAAgregar = porNivel.obtenerLargo()+1;
            NodoHeap<K,T> nuevoNodo = new NodoHeap<>(key,data);
            NodoHeap<K,T> nodoPadre = root;
            if ((lugarAAgregar)%2 == 0){
                nodoPadre = porNivel.get((lugarAAgregar/2) -1);
                nodoPadre.setLeftChild(nuevoNodo);
                nuevoNodo.setParent(nodoPadre);
            }
            else if(lugarAAgregar%2 !=0){
                nodoPadre = porNivel.get(((lugarAAgregar-1)/2) -1);
                nodoPadre.setRightChild(nuevoNodo);
                nuevoNodo.setParent(nodoPadre);
            }
        }

    }

    @Override
    public void remove(K key) {
        MyLinkedList<NodoHeap<K,T>> nodosPorNivel = listaPorNivel(root);
        if (nodosPorNivel.obtenerLargo() == 0){
            throw new EmptyTreeException("El arbol esta vacio");
        }
        NodoHeap<K,T> nodoAEliminar = encontrarNodo(key,nodosPorNivel);
        NodoHeap<K,T> ultimoNodoAgregado = nodosPorNivel.getUltimo().getElemento();
        remplazarNodo(nodoAEliminar,ultimoNodoAgregado);
        eliminarUltimoNodo(ultimoNodoAgregado);
    }

    private NodoHeap<K,T> encontrarNodo(K key, MyLinkedList<NodoHeap<K,T>> lista){
        for (int i = 0; i < lista.obtenerLargo();i++){
            if (lista.get(i).getKey().equals(key)){
                return lista.get(i);
            }
        }
        return null;
    }

    private void remplazarNodo(NodoHeap<K,T> nodoAEliminar, NodoHeap<K,T> ultimoNodo){
        nodoAEliminar.setKey(ultimoNodo.getKey());
        ultimoNodo.setKey(null);
        nodoAEliminar.setData(ultimoNodo.getData());
        ultimoNodo.setData(null);
    }

    private void eliminarUltimoNodo(NodoHeap<K,T> ultimoNodo) {
            NodoHeap<K,T> padre = ultimoNodo.getParent();
            if (padre.getLeftChild() == ultimoNodo) {
                padre.setLeftChild(null);
            } else if (padre.getRightChild() == ultimoNodo) {
                padre.setRightChild(null);
            }
        }

    public MyLinkedList<NodoHeap<K,T>> porNivel() {
        if (root == null) {
            System.out.println("El arbol esta vacio");
        }
        return listaPorNivel(root);
    }


    private MyLinkedList<NodoHeap<K,T>> listaPorNivel(NodoHeap<K,T> nodoBinario) {
        if(nodoBinario == null){
            return null;
        }
        ColaListaEnlazada<NodoHeap<K, T>> cola = new ColaListaEnlazada<>();
        MyLinkedList<NodoHeap<K,T>> resultado = new MyLinkedList<>();

        cola.enqueue(nodoBinario);

        while (!cola.isEmpty()) {
            NodoHeap<K, T> actual = cola.dequeue();
            resultado.add(actual);
            if (actual.getLeftChild() != null) {
                cola.enqueue(actual.getLeftChild());
            }
            if (actual.getRightChild() != null) {
                cola.enqueue(actual.getRightChild());
            }
        }
        return resultado;
    }

    @Override
    public String toString() {
        if (root == null) {
            System.out.println("Árbol vacío");
            return null;
        }

        // Calcular altura del árbol
        int altura = calcularAltura(root);

        // Calcular ancho necesario
        int anchoMaximo = (int) Math.pow(2, altura) * 4;

        ColaListaEnlazada<NodoHeap<K, T>> cola = new ColaListaEnlazada<>();
        cola.enqueue(root);

        for (int nivel = 0; nivel < altura; nivel++) {
            int nodosEnNivel = (int) Math.pow(2, nivel);
            int espacioEntre = anchoMaximo / (nodosEnNivel + 1);


            for (int i = 0; i < espacioEntre / 2; i++) {
                System.out.print(" ");
            }


            for (int i = 0; i < nodosEnNivel && !cola.isEmpty(); i++) {
                NodoHeap<K, T> actual = cola.dequeue();

                if (actual != null) {
                    System.out.print(actual.getKey());

                    cola.enqueue(actual.getLeftChild());
                    cola.enqueue(actual.getRightChild());
                } else {
                    System.out.print(" ");

                    cola.enqueue(null);
                    cola.enqueue(null);
                }

                for (int j = 0; j < espacioEntre; j++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        return null;
    }

    private int calcularAltura(NodoHeap<K, T> nodo) {
        if (nodo == null) {
                return 0;
        }

        int alturaIzq = calcularAltura(nodo.getLeftChild());
        int alturaDer = calcularAltura(nodo.getRightChild());

        return Math.max(alturaIzq, alturaDer) + 1;
    }

    public NodoHeap<K,T> getUltimoElemento(){
        MyLinkedList<NodoHeap<K,T>> nodosPorNivel = porNivel();
        return nodosPorNivel.getUltimo().getElemento();
    }

    public void moverALugarCorrectoSubida(NodoHeap<K, T> nodoHeap,boolean maxOrMin) {
        if (nodoHeap == null || nodoHeap.getParent() == null) {
            return;
        }
        NodoHeap<K, T> padre = nodoHeap.getParent();
        boolean nececitaIntercambiar = false;

        //Verifico si es min o max y decido si debo intercambiar
        if (maxOrMin) { // Heap máximo
            nececitaIntercambiar = nodoHeap.compareTo(padre) > 0;
        } else { // Heap mínimo
            nececitaIntercambiar = nodoHeap.compareTo(padre) < 0;
        }
        if (nececitaIntercambiar) {
            intercambiarDatos(nodoHeap, padre);
            moverALugarCorrectoSubida(padre, maxOrMin);
        }
    }

    public void moverALugarCorrectobajada(NodoHeap<K,T > nodoHeap, boolean maxOrMin){
        if (nodoHeap == null) {
            return;
        }

        NodoHeap<K, T> candidato = nodoHeap;
        NodoHeap<K, T> hijoIzq = nodoHeap.getLeftChild();
        NodoHeap<K, T> hijoDer = nodoHeap.getRightChild();

        if (maxOrMin) { // Heap máximo
            if (hijoIzq != null && hijoIzq.compareTo(candidato) > 0) {
                candidato = hijoIzq;
            }
            if (hijoDer != null && hijoDer.compareTo(candidato) > 0) {
                candidato = hijoDer;
            }
        } else { // Heap mínimo
            if (hijoIzq != null && hijoIzq.compareTo(candidato) < 0) {
                candidato = hijoIzq;
            }
            if (hijoDer != null && hijoDer.compareTo(candidato) < 0) {
                candidato = hijoDer;
            }
        }

        if (candidato != nodoHeap) {
            intercambiarDatos(nodoHeap, candidato);
            moverALugarCorrectobajada(candidato, maxOrMin);
        }
    }

    private void intercambiarDatos(NodoHeap<K, T> nodo1, NodoHeap<K, T> nodo2) {
        K tempKey = nodo1.getKey();
        T tempData = nodo1.getData();

        nodo1.setKey(nodo2.getKey());
        nodo1.setData(nodo2.getData());

        nodo2.setKey(tempKey);
        nodo2.setData(tempData);
    }

    public boolean isEmpty(){
        if(root == null){
            return true;
        }
        return false;
    }
}
