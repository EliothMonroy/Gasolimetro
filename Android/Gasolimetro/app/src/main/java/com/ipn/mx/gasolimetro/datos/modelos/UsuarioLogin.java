package com.ipn.mx.gasolimetro.datos.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UsuarioLogin implements Parcelable {

    @SerializedName("idUsuario")
    private Long idUsuario;
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
    private EstadoUsuario estadoUsuario;
    @SerializedName("login")
    private LoginServer login;
    @SerializedName("puerto")
    private Long puerto;

    /**
     * No args constructor for use in serialization
     * 
     */
    public UsuarioLogin() {
    }

    /**
     * 
     * @param nombre
     * @param genero
     * @param idUsuario
     * @param tipoUsuario
     * @param puerto
     * @param apellidoMaterno
     * @param apellidoPaterno
     * @param login
     * @param estadoUsuario
     */
    public UsuarioLogin(Long idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, Boolean genero, int tipoUsuario, EstadoUsuario estadoUsuario, LoginServer login, Long puerto) {
        super();
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.genero = genero;
        this.tipoUsuario = tipoUsuario;
        this.estadoUsuario = estadoUsuario;
        this.login = login;
        this.puerto = puerto;
    }

    protected UsuarioLogin(Parcel in) {
        if (in.readByte() == 0) {
            idUsuario = null;
        } else {
            idUsuario = in.readLong();
        }
        nombre = in.readString();
        apellidoPaterno = in.readString();
        apellidoMaterno = in.readString();
        byte tmpGenero = in.readByte();
        genero = tmpGenero == 0 ? null : tmpGenero == 1;
        tipoUsuario = in.readInt();
        estadoUsuario=in.readParcelable(EstadoUsuario.class.getClassLoader());
        login=in.readParcelable(LoginServer.class.getClassLoader());
        if (in.readByte() == 0) {
            puerto = null;
        } else {
            puerto = in.readLong();
        }
    }

    public static final Creator<UsuarioLogin> CREATOR = new Creator<UsuarioLogin>() {
        @Override
        public UsuarioLogin createFromParcel(Parcel in) {
            return new UsuarioLogin(in);
        }

        @Override
        public UsuarioLogin[] newArray(int size) {
            return new UsuarioLogin[size];
        }
    };

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

    public EstadoUsuario getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(EstadoUsuario estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public LoginServer getLogin() {
        return login;
    }

    public void setLogin(LoginServer login) {
        this.login = login;
    }

    public Long getPuerto() {
        return puerto;
    }

    public void setPuerto(Long puerto) {
        this.puerto = puerto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idUsuario == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idUsuario);
        }
        dest.writeString(nombre);
        dest.writeString(apellidoPaterno);
        dest.writeString(apellidoMaterno);
        dest.writeByte((byte) (genero == null ? 0 : genero ? 1 : 2));

        dest.writeInt(tipoUsuario);
        dest.writeParcelable(estadoUsuario,flags);
        dest.writeParcelable(login,flags);

        if (puerto == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(puerto);
        }
    }
}
