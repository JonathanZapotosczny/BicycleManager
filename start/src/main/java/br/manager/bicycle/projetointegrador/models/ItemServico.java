package br.manager.bicycle.projetointegrador.models;


public class ItemServico {

    private int idItensServico; 
    private Produto produto;
    private int quantidade;
    private double valor;
    
    public ItemServico(int idItensServico, Produto produto, int quantidade, double valor) {
        this.setIdItensServico(idItensServico);
        this.setProduto(produto);
        this.setQuantidade(quantidade);
        this.setValor(valor);
    }

    public ItemServico(Produto produto, int quantidade, double valor) {
        this(-1,produto,quantidade,valor);
    }

    public double getSubTotal() {
        return quantidade * valor;
    }

    public int getIdItensServico() {
        return idItensServico;
    }

    public void setIdItensServico(int idItensServico) {
        this.idItensServico = idItensServico;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String toString() {
        return "Nome: " + produto.getNome() + "\nID: " + idItensServico + "\nQuantidade: " + quantidade + "\nValor: " + valor;
    }
}

