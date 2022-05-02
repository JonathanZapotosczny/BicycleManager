package br.manager.bicycle.projetointegrador.screens;

import java.time.LocalDate;
import br.manager.bicycle.projetointegrador.models.Cliente;
import br.manager.bicycle.projetointegrador.models.Funcionario;
import br.manager.bicycle.projetointegrador.models.ItemServico;
import br.manager.bicycle.projetointegrador.models.Produto;
import br.manager.bicycle.projetointegrador.repositories.RepositorioCliente;
import br.manager.bicycle.projetointegrador.repositories.RepositorioFuncionario;
import br.manager.bicycle.projetointegrador.repositories.RepositorioOrdemServico;
import br.manager.bicycle.projetointegrador.repositories.RepositorioProduto;

import javafx.fxml.FXML;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class RealizaOrdemServico {

    @FXML
    private ComboBox<Cliente> cbCliente;

    @FXML
    private ComboBox<Funcionario> cbFuncionario;

    @FXML
    private ComboBox<Produto> cbProdutos;

    @FXML
    private TextField tfDataHora;

    @FXML
    private DatePicker dpDataEntrega;

    @FXML
    private TextField tfMaoObra;

    @FXML 
    private TextField tfTotalItens;

    @FXML
    private TextField tfGarantia;

    @FXML
    private TextField tfObservacao;

    @FXML
    private TextField tfQuantidade;

    @FXML
    private TableView<ItemServico> tbItensServico;

    @FXML
    private TableColumn<ItemServico,String> tbcProduto;

    @FXML
    private TableColumn<ItemServico,Double> tbcValorUnitario;

    @FXML
    private TableColumn<ItemServico,Integer> tbcQuantidade;

    @FXML
    private TableColumn<ItemServico,Double> tbcSubTotal;

    @FXML
    private Button btAdicionaProduto;

    @FXML
    private Button btIniciaOrdem;

    @FXML
    private Button btFinalizaOrdem;


    private RepositorioCliente repositorioCliente;
    private RepositorioFuncionario repositorioFuncionario;
    private RepositorioProduto repositorioProduto;
    private RepositorioOrdemServico repositorioOrdemServico;

    public RealizaOrdemServico(RepositorioCliente repositorioCliente, RepositorioFuncionario repositorioFuncionario, RepositorioProduto repositorioProduto, RepositorioOrdemServico repositorioOrdemServico) {
        this.repositorioCliente = repositorioCliente;
        this.repositorioFuncionario = repositorioFuncionario;
        this.repositorioProduto = repositorioProduto;
        this.repositorioOrdemServico = repositorioOrdemServico;
    }

    @FXML
    public void initialize() {
        tfDataHora.setDisable(true);
        tfGarantia.setDisable(true);
        tfObservacao.setDisable(true);
        tfQuantidade.setDisable(true);
        tfMaoObra.setDisable(true);
        btAdicionaProduto.setDisable(true);
        btFinalizaOrdem.setDisable(true);
        cbProdutos.setDisable(true);
        dpDataEntrega.setDisable(true);
        tbItensServico.setDisable(true);
        tfTotalItens.setEditable(false);

        tbcProduto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getNome()));
        tbcValorUnitario.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValor()).asObject());
        tbcQuantidade.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantidade()).asObject());
        tbcSubTotal.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSubTotal()).asObject());
        
        try {
            cbCliente.getItems().addAll(repositorioCliente.listarClientes());
            cbFuncionario.getItems().addAll(repositorioFuncionario.listarFuncionarios());
            cbProdutos.getItems().addAll(repositorioProduto.listarProdutos());
        }catch(Exception e) {
            Alert alert = new Alert(AlertType.ERROR,e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void iniciarOrdem() {
        Cliente cliente = cbCliente.getSelectionModel().getSelectedItem();
        Funcionario funcionario = cbFuncionario.getSelectionModel().getSelectedItem();
        
        if(cliente != null && funcionario != null) {
            tfGarantia.setDisable(false);
            tfObservacao.setDisable(false);
            tfQuantidade.setDisable(false);
            tfMaoObra.setDisable(false);
            btAdicionaProduto.setDisable(false);
            btFinalizaOrdem.setDisable(false);
            cbProdutos.setDisable(false);
            cbCliente.setDisable(true);
            cbFuncionario.setDisable(true);
            dpDataEntrega.setDisable(false);
            tbItensServico.setDisable(false);
            repositorioOrdemServico.iniciaOrdemServico(cliente, funcionario);
            tfDataHora.setText(repositorioOrdemServico.getOrdemServico().getDataHora().toString());

        }else {
            Alert alert = new Alert(AlertType.ERROR,"Selecione os campos CLIENTE e TÉCNICO RESPONSÁVEL corretamente!");
            alert.showAndWait();
        }
    }

    @FXML
    private void adicionarItem() {
        String strQuantidade = tfQuantidade.getText();
        Produto produto = cbProdutos.getSelectionModel().getSelectedItem();

        int quantidade = 0;
        boolean existeErro = false;
        String msg = "";

        try {
            quantidade = Integer.parseInt(strQuantidade);
        }catch(NumberFormatException e) {
            existeErro = true;
            msg = "QUANTIDADE inválida!\n";
        }

        if(quantidade <= 0 ) {
            existeErro = true;
            msg = "QUANTIDADE inválida!\n";
        }

        if(produto == null) {
            existeErro = true;
            msg += "Selecione um PRODUTO!\n";
        }

        if(!existeErro) {
            repositorioOrdemServico.adicionaProduto(produto, quantidade);
            atualizaTabela();
            cbProdutos.getSelectionModel().clearSelection();
            tfQuantidade.clear();

            Double total = repositorioOrdemServico.finalizaTotalMaoObra();
            tfTotalItens.setText(""+total);
        }else {
            Alert alert = new Alert(AlertType.ERROR,msg);
            alert.showAndWait();
        }
    }

    private void atualizaTabela() {
        tbItensServico.getItems().clear();
        tbItensServico.getItems().addAll(repositorioOrdemServico.getOrdemServico().getItensServico());
    }

    @FXML
    private void finalizarOrdem() {
        String garantia = tfGarantia.getText();
        LocalDate dataEntrega = dpDataEntrega.getValue();
        String observacao = tfObservacao.getText();
        String sMaoObra = tfMaoObra.getText();
        Double maoObra = 0.0;
        boolean existeErro = false;
        String msg = "";

        if(garantia.isEmpty() || garantia.isBlank()) {
            msg = "GARANTIA não pode ser vazio!";
            existeErro = true;
        }

        if(dataEntrega == null) {
            msg += "\nDATA DE ENTREGA não pode ser vazio!";
            existeErro = true;
        }

        if(observacao.isEmpty() || observacao.isBlank()) {
            msg += "\nOBSERVAÇÃO não pode ser vazio!";
            existeErro = true;
        }

        if(sMaoObra.isEmpty() || sMaoObra.isBlank()) {
            msg += "\nMÃO DE OBRA não pode ser vazio!";
            existeErro = true;
        }else {
            maoObra = Double.valueOf(sMaoObra).doubleValue();
        }
        
        if(!existeErro && repositorioOrdemServico.getOrdemServico().getItensServico().size() > 0) {  
            try {
                repositorioOrdemServico.insereInfoAdicionais(dataEntrega, garantia, observacao, maoObra);
                Double total = repositorioOrdemServico.finalizaTotalMaoObra();
                boolean ret = repositorioOrdemServico.finalizaOrdem();

                if(ret) {
                    msg += "[SUCESSO] - Ordem de Serviço cadastrada com sucesso!\n[TOTAL] - " + total;
                }else {
                    msg += "[ERRO] - Ordem de Serviço não cadastrada!";
                }
                reiniciar();
            }catch(Exception e) {
                e.printStackTrace();
                msg += e.getMessage();        
            }
        }else if (repositorioOrdemServico.getOrdemServico().getItensServico().size() <= 0) {
            msg += "\nAdicione ao menos (UM) produto ou serviço!";
        }
        Alert alert = new Alert(AlertType.INFORMATION,msg);
        alert.showAndWait();
    }

    @FXML
    private void reiniciar() {
        tfTotalItens.clear();
        tfDataHora.clear();
        tfGarantia.clear();
        tfObservacao.clear();
        tfQuantidade.clear();
        tfMaoObra.clear();
        cbProdutos.getSelectionModel().clearSelection();
        cbCliente.getSelectionModel().clearSelection();
        cbFuncionario.getSelectionModel().clearSelection();
        dpDataEntrega.getEditor().clear();
        tbItensServico.getItems().clear();
        tfGarantia.setDisable(true);
        tfObservacao.setDisable(true);
        tfQuantidade.setDisable(true);
        tfMaoObra.setDisable(true);
        btAdicionaProduto.setDisable(true);
        btFinalizaOrdem.setDisable(true);
        cbProdutos.setDisable(true);
        cbCliente.setDisable(false);
        cbFuncionario.setDisable(false);
        dpDataEntrega.setDisable(true);
        tbItensServico.setDisable(true);
    }
}