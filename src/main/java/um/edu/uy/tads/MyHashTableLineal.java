package um.edu.uy.tads;

import um.edu.uy.exceptions.ElementAlreadyExistException;
import um.edu.uy.exceptions.ElementDosentExistException;
import um.edu.uy.interfaces.MyHashTable;

import java.util.Iterator;
import java.util.NoSuchElementException;


public class MyHashTableLineal<K extends Comparable<K>, V> implements MyHashTable<K, V>, Iterable<NodeHash<K, V>> {

    private NodeHash<K, V>[] tabla;
    private int tamanio;       // Número de elementos
    private int capacidad;   // Capacidad de la tabla

    public MyHashTableLineal(int capacidadInicial) {
        this.capacidad = capacidadInicial;
        this.tabla = (NodeHash<K, V>[]) new NodeHash[this.capacidad];
        this.tamanio = 0;
    }

    @Override
    public void insert(K clave, V valor) throws ElementAlreadyExistException {
        if ((double) tamanio / capacidad >= 0.70) {
            redimensionarTabla();
        }

        int hash = obtenerIndiceHash(clave);
        for (int i = 0; i < capacidad; i++) {
            int indiceSondeo = (hash + i) % capacidad;

            if (tabla[indiceSondeo] == null) {
                tabla[indiceSondeo] = new NodeHash<>(clave, valor);
                tamanio++;
                return;
            }

            if (tabla[indiceSondeo].getClave().equals(clave)) {
                throw new ElementAlreadyExistException("La clave ya existe: " + clave);
            }
        }
    }

    @Override
    public V search(K clave) throws ElementDosentExistException {
        int indice = encontrarIndiceParaClave(clave);
        if (indice == -1) {
            throw new ElementDosentExistException("No se encontró la clave: " + clave);
        }
        return tabla[indice].getValor();
    }

    @Override
    public boolean belongs(K clave) {
        return encontrarIndiceParaClave(clave) != -1;
    }

    @Override
    public void borrar(K clave) {
        int indice = encontrarIndiceParaClave(clave);
        if (indice != -1) {
            tabla[indice] = null;
            tamanio--;
            reOrdenar(indice);
        }
    }

    @Override
    public int tamanio() {
        return this.tamanio;
    }

    public NodeHash<K, V>[] getTabla() {
        return tabla;
    }

    private int encontrarIndiceParaClave(K clave) {
        int hash = obtenerIndiceHash(clave);
        for (int i = 0; i < capacidad; i++) {
            int indiceSondeo = (hash + i) % capacidad;

            if (tabla[indiceSondeo] == null) {
                return -1;
            }

            if (tabla[indiceSondeo].getClave().equals(clave)) {
                return indiceSondeo;
            }
        }
        return -1;
    }

    private void reOrdenar(int indiceInicio) {
        int indiceSondeo = (indiceInicio + 1) % capacidad;
        while (tabla[indiceSondeo] != null) {
            NodeHash<K, V> nodoARehashear = tabla[indiceSondeo];
            tabla[indiceSondeo] = null;
            tamanio--;
            try {

                insert(nodoARehashear.getClave(), nodoARehashear.getValor());
            } catch (ElementAlreadyExistException e) {
                System.err.println("Error inesperado durante el rehasheo del clúster: " + e.getMessage());
            }
            indiceSondeo = (indiceSondeo + 1) % capacidad;
        }
    }

    private void redimensionarTabla() {
        NodeHash<K, V>[] tablaAnterior = tabla;
        int capacidadAnterior = capacidad;

        this.capacidad = proximoPrimo(capacidad * 2);
        this.tabla = (NodeHash<K, V>[]) new NodeHash[this.capacidad];
        this.tamanio = 0;

        for (int i = 0; i < capacidadAnterior; i++) {
            if (tablaAnterior[i] != null) {
                try {
                    insert(tablaAnterior[i].getClave(), tablaAnterior[i].getValor());
                } catch (ElementAlreadyExistException e) {
                    System.err.println("Error inesperado durante la redimensión: " + e.getMessage());
                }
            }
        }
    }

    private int obtenerIndiceHash(K clave) {
        return Math.abs(clave.hashCode()) % capacidad;
    }

    private int proximoPrimo(int n) {
        while (!esPrimo(n)) {
            n++;
        }
        return n;
    }

    private boolean esPrimo(int n) {
        if (n <= 1) return false;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    @Override
    public Iterator<NodeHash<K, V>> iterator() {
        return new IteradorTablaHash();
    }

    private class IteradorTablaHash implements Iterator<NodeHash<K, V>> {
        private int indiceActual = 0;
        private int elementosDevueltos = 0;

        @Override
        public boolean hasNext() {
            return elementosDevueltos < tamanio;
        }

        @Override
        public NodeHash<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            while (tabla[indiceActual] == null) {
                indiceActual++;
            }
            elementosDevueltos++;
            return tabla[indiceActual++];
        }
    }
}
