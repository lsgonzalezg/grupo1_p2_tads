package um.edu.uy.entities;

public class Cast {
    private String id;
    private String name;

    public Cast(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

}
