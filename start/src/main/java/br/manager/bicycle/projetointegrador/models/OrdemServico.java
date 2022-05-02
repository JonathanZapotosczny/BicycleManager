package br.manager.bicycle.projetointegrador.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class OrdemServico {

    private int idOrdemServico;
    private Cliente cliente;
    private Funcionario tecnicoResponsavel;
    private LocalDateTime dataHora;
    private LocalDate dataEntrega;
    private String garantia;
    private String observacao;
    private Double valorTotal;
    private Double maoObra;
    private ArrayList<ItemServico> itensServico;

    public OrdemServico(int idOrdemServico, Cliente cliente, Funcionario tecnicoResponsavel, LocalDateTime dataHora, LocalDate dataEntrega, String garantia, String observacao, Double valorTotal, Double maoObra, ArrayList<ItemServico> itensServico) {
        this.idOrdemServico = idOrdemServico;
        this.cliente = cliente;
        this.tecnicoResponsavel = tecnicoResponsavel;
        this.dataHora = dataHora;
        this.dataEntrega = dataEntrega;
        this.garantia = garantia;
        this.observacao = observacao;
        this.valorTotal = valorTotal;
        this.maoObra = maoObra;
        this.itensServico = itensServico; 
    }

    public OrdemServico(Cliente cliente, Funcionario tecnicoResponsavel, LocalDateTime dataHora) {
        this(-1, cliente, tecnicoResponsavel, dataHora, null, null, null, 0.0, 0.0, new ArrayList<>());
    }

    public void adicionarItem(ItemServico itemAdiciona) {
        for(ItemServico item:itensServico) {
            if(item.getProduto().getIdProduto() == itemAdiciona.getProduto().getIdProduto()) {

                item.setQuantidade(item.getQuantidade()+itemAdiciona.getQuantidade());

                return;
            }
        }
        itensServico.add(itemAdiciona);
    }

    public double calcularTotal() {
        double total = 0.0;

        for(ItemServico item:itensServico) {
            total += item.getValor()*item.getQuantidade();
        }
        this.valorTotal = total;

        return this.valorTotal;
    }

    public double calcularTotalMaoObra() {
        double total = 0.0;

        total = calcularTotal() + maoObra;

        return total;
    }

    public int getIdOrdemServico() {
        return idOrdemServico;
    }

    public void setIdOrdemServico(int idOrdemServico) {
        this.idOrdemServico = idOrdemServico;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getTecnicoResponsavel() {
        return tecnicoResponsavel;
    }

    public void setTecnicoResponsavel(Funcionario tecnicoResponsavel) {
        this.tecnicoResponsavel = tecnicoResponsavel;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getGarantia() {
        return garantia;
    }

    public void setGarantia(String garantia) {
        this.garantia = garantia;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getMaoObra() {
        return maoObra;
    }

    public void setMaoObra(Double maoObra) {
        this.maoObra = maoObra;
    }

    public ArrayList<ItemServico> getItensServico() {
        return itensServico;
    }

    public void setItensServico(ArrayList<ItemServico> itensServico) {
        this.itensServico = itensServico;
    }
    
    public String toString() {
        return "ID: " + idOrdemServico + "\nNome Cliente: " + cliente + "\nNome Técnico Responsável: " + tecnicoResponsavel + "\nDataHora: " + dataHora + "\nDataEntrega: " + dataEntrega + "\nGarantia: " + garantia + "\nObservação: " + observacao + "\nValor Total: " + valorTotal + "\nMão de Obra: " + maoObra + "\n Itens de Servico: " + itensServico;
    }
}