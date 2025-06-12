package um.edu.uy.entidades;

public class Crew {
    Integer credit_id;
    String department;
    Integer gender;
    String id;
    String job;
    String name;
    String profile_path;

    public Crew(Integer credit_id, String department, Integer gender, String id, String job, String name, String profile_path) {
        this.credit_id = credit_id;
        this.department = department;
        this.gender = gender;
        this.id = id;
        this.job = job;
        this.name = name;
        this.profile_path = profile_path;
    }
}
