package um.edu.uy.entities;

public class Crew {
    private String id;
    private String job;
    private String name;

    public Crew(String id, String job, String name) {
        this.id = id;
        this.job = job;
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
