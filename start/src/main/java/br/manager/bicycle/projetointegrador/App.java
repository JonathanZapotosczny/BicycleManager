package br.manager.bicycle.projetointegrador;

import br.manager.bicycle.projetointegrador.daos.JDBCAutenticacaoDAO;
import br.manager.bicycle.projetointegrador.daos.JDBCClienteDAO;
import br.manager.bicycle.projetointegrador.daos.JDBCFuncionarioDAO;
import br.manager.bicycle.projetointegrador.daos.JDBCOrdemServicoDAO;
import br.manager.bicycle.projetointegrador.daos.JDBCProdutoDAO;
import br.manager.bicycle.projetointegrador.daos.interfaces.AutenticacaoDAO;
import br.manager.bicycle.projetointegrador.daos.interfaces.ClienteDAO;
import br.manager.bicycle.projetointegrador.daos.interfaces.FuncionarioDAO;
import br.manager.bicycle.projetointegrador.daos.interfaces.OrdemServicoDAO;
import br.manager.bicycle.projetointegrador.daos.interfaces.ProdutoDAO;
import br.manager.bicycle.projetointegrador.repositories.RepositorioCliente;
import br.manager.bicycle.projetointegrador.repositories.RepositorioFuncionario;
import br.manager.bicycle.projetointegrador.repositories.RepositorioOrdemServico;
import br.manager.bicycle.projetointegrador.repositories.RepositorioProduto;
import br.manager.bicycle.projetointegrador.screens.Home;
import br.manager.bicycle.projetointegrador.services.AutenticacaoService;
import br.manager.bicycle.projetointegrador.utils.FabricaConexoes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;

public class App extends Application {

    FabricaConexoes fabricaConexoes = FabricaConexoes.getInstance();

    ClienteDAO clienteDAO = new JDBCClienteDAO(fabricaConexoes);
    FuncionarioDAO funcionarioDAO = new JDBCFuncionarioDAO(fabricaConexoes);
    ProdutoDAO produtoDAO = new JDBCProdutoDAO(fabricaConexoes);
    OrdemServicoDAO ordemServicoDAO = new JDBCOrdemServicoDAO(fabricaConexoes);
    AutenticacaoDAO autenticacaoDAO = new JDBCAutenticacaoDAO(fabricaConexoes);

    AutenticacaoService autenticacaoService = new AutenticacaoService(autenticacaoDAO);
    RepositorioCliente repositorioCliente = new RepositorioCliente(clienteDAO);
    RepositorioFuncionario repositorioFuncionario = new RepositorioFuncionario(funcionarioDAO);
    RepositorioProduto repositorioProduto = new RepositorioProduto(produtoDAO);
    RepositorioOrdemServico repositorioOrdemServico = new RepositorioOrdemServico(ordemServicoDAO, clienteDAO, funcionarioDAO, produtoDAO);

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(loadTela("fxml/home.fxml", o->new Home(repositorioCliente, repositorioFuncionario, repositorioProduto, repositorioOrdemServico, autenticacaoService)), 640, 480);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setTitle("Bicycle Maneger");
        stage.getIcons().add(new Image(App.class.getResourceAsStream("img/bicycle.png")));
        stage.show();
    }
    
    public static Parent loadTela(String fxml, Callback controller) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(fxml));
            loader.setControllerFactory(controller);
            root = loader.load();
        }catch (Exception e) {
            System.out.println("Problema no arquivo fxml. Está correto? "+fxml);
            e.printStackTrace();
            System.exit(0);
        }
        return root;   
    }

    public static void main(String[] args) {
        launch();
    }
}