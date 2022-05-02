package br.manager.bicycle.projetointegrador.models;

import java.time.LocalDate;

public class Funcionario {

    private int idFuncionario;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private LocalDate dataAdmissao;
    
    public Funcionario(int idFuncionario, String nome, String email, String cpf, String telefone, LocalDate dataAdmissao) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.dataAdmissao = dataAdmissao;
    }

    public Funcionario(String nome, String email, String cpf, String telefone, LocalDate dataAdmissao) {
        this(-1, nome, email, cpf, telefone, dataAdmissao);
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(int idFuncionario) {
        this.idFuncionario = idFuncionario;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String toString() {
        return nome + "\nID: " + idFuncionario + "\nE-mail: " + email + "\nCPF: " + cpf + "\nTelefone: " + telefone + "\nDataAdmissão: " + dataAdmissao;
    }
}