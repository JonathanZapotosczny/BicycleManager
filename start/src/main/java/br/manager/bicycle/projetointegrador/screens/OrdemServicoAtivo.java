package br.manager.bicycle.projetointegrador.screens;

import java.time.format.DateTimeFormatter;

import br.manager.bicycle.projetointegrador.models.ItemServico;
import br.manager.bicycle.projetointegrador.models.OrdemServico;
import br.manager.bicycle.projetointegrador.repositories.RepositorioCliente;
import br.manager.bicycle.projetointegrador.repositories.RepositorioFuncionario;
import br.manager.bicycle.projetointegrador.repositories.RepositorioOrdemServico;
import br.manager.bicycle.projetointegrador.repositories.RepositorioProduto;

import javafx.fxml.FXML;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class OrdemServicoAtivo {

    @FXML
    private TextArea taInfo;

    @FXML
    private TableView<OrdemServico> tbOrdemServico;

    @FXML
    private TableColumn<OrdemServico,String> tbcDataOrdem;

    @FXML
    private TableColumn<OrdemServico,String> tbcCliente;

    @FXML
    private TableColumn<OrdemServico,Double> tbcTotalOrdem;

    @FXML
    private TableView<ItemServico> tbItensServico;

    @FXML
    private TableColumn<ItemServico,String> tbcProduto;

    @FXML
    private TableColumn<ItemServico,Double> tbcValorUnitario;

    @FXML
    private TableColumn<ItemServico,Integer> tbcQuantidade;

    @FXML
    private ProgressIndicator piProcessandoOrdem;

    private RepositorioOrdemServico repositorioOrdemServico;
    private RepositorioProduto repositorioProdutos;
    private RepositorioCliente repositorioCliente;
    private RepositorioFuncionario repositorioFuncionario;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public OrdemServicoAtivo(RepositorioOrdemServico repositorioOrdemServico, RepositorioProduto repositorioProdutos, RepositorioCliente repositorioCliente, RepositorioFuncionario repositorioFuncionario) {
        this.repositorioOrdemServico = repositorioOrdemServico;
        this.repositorioProdutos = repositorioProdutos;
        this.repositorioCliente = repositorioCliente;
        this.repositorioFuncionario = repositorioFuncionario;
    }

    public void initialize() {

        taInfo.setEditable(false);

        tbcDataOrdem.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDataHora().format(formatter)));
        tbcCliente.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCliente().getNome()));
        tbcTotalOrdem.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValorTotal()).asObject());

        tbcProduto.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduto().getNome()));
        tbcQuantidade.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQuantidade()).asObject());
        tbcValorUnitario.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getValor()).asObject());
        
        Thread ordemServicoAtivo = new Thread(()-> {
        try {
            tbOrdemServico.getItems().addAll(repositorioOrdemServico.listar());
            Platform.runLater(()->{
                piProcessandoOrdem.setVisible(false);
            });
        }catch(Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }});

        ordemServicoAtivo.setDaemon(true);
        ordemServicoAtivo.start();
    }

    @FXML
    private void atualizarRemoverProdutos(MouseEvent event) {
        OrdemServico ordemServicoSelecionada = tbOrdemServico.getSelectionModel().getSelectedItem();

        if(event.getClickCount() == 1) {
            if(ordemServicoSelecionada != null) {
                try {
                    tbItensServico.getItems().clear();
                    tbItensServico.getItems().addAll(ordemServicoSelecionada.getItensServico());
                    taInfo.setText("Data l Hora do Pedido: "+ordemServicoSelecionada.getDataHora().format(formatterDate)+"\nData da Entrega: "+ordemServicoSelecionada.getDataEntrega().format(formatter)+"\nTécnico Responsável: "+ordemServicoSelecionada.getTecnicoResponsavel().getNome()+"\nGarantia: "+ordemServicoSelecionada.getGarantia()+"\nMão de Obra: "+ordemServicoSelecionada.getMaoObra()+"\nObservações: "+ordemServicoSelecionada.getObservacao());
                }catch(Exception e) {
                    Alert alert = new Alert(AlertType.ERROR,e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    private void finalizarOrdem(ActionEvent event) {
        OrdemServico ordemServicoSelecionada = tbOrdemServico.getSelectionModel().getSelectedItem();

        if(ordemServicoSelecionada != null) {
            try {
                repositorioOrdemServico.removerOrdemServico(ordemServicoSelecionada.getIdOrdemServico());
                tbOrdemServico.getItems().clear();
                tbOrdemServico.getItems().addAll(repositorioOrdemServico.listar());
                tbItensServico.getItems().clear();
                taInfo.clear();
            }catch(Exception e) {
                Alert alert = new Alert(AlertType.ERROR,e.getMessage());
                alert.showAndWait();
            }
        }
    } 
}