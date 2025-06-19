package Models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Lucas
 */

public class User {
    private String name;
    private String email;
    private String city;

    public User(String name, String email, String city) {
        this.name = name;
        this.email = email;
        this.city = city;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getCity() { return city; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setCity(String city) { this.city = city; }

    @Override
    public String toString() {
        return name + " - " + email + " (" + city + ")";
    }
}

