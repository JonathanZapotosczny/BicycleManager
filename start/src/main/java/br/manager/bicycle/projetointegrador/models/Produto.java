package br.manager.bicycle.projetointegrador.models;

public class Produto {

    private int idProduto;
    private String nome;
    private String descricao;
    private Double valor;
    
    public Produto(int idProduto, String nome, String descricao, Double valor) {
        this.setIdProduto(idProduto);
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setValor(valor);
    }

    public Produto(String nome, String descricao, Double valor) {
        this(-1, nome, descricao, valor);
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String toString() {
        return nome + "\nID: " + idProduto + "\nDescricao: " + descricao + "\nValor R$: " + valor;
    }
}