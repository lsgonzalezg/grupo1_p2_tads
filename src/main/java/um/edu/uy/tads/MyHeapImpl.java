package um.edu.uy.tads;
import um.edu.uy.exceptions.HeapIsEmpty;
import um.edu.uy.exceptions.HeapIsFull;
import um.edu.uy.interfaces.MyHeap;

public class MyHeapImpl<K extends Comparable<K>,T> implements MyHeap<K,T> {
    MyBinaryTreeCompleateImpl<K,T> heapTree;
    int maximaCapacidad;
    //Si es un heap max el boolean es true, si es un heap min el boolean es min
    boolean maxOrMin;


    public MyHeapImpl(int size,boolean maxOrMin) {
        this.heapTree = new MyBinaryTreeCompleateImpl<>();
        this.maximaCapacidad = size;
        this.maxOrMin = maxOrMin;
    }

    public NodoHeap<K, T> peek() {
        if (heapTree.isEmpty()) {
            throw new HeapIsEmpty("Heap vacío");
        }
        return heapTree.getRoot();
    }

    @Override
    public void insert(K key,T data) {
        if (obtenerTamano() >= maximaCapacidad){
            throw new HeapIsFull("Heap lleno, capacidad máxima alcanzada");
        }

        heapTree.insert(key, data);
        heapTree.moverALugarCorrectoSubida(heapTree.getUltimoElemento(),maxOrMin);
    }



    @Override
    public NodoHeap<K, T> remove() {
        if(heapTree.isEmpty()){
            return null;
        }
        return removeRoot();
    }

    public NodoHeap<K,T> removeRoot(){
        if (heapTree.isEmpty()) {
            throw new HeapIsEmpty("El heap esta vacio");
        }

        NodoHeap<K, T> raiz = heapTree.getRoot();
        K keyRaiz = raiz.getKey();
        T dataRaiz = raiz.getData();

        NodoHeap<K, T> nodoARetornar = new NodoHeap<>(keyRaiz, dataRaiz);

        if (obtenerTamano() == 1) {
            heapTree.setRoot(null);
            return nodoARetornar;
        }

        NodoHeap<K, T> ultimoElemento = heapTree.getUltimoElemento();

        // intercambiar raiz con ultimo elemento
        raiz.setKey(ultimoElemento.getKey());
        raiz.setData(ultimoElemento.getData());

        // Eliminar el último nodo
        eliminarUltimoNodo(ultimoElemento);

        // Restaurar la propiedad del heap moviendo la nueva raíz hacia abajo
        heapTree.moverALugarCorrectobajada(raiz, maxOrMin);

        return nodoARetornar;
    }

    private void eliminarUltimoNodo(NodoHeap<K,T> ultimoElemento) {
        NodoHeap<K, T> padre = ultimoElemento.getParent();
        if (padre != null) {
            if (padre.getLeftChild() == ultimoElemento) {
                padre.setLeftChild(null);
            } else if (padre.getRightChild() == ultimoElemento) {
                padre.setRightChild(null);
            }
        }
        ultimoElemento.setParent(null);
    }

    @Override
    public int obtenerTamano() {
        if(heapTree.isEmpty()){
            return 0;
        }
        MyLinkedList<NodoHeap<K,T>> listaPorNivel =  heapTree.porNivel();
        return listaPorNivel.obtenerLargo();
    }

    public void imprimirArbol(){
        heapTree.toString();
    }

}
