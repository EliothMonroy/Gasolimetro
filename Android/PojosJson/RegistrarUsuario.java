
package com.example;


public class RegistrarUsuario {

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Boolean genero;
    private Long tipoUsuario;
    private EstadoUsuario estadoUsuario;
    private Login login;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RegistrarUsuario() {
    }

    /**
     * 
     * @param nombre
     * @param genero
     * @param tipoUsuario
     * @param apellidoMaterno
     * @param apellidoPaterno
     * @param login
     * @param estadoUsuario
     */
    public RegistrarUsuario(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean genero, Long tipoUsuario, EstadoUsuario estadoUsuario, Login login) {
        super();
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.genero = genero;
        this.tipoUsuario = tipoUsuario;
        this.estadoUsuario = estadoUsuario;
        this.login = login;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Boolean getGenero() {
        return genero;
    }

    public void setGenero(Boolean genero) {
        this.genero = genero;
    }

    public Long getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Long tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public EstadoUsuario getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(EstadoUsuario estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

}
