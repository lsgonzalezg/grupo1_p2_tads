package um.edu.uy.entidades;

public class Cast {
    Integer cast_id;
    String character;
    String credit_Id;
    Integer gender;
    Integer id;
    String name;
    Integer order;
    String profile_path;

    public Cast(Integer cast_id, String character, String credit_Id, Integer gender, Integer id, String name, Integer order, String profile_path) {
        this.cast_id = cast_id;
        this.character = character;
        this.credit_Id = credit_Id;
        this.gender = gender;
        this.id = id;
        this.name = name;
        this.order = order;
        this.profile_path = profile_path;
    }
}
