
package com.ipn.mx.gasolimetro.datos.modelos;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class EstadoUsuario implements Parcelable {

    @SerializedName("idEstado")
    private Long idEstado;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("codigo")
    private String codigo;

    /**
     * No args constructor for use in serialization
     * 
     */
    public EstadoUsuario() {
    }

    /**
     * 
     * @param codigo
     * @param descripcion
     * @param idEstado
     */
    public EstadoUsuario(Long idEstado, String descripcion, String codigo) {
        super();
        this.idEstado = idEstado;
        this.descripcion = descripcion;
        this.codigo = codigo;
    }

    protected EstadoUsuario(Parcel in) {
        if (in.readByte() == 0) {
            idEstado = null;
        } else {
            idEstado = in.readLong();
        }
        descripcion = in.readString();
        codigo = in.readString();
    }

    public static final Creator<EstadoUsuario> CREATOR = new Creator<EstadoUsuario>() {
        @Override
        public EstadoUsuario createFromParcel(Parcel in) {
            return new EstadoUsuario(in);
        }

        @Override
        public EstadoUsuario[] newArray(int size) {
            return new EstadoUsuario[size];
        }
    };

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idEstado == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idEstado);
        }
        dest.writeString(descripcion);
        dest.writeString(codigo);
    }
}
