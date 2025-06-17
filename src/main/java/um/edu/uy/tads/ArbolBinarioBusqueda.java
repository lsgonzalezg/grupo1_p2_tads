package um.edu.uy.tads;
import um.edu.uy.exceptions.EmptyTreeException;

public class ArbolBinarioBusqueda<K extends Comparable<K>,T> implements MyBinarySearchTree<K,T> {

    NodeBST<K,T> root;


    @Override
    public T find(K key) {
        NodeBST<K,T> actual = root;
        while (key.compareTo(actual.getKey())!=0 && actual.getKey() != null){
            if(key.compareTo(actual.getKey()) == -1){
                actual = actual.getLeftChild();
            }
            if(key.compareTo(actual.getKey()) == 1){
                actual = actual.getRightChild();
            }
        }
        return actual.getData();
    }

    @Override
    public void insert(K key, T data) {
        root = insertarRecursivo(root, key, data);
    }

    private NodeBST<K, T> insertarRecursivo(NodeBST<K, T> nodoBST, K key, T data) {
        if (nodoBST == null) {
            return new NodeBST<>(key, data);
        }

        if (key.compareTo(nodoBST.getKey()) < 0) {
            nodoBST.setLeftChild(insertarRecursivo(nodoBST.getLeftChild(), key, data));
        } else if (key.compareTo(nodoBST.getKey()) > 0) {
            nodoBST.setRightChild(insertarRecursivo(nodoBST.getRightChild(), key, data));
        }

        return nodoBST;
    }

    @Override
    public void delete(K key) {
        if (root == null){
            throw new EmptyTreeException("El arbol esta vacio");
        }
        root = eliminarRecursivo(root,key);

    }

    private NodeBST<K, T> eliminarRecursivo(NodeBST<K, T> nodoBST, K key) {
        //Caso base que no se encontro el nodo a eliminar
        if (nodoBST == null) {
            return null;
        }


        if (key.compareTo(nodoBST.getKey()) < 0) {
            nodoBST.setLeftChild(eliminarRecursivo(nodoBST.getLeftChild(), key));
        } else if (key.compareTo(nodoBST.getKey()) > 0) {
            nodoBST.setRightChild(eliminarRecursivo(nodoBST.getRightChild(), key));
        } else {
            // si el nodo no tiene hijos
            if (nodoBST.getLeftChild() == null && nodoBST.getRightChild() == null) {
                return null;
            }
            // si solo tien un hijo
            if (nodoBST.getLeftChild() == null) {
                return nodoBST.getRightChild();
            }
            if (nodoBST.getRightChild() == null) {
                return nodoBST.getLeftChild();
            }
            // si tiene dos hijos
            NodeBST<K, T> sucesor = encontrarMinimo(nodoBST.getRightChild());
            nodoBST = new NodeBST<>(sucesor.getKey(), sucesor.getData());
            nodoBST.setLeftChild(eliminarRecursivo(nodoBST.getLeftChild(), sucesor.getKey()));//Elimino en cascada
            nodoBST.setRightChild(eliminarRecursivo(nodoBST.getRightChild(), sucesor.getKey()));
        }

        return nodoBST;
    }

    private NodeBST<K, T> encontrarMinimo(NodeBST<K, T> nodoBST) {
        while (nodoBST.getLeftChild() != null) {
            nodoBST = nodoBST.getLeftChild();
        }
        return nodoBST;
    }

    public NodeBST<K, T> findNodo(K key, NodeBST<K, T> Nodo) {
        if (Nodo.getKey().equals(key)) {
            return Nodo;
        }

        if (Nodo.getLeftChild() != null) {
            NodeBST<K, T> LadoIzquierdo = this.findNodo(key, Nodo.getLeftChild());
            if (LadoIzquierdo != null) {
                return LadoIzquierdo;
            }
        }

        if (Nodo.getRightChild() != null) {
            NodeBST<K, T> LadoDerecho = this.findNodo(key, Nodo.getRightChild());
            if (LadoDerecho != null) {
                return LadoDerecho;
            }
        }
        return null;

    }


    @Override
    public int size(NodeBST<K, T> nodoBinario) {
        if (nodoBinario == null) {
            return 0;
        }
        int sizeIzquierda = size(nodoBinario.getLeftChild());
        int sizeDerecha = size(nodoBinario.getRightChild());

        return 1 + sizeIzquierda + sizeDerecha;
    }

