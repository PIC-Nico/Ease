package model;

public class User {
    private final int number;
    private final String salt;

    private String name;
    private String hash;

    public User(int number, String name, String salt, String hash) {
        this.number = number;
        this.name = name;
        this.salt = salt;
        this.hash = hash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getNumber() {
        return number;
    }
}
