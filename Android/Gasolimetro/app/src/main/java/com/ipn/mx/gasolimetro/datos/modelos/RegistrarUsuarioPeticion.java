
package com.ipn.mx.gasolimetro.datos.modelos;


import com.google.gson.annotations.SerializedName;

public class RegistrarUsuarioPeticion {
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("apellidoPaterno")
    private String apellidoPaterno;
    @SerializedName("apellidoMaterno")
    private String apellidoMaterno;
    @SerializedName("genero")
    private Boolean genero;
    @SerializedName("tipoUsuario")
    private int tipoUsuario;
    @SerializedName("estadoUsuario")
    private EstadoUsuarioRegistrarUsuario estadoUsuario;
    @SerializedName("login")
    private Login login;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RegistrarUsuarioPeticion() {
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
    public RegistrarUsuarioPeticion(String nombre, String apellidoPaterno, String apellidoMaterno, Boolean genero, int tipoUsuario, EstadoUsuarioRegistrarUsuario estadoUsuario, Login login) {
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

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public EstadoUsuarioRegistrarUsuario getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(EstadoUsuarioRegistrarUsuario estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

}
