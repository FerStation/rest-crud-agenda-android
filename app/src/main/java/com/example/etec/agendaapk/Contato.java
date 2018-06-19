package com.example.etec.agendaapk;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fernando on 31/07/17.
 */

public class Contato {

    //atributos
    @SerializedName("ID")
    @Expose
    private int ID;

    @SerializedName("nome")
    @Expose
    private String nome;

    private String email;

    @SerializedName("telefone")
    private long tel;

    @SerializedName("celular")
    private long cel;

    //!add a foto....


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getTel() {
        return tel;
    }

    public void setTel(long tel) {
        this.tel = tel;
    }

    public long getCelular() {
        return cel;
    }

    public void setCelular(long celular) {
        this.cel = celular;
    }
}