    @Override
    public int countLeaf(NodeBST<K, T> nodoBinario) {
        if (nodoBinario == null) {
            return 0;
        }
        if (nodoBinario.getRightChild() == null && nodoBinario.getLeftChild() == null) {
            return 1;
        }
        int hojasIzquierda = countLeaf(nodoBinario.getLeftChild());
        int hojasDerecha = countLeaf(nodoBinario.getRightChild());
        return hojasDerecha + hojasIzquierda;
    }

    @Override
    public int countCompleteElements(NodeBST<K, T> nodoBinario) {
        if (nodoBinario == null) {
            return 0;
        }
        int elementosCompletosIzquierda = countCompleteElements(nodoBinario.getLeftChild());
        int elementosCompletosDerecha = countCompleteElements(nodoBinario.getRightChild());
        if (nodoBinario.getLeftChild() != null && nodoBinario.getRightChild() != null) {
            return 1 + elementosCompletosDerecha + elementosCompletosIzquierda;
        }
        return elementosCompletosDerecha + elementosCompletosIzquierda;
    }

    @Override
    public MyLinkedList<K> inOrder() {
        MyLinkedList<K> listaInOrder = new MyLinkedList<>();
        agregadorDeNodosInOrder(root, listaInOrder);
        return listaInOrder;
    }

    private void agregadorDeNodosInOrder(NodeBST<K, T> nodoBinario, MyLinkedList<K> lista) {
        if (nodoBinario != null) {
            //Agrego nodo Izquiera
            agregadorDeNodosInOrder(nodoBinario.getLeftChild(), lista);
            //Agrego la raiz
            lista.add(nodoBinario.getKey());
            //Agrego nodo Derecho
            agregadorDeNodosInOrder(nodoBinario.getRightChild(), lista);

        }
    }

    @Override
    public MyLinkedList<K> preOrder() {
        MyLinkedList<K> listaPreOrder = new MyLinkedList<>();
        agregadorDeNodosPreOrder(root, listaPreOrder);
        return listaPreOrder;
    }

    private void agregadorDeNodosPreOrder(NodeBST<K, T> nodoBinario, MyLinkedList<K> lista) {
        if (nodoBinario != null) {
            //Agrego la raiz
            lista.add(nodoBinario.getKey());
            //Agrego nodo Izquiera
            agregadorDeNodosPreOrder(nodoBinario.getLeftChild(), lista);
            //Agrego nodo Derecho
            agregadorDeNodosPreOrder(nodoBinario.getRightChild(), lista);

        }
    }

    @Override
    public MyLinkedList<K> posOrder() {
        MyLinkedList<K> listaPostOrder = new MyLinkedList<>();
        agregadorDeNodosPostOrder(root, listaPostOrder);
        return listaPostOrder;
    }

    private void agregadorDeNodosPostOrder(NodeBST<K, T> nodoBinario, MyLinkedList<K> lista) {
        if (nodoBinario != null) {
            //Agrego nodo Izquiera
            agregadorDeNodosPostOrder(nodoBinario.getLeftChild(), lista);
            //Agrego nodo Derecho
            agregadorDeNodosPostOrder(nodoBinario.getRightChild(), lista);
            //Agrego la raiz
            lista.add(nodoBinario.getKey());

        }
    }

    @Override
    public MyLinkedList<K> porNivel() {
        if (root == null) {
            System.out.println("El arbol esta vacio");
        }
        return listaPorNivel(root);
    }

    private MyLinkedList<K> listaPorNivel(NodeBST<K, T> nodoBinario) {
        ColaListaEnlazada<NodeBST<K, T>> cola = new ColaListaEnlazada<>();
        MyLinkedList<K> resultado = new MyLinkedList<>();

        cola.enqueue(nodoBinario);

        while (!cola.isEmpty()) {
            NodeBST<K, T> actual = cola.dequeue();
            resultado.add(actual.getKey());
            if (actual.getLeftChild() != null) {
                cola.enqueue(actual.getLeftChild());
            }
            if (actual.getRightChild() != null) {
                cola.enqueue(actual.getRightChild());
            }
        }
        return resultado;
    }
}
