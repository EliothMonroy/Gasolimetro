package com.ipn.mx.gasolimetro.datos.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LoginServer implements Parcelable {

    @SerializedName("idLogin")
    private Long idLogin;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    /**
     * No args constructor for use in serialization
     *
     */
    public LoginServer() {
    }

    /**
     *
     * @param email
     * @param idLogin
     * @param password
     */
    public LoginServer(Long idLogin, String email, String password) {
        super();
        this.idLogin = idLogin;
        this.email = email;
        this.password = password;
    }

    protected LoginServer(Parcel in) {
        if (in.readByte() == 0) {
            idLogin = null;
        } else {
            idLogin = in.readLong();
        }
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<LoginServer> CREATOR = new Creator<LoginServer>() {
        @Override
        public LoginServer createFromParcel(Parcel in) {
            return new LoginServer(in);
        }

        @Override
        public LoginServer[] newArray(int size) {
            return new LoginServer[size];
        }
    };

    public Long getIdLogin() {
        return idLogin;
    }

    public void setIdLogin(Long idLogin) {
        this.idLogin = idLogin;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (idLogin == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(idLogin);
        }
        dest.writeString(email);
        dest.writeString(password);
    }
}