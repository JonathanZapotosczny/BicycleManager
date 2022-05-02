package br.manager.bicycle.projetointegrador.screens;

import java.sql.SQLException;

import br.manager.bicycle.projetointegrador.models.Cliente;
import br.manager.bicycle.projetointegrador.repositories.RepositorioCliente;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroCliente {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfTelefone;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLimpar;

    private RepositorioCliente repositorioCliente;
    private Cliente clienteExistente = null;

    public CadastroCliente (RepositorioCliente repositorioCliente) {
        this.repositorioCliente = repositorioCliente;
    }

    public CadastroCliente (Cliente clienteExistente, RepositorioCliente repositorioCliente) {
        this.clienteExistente = clienteExistente;
        this.repositorioCliente = repositorioCliente;
    }

    public void initialize() {
        if(clienteExistente != null) {
            tfNome.setText(clienteExistente.getNome());
            tfEmail.setText(clienteExistente.getEmail());
            tfCpf.setText(clienteExistente.getCpf());
            tfTelefone.setText(clienteExistente.getTelefone());
            btCadastrar.setText("ATUALIZAR");
        }
    }

    @FXML
    private void cadastrar() {
        String nome = tfNome.getText();
        String email = tfEmail.getText();
        String cpf = tfCpf.getText();
        String telefone = tfTelefone.getText();
        boolean existeErro = false;
        String msg = "";

        if (nome.isEmpty() || nome.isBlank()) {
            existeErro = true;
            msg += "NOME não pode ser vazio!\n";
        }

        if (email.isEmpty() || email.isBlank()) {
            existeErro = true;
            msg += "E-MAIL não pode ser vazio!\n";
        }

        if (cpf.isEmpty() || cpf.isBlank()) {
            existeErro = true;
            msg += "CPF não pode ser vazio!\n";
        }

        if (telefone.isEmpty() || telefone.isBlank()) {
            existeErro = true;
            msg += "TELEFONE não pode ser vazio!\n";
        }

        if(!existeErro) {
            try {
                boolean ret;

                if(clienteExistente != null) {
                    ret = repositorioCliente.atualizarCliente(clienteExistente.getIdCliente(), nome, email, cpf, telefone);
                }else {
                    ret = repositorioCliente.cadastrarCliente(nome, email, cpf, telefone);
                }

                if(ret) {
                    msg = "[SUCESSO] - Cliente cadastrado com sucesso!";
                    limpar();
                }else {
                    msg = "[ERRO] - Cliente não cadastrado!";
                }

            }catch(SQLException e) {
                existeErro = true;
                msg = e.getMessage();
            }
        }
        Alert alert = new Alert(existeErro ? AlertType.ERROR : AlertType.INFORMATION, msg);
        alert.showAndWait();
    }

    @FXML
    private void limpar() {
        tfNome.clear();
        tfEmail.clear();
        tfCpf.clear();
        tfTelefone.clear();
    }
}