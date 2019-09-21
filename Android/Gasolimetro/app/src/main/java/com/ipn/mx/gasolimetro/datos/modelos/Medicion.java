
package com.ipn.mx.gasolimetro.datos.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Medicion implements Parcelable {

    @SerializedName("finMedicion")
    private String finMedicion;
    @SerializedName("litrosSolicitados")
    private Double litrosSolicitados;
    @SerializedName("litrosIngresados")
    private Double litrosIngresados;
    @SerializedName("precioSolicitado")
    private Double precioSolicitado;
    @SerializedName("precioPagado")
    private Double precioPagado;
    @SerializedName("bomba")
    private Integer bomba;

    /**
     * No args constructor for use in serialization
     *
     */
    public Medicion() {
    }

    /**
     *
     * @param bomba
     * @param litrosSolicitados
     * @param precioSolicitado
     * @param precioPagado
     * @param finMedicion
     * @param litrosIngresados
     */
    public Medicion(String finMedicion, Double litrosSolicitados, Double litrosIngresados, Double precioSolicitado, Double precioPagado, Integer bomba) {
        super();
        this.finMedicion = finMedicion;
        this.litrosSolicitados = litrosSolicitados;
        this.litrosIngresados = litrosIngresados;
        this.precioSolicitado = precioSolicitado;
        this.precioPagado = precioPagado;
        this.bomba = bomba;
    }

    protected Medicion(Parcel in) {
        finMedicion = in.readString();
        if (in.readByte() == 0) {
            litrosSolicitados = null;
        } else {
            litrosSolicitados = in.readDouble();
        }
        if (in.readByte() == 0) {
            litrosIngresados = null;
        } else {
            litrosIngresados = in.readDouble();
        }
        if (in.readByte() == 0) {
            precioSolicitado = null;
        } else {
            precioSolicitado = in.readDouble();
        }
        if (in.readByte() == 0) {
            precioPagado = null;
        } else {
            precioPagado = in.readDouble();
        }
        if (in.readByte() == 0) {
            bomba = null;
        } else {
            bomba = in.readInt();
        }
    }

    public static final Creator<Medicion> CREATOR = new Creator<Medicion>() {
        @Override
        public Medicion createFromParcel(Parcel in) {
            return new Medicion(in);
        }

        @Override
        public Medicion[] newArray(int size) {
            return new Medicion[size];
        }
    };

    public String getFinMedicion() {
        return finMedicion;
    }

    public void setFinMedicion(String finMedicion) {
        this.finMedicion = finMedicion;
    }

    public Double getLitrosSolicitados() {
        return litrosSolicitados;
    }

    public void setLitrosSolicitados(Double litrosSolicitados) {
        this.litrosSolicitados = litrosSolicitados;
    }

    public Double getLitrosIngresados() {
        return litrosIngresados;
    }

    public void setLitrosIngresados(Double litrosIngresados) {
        this.litrosIngresados = litrosIngresados;
    }

    public Double getPrecioSolicitado() {
        return precioSolicitado;
    }

    public void setPrecioSolicitado(Double precioSolicitado) {
        this.precioSolicitado = precioSolicitado;
    }

    public Double getPrecioPagado() {
        return precioPagado;
    }

    public void setPrecioPagado(Double precioPagado) {
        this.precioPagado = precioPagado;
    }

    public Integer getBomba() {
        return bomba;
    }

    public void setBomba(Integer bomba) {
        this.bomba = bomba;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(finMedicion);
        if (litrosSolicitados == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(litrosSolicitados);
        }
        if (litrosIngresados == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(litrosIngresados);
        }
        if (precioSolicitado == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(precioSolicitado);
        }
        if (precioPagado == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(precioPagado);
        }
        if (bomba == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bomba);
        }
    }
}
