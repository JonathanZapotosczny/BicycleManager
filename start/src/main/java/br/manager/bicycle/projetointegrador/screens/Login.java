package br.manager.bicycle.projetointegrador.screens;

import java.sql.SQLException;

import br.manager.bicycle.projetointegrador.services.AutenticacaoService;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.FlowPane;

public class Login {

    @FXML
    private TextField tfUsuario;

    @FXML
    private PasswordField pfSenha;

    @FXML
    private Button btLogar;

    @FXML
    private Button btCadastrar;

    @FXML
    private FlowPane rootPane;

    private AutenticacaoService autenticacaoService;
    private Home homeControler;

    public Login(AutenticacaoService autenticacaoService, Home homeControler) {
        this.autenticacaoService = autenticacaoService;
        this.homeControler = homeControler;
    }

    @FXML
    public void logar() throws Exception {
        String usuario = tfUsuario.getText();
        String senha = pfSenha.getText();
        boolean existeErro = false;
        String msg = "";

        if(usuario.isEmpty() || usuario.isBlank()) {
            existeErro = true;
            msg += "USUÁRIO não pode ser vazio!\n";
        }
        if(senha.isEmpty() || senha.isBlank()) {
            existeErro = true;
            msg += "SENHA não pode ser vazio!\n";
        }

        if(!existeErro) {
            try {
                autenticacaoService.logar(usuario, senha);
                if(autenticacaoService.estaLogado()) {
                    msg = "[SUCESSO] - Usuário logado com sucesso!";
                    homeControler.atualizaTela();
                }else {
                    msg = "[ERRO] - Usuário ou senha incorreto(s)!";
                }
            }catch(Exception e) {
                msg += e.getMessage();
            }
        }
        Alert alert = new Alert(AlertType.INFORMATION, msg);
        alert.showAndWait();
    }

    @FXML
    public void cadastrar() throws Exception {
        String usuario = tfUsuario.getText();
        String senha = pfSenha.getText();
        boolean existeErro = false;
        String msg = "";

        if (usuario.isEmpty() || usuario.isBlank()) {
            existeErro = true;
            msg += "USUÁRIO não pode ser vazio!\n";
        }

        if (senha.isEmpty() || senha.isBlank()) {
            existeErro = true;
            msg += "SENHA não pode ser vazio!\n";
        }

        if(!existeErro) {
            try {
                boolean ret;
                ret = autenticacaoService.cadastrar(usuario, senha);
                if(ret) {
                    msg = "[SUCESSO] - Usuário cadastrado com sucesso!";
                    limpar();
                }else {
                    msg = "[ERRO] - Usuário não cadastrado!";
                }
            }catch(SQLException e) {
                msg = e.getMessage();
            }
        }
        Alert alert = new Alert(AlertType.INFORMATION, msg);
        alert.showAndWait();
    }

    @FXML
    public void carregaTelaCadastro() {
        homeControler.carregaTela("cadastro");
    }

    @FXML
    public void carregaTelaLogin() {
        homeControler.atualizaTela();
    }

    @FXML
    private void limpar() {
        tfUsuario.clear();
        pfSenha.clear();
    }
}