package um.edu.uy.tads;

public class MyArrayList<T> {
    private T[] elementos;
    private int tamano;
    private static final int capacidadInicial = 10;

    public MyArrayList() {
        elementos = (T[]) new Object[capacidadInicial];
        tamano = 0;
    }

    public void add(T elemento) {
        asegurarCapacidad();
        elementos[tamano] = elemento;
        tamano++;
    }

    public T get(int indice) {
        verificarIndice(indice);
        return elementos[indice];
    }

    public T remove(int indice) {
        verificarIndice(indice);
        T eliminado = elementos[indice];

        for (int i = indice; i < tamano - 1; i++) {
            elementos[i] = elementos[i + 1];
        }

        elementos[--tamano] = null;
        return eliminado;
    }

    public int size() {
        return tamano;
    }

    private void verificarIndice(int indice) {
        if (indice < 0 || indice >= tamano) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }

    private void asegurarCapacidad() {
        if (tamano == elementos.length) {
            T[] nuevoArray = (T[]) new Object[elementos.length * 2];
            System.arraycopy(elementos, 0, nuevoArray, 0, elementos.length);
            elementos = nuevoArray;
        }
    }

    public boolean pertenece(T elementoABuscar){
        for (T elemento: elementos){
            if (elemento.equals(elementoABuscar)){
                return true;
            }
        }
        return false;
    }
}
