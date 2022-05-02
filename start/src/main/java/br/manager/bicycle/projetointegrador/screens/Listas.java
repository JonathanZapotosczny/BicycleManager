package br.manager.bicycle.projetointegrador.screens;

import br.manager.bicycle.projetointegrador.App;
import br.manager.bicycle.projetointegrador.models.Cliente;
import br.manager.bicycle.projetointegrador.models.Funcionario;
import br.manager.bicycle.projetointegrador.models.Produto;
import br.manager.bicycle.projetointegrador.repositories.RepositorioCliente;
import br.manager.bicycle.projetointegrador.repositories.RepositorioFuncionario;
import br.manager.bicycle.projetointegrador.repositories.RepositorioProduto;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

public class Listas {

    @FXML
    private ListView<Cliente> listaCliente;

    @FXML
    private ListView<Funcionario> listaFuncionario;

    @FXML
    private ListView<Produto> listaProduto;

    @FXML
    private ProgressIndicator piProcessando;

    @FXML
    private Button btExcluir;

    @FXML
    private FlowPane rootPane;

    private RepositorioCliente repositorioCliente;
    private RepositorioFuncionario repositorioFuncionario;
    private RepositorioProduto repositorioProduto;
    
    public Listas (RepositorioCliente repositorioCliente, RepositorioFuncionario repositorioFuncionario, RepositorioProduto repositorioProduto) {
        this.repositorioCliente = repositorioCliente;
        this.repositorioFuncionario = repositorioFuncionario;
        this.repositorioProduto = repositorioProduto;
    }

    public void initialize() {
        Thread listas = new Thread(()-> {
        try {
                listaCliente.getItems().addAll(repositorioCliente.listarClientes());
                listaFuncionario.getItems().addAll(repositorioFuncionario.listarFuncionarios());
                listaProduto.getItems().addAll(repositorioProduto.listarProdutos());
                Platform.runLater(()-> {
                    piProcessando.setVisible(false);
                });
        }catch(Exception e) {
            Alert alert = new Alert(AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    });
        listas.setDaemon(true);
        listas.start();
    }

    @FXML
    private void remover(ActionEvent event) {
        Cliente clienteSelecionado = listaCliente.getSelectionModel().getSelectedItem();
        Funcionario funcionarioSelecionado = listaFuncionario.getSelectionModel().getSelectedItem();
        Produto produtoSelecionado = listaProduto.getSelectionModel().getSelectedItem();
        
            if(clienteSelecionado != null) {
                try {
                    repositorioCliente.removerCliente(clienteSelecionado.getIdCliente());
                    listaCliente.getItems().clear();
                    listaCliente.getItems().addAll(repositorioCliente.listarClientes());
                }catch (Exception e) {
                    Alert alert = new Alert(AlertType.ERROR,e.getMessage());
                    alert.showAndWait();
                }
            }

            else if(funcionarioSelecionado != null) {
                try {
                    repositorioFuncionario.removerFuncionario(funcionarioSelecionado.getIdFuncionario());
                    listaFuncionario.getItems().clear();
                    listaFuncionario.getItems().addAll(repositorioFuncionario.listarFuncionarios());
                }catch (Exception e) {
                    Alert alert = new Alert(AlertType.ERROR,e.getMessage());
                    alert.showAndWait();
                }
            } 
            else if(produtoSelecionado != null) {
                try {
                    repositorioProduto.removerProduto(produtoSelecionado.getIdProduto());
                    listaProduto.getItems().clear();
                    listaProduto.getItems().addAll(repositorioProduto.listarProdutos());
                }catch (Exception e) {
                    Alert alert = new Alert(AlertType.ERROR,e.getMessage());
                    alert.showAndWait();
                }
            }
    }

    @FXML
    private void atualizar(ActionEvent event) {
        Cliente clienteSelecionado = listaCliente.getSelectionModel().getSelectedItem();
        Funcionario funcionarioSelecionado = listaFuncionario.getSelectionModel().getSelectedItem();
        Produto produtoSelecionado = listaProduto.getSelectionModel().getSelectedItem();
        
            if(clienteSelecionado != null) {
                StackPane painelCentral = (StackPane) rootPane.getParent();
                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/cadastro_cliente.fxml", (o)->new CadastroCliente(clienteSelecionado, repositorioCliente)));
            } 
            else if(funcionarioSelecionado != null) {
                StackPane painelCentral = (StackPane) rootPane.getParent();
                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/cadastro_funcionario.fxml", (o)->new CadastroFuncionario(funcionarioSelecionado, repositorioFuncionario)));
            } 
            else if(produtoSelecionado != null) {
                StackPane painelCentral = (StackPane) rootPane.getParent();
                painelCentral.getChildren().clear();
                painelCentral.getChildren().add(App.loadTela("fxml/cadastro_produto.fxml", (o)->new CadastroProduto(produtoSelecionado, repositorioProduto)));
            }
    }
}