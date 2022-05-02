package br.manager.bicycle.projetointegrador.models;

public class Usuario {

    private int idUsuario;
    private String login;
    private String senha;
    
    public Usuario(int idUsuario, String login, String senha) {
        this.idUsuario = idUsuario;
        this.login = login;
        this.senha = senha;
    }

    public Usuario(String login, String senha) {
        this(-1, login, senha);
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}