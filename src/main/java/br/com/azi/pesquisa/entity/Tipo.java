package br.com.azi.pesquisa.entity;

/**
 * Created by DMartinez on 13/11/2015.
 */
public class Tipo {
    public static int _id = 0;

    private Integer id;
    private String nome;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
