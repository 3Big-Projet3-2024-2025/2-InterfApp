package be.helha.interf_app.Model;

public class PasswordCheckRequest {
    private String id;
    private String password;

    public PasswordCheckRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}