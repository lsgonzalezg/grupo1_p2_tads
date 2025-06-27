package um.edu.uy.querys;
import um.edu.uy.entities.Collection;
import um.edu.uy.tads.NodeHash;
import um.edu.uy.tads.MyHashTableLineal;

public class Query3Top5RevenuesByCollections {

    private MyHashTableLineal<Integer, Collection> collections;

    public Query3Top5RevenuesByCollections(MyHashTableLineal<Integer, Collection> collections) {
        this.collections = collections;
    }

    public void top5RevenuesByCollections() {
        try {
            //Creo el top5 como Array ya que no lo puedo hacer en Heap porque la clave seria el revenue y es long (necesito int para nuestra impl)
            Collection[] topCollections = new Collection[5];
            int cant_Collections = 0;

            for (NodeHash<Integer, Collection> node : collections) {
                Collection collection = node.getValor();
                long revenueTotal = collection.calculateTotalRevenue();

                if (cant_Collections < 5) {
                    //Si no esta lleno lo meto directamente, si tiene un valor mas chico adelante lo cambia.
                    insertSortedByRevenue(topCollections, collection, ++cant_Collections);
                } else {
                    //Comparo con el ultimo
                    long worstRevenue = topCollections[4].calculateTotalRevenue();
                    if (revenueTotal > worstRevenue) {
                        insertSortedByRevenue(topCollections, collection, 5);
                    }
                }
            }
            System.out.println("Top 5 de las colecciones que mas generaron:");
            for (int i = 0; i < topCollections.length; i++) {
                if (topCollections[i] != null) {
                    System.out.println(topCollections[i].getId() + ", " + topCollections[i].getName() + ", " + topCollections[i].getMovies().obtenerLargo() + ", [" + topCollections[i].toString() + "] ," + topCollections[i].calculateTotalRevenue());
                }
            }
        } catch (Exception e) {
        }
    }

    private void insertSortedByRevenue(Collection[] array, Collection newCollection, int cant_Top) {
        long newCompanyRevenue = newCollection.calculateTotalRevenue();
        int i = cant_Top - 1;

        while (i > 0 && array[i - 1] != null && array[i - 1].calculateTotalRevenue() < newCompanyRevenue) {
            array[i] = array[i - 1];
            i--;
        }
        array[i] = newCollection;
    }
}
