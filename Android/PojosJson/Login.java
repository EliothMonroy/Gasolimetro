
package com.example;


public class AgregarAutomovil {

    private String email;
    private String password;

    /**
     * No args constructor for use in serialization
     * 
     */
    public AgregarAutomovil() {
    }

    /**
     * 
     * @param email
     * @param password
     */
    public AgregarAutomovil(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
