package ex_241023_cha15.homework;

public class Shw1013_DTO {
    private String name;
    private String email;
    private String password;

    // Constructor
    public Shw1013_DTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
