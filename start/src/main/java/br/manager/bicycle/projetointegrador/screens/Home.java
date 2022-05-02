package br.manager.bicycle.projetointegrador.screens;

import br.manager.bicycle.projetointegrador.App;
import br.manager.bicycle.projetointegrador.repositories.RepositorioCliente;
import br.manager.bicycle.projetointegrador.repositories.RepositorioFuncionario;
import br.manager.bicycle.projetointegrador.repositories.RepositorioOrdemServico;
import br.manager.bicycle.projetointegrador.repositories.RepositorioProduto;
import br.manager.bicycle.projetointegrador.services.AutenticacaoService;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class Home {

    private RepositorioCliente repositorioCliente;
    private RepositorioFuncionario repositorioFuncionario;
    private RepositorioProduto repositorioProduto;
    private RepositorioOrdemServico repositorioOrdemServico;
    private AutenticacaoService autenticacaoService;

    @FXML
    private BorderPane root;
    
    @FXML
    private StackPane painelCentral;

    public Home(RepositorioCliente repositorioCliente, RepositorioFuncionario repositorioFuncionario, RepositorioProduto repositorioProduto, RepositorioOrdemServico repositorioOrdemServico, AutenticacaoService autenticacaoService) {
        this.repositorioCliente = repositorioCliente;
        this.repositorioFuncionario = repositorioFuncionario;
        this.repositorioProduto = repositorioProduto;
        this.repositorioOrdemServico = repositorioOrdemServico;
        this.autenticacaoService = autenticacaoService;
    }

    public void initialize() {
        atualizaTela();
    }

    @FXML
    private void loadCadastroCliente() {
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/cadastro_cliente.fxml", (o)->new CadastroCliente(repositorioCliente)));
    }

    @FXML
    private void loadCadastroFuncionario() {
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/cadastro_funcionario.fxml", (o)->new CadastroFuncionario(repositorioFuncionario)));
    }

    @FXML
    private void loadCadastroProduto() {
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/cadastro_produto.fxml", (o)->new CadastroProduto(repositorioProduto)));
    }
    
    @FXML
    private void loadCadastroOrdem() {
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/cadastro_ordem.fxml", (o)->new RealizaOrdemServico(repositorioCliente,repositorioFuncionario, repositorioProduto, repositorioOrdemServico)));
    }

    @FXML
    private void loadListas() {
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/listas.fxml", (o)->new Listas(repositorioCliente,repositorioFuncionario, repositorioProduto)));
    }

    @FXML
    private void loadOrdemServicoAtivo() {
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/ordem_servico_ativo.fxml", (o)->new OrdemServicoAtivo(repositorioOrdemServico, repositorioProduto, repositorioCliente, repositorioFuncionario)));
    }

    @FXML
    private void loadHome() {
        painelCentral.getChildren().clear();
        painelCentral.getChildren().add(App.loadTela("fxml/principal.fxml", null));
    }

    @FXML
    public void logout() {
        autenticacaoService.logout();
        atualizaTela();
    }

    @FXML
    public void atualizaTela() {
        if(!autenticacaoService.estaLogado()) {
            root.getLeft().setVisible(false);
            painelCentral.getChildren().clear();
            painelCentral.getChildren().add(App.loadTela("fxml/login.fxml", a->new Login(autenticacaoService,this)));
        }else {
            painelCentral.getChildren().clear();
            root.getLeft().setVisible(true);
            painelCentral.getChildren().add(App.loadTela("fxml/principal.fxml", null));
        }
    }

    @FXML
    public void carregaTela(String tela) {
        if(tela.equals("cadastro")) {
            painelCentral.getChildren().clear();
            painelCentral.getChildren().add(App.loadTela("fxml/cadastro.fxml", a->new Login(autenticacaoService, this)));
        }
    }
}