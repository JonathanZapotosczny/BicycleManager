package br.manager.bicycle.projetointegrador.screens;

import java.sql.SQLException;
import java.time.LocalDate;

import br.manager.bicycle.projetointegrador.models.Funcionario;
import br.manager.bicycle.projetointegrador.repositories.RepositorioFuncionario;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroFuncionario {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfCpf;

    @FXML
    private TextField tfTelefone;

    @FXML
    private DatePicker datePickerDataAdmissao;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLimpar;

    private RepositorioFuncionario repositorioFuncionario;
    private Funcionario funcionarioExistente = null;

    public CadastroFuncionario (RepositorioFuncionario repositorioFuncionario) {
        this.repositorioFuncionario = repositorioFuncionario;
    }

    public CadastroFuncionario (Funcionario funcionarioExistente, RepositorioFuncionario repositorioFuncionario) {
        this.funcionarioExistente = funcionarioExistente;
        this.repositorioFuncionario = repositorioFuncionario;
    }

    public void initialize() {

        if(funcionarioExistente != null) {
            tfNome.setText(funcionarioExistente.getNome());
            tfEmail.setText(funcionarioExistente.getEmail());
            tfCpf.setText(funcionarioExistente.getCpf());
            tfTelefone.setText(funcionarioExistente.getTelefone());
            datePickerDataAdmissao.setValue(funcionarioExistente.getDataAdmissao());
            btCadastrar.setText("ATUALIZAR");
        }
    }

    @FXML
    private void cadastrar() {
        String nome = tfNome.getText();
        String email = tfEmail.getText();
        String cpf = tfCpf.getText();
        String telefone = tfTelefone.getText();
        LocalDate dataAdmissao = datePickerDataAdmissao.getValue();
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
        
        if (dataAdmissao == null) {
            existeErro = true;
            msg += "\nData não pode ser vazio!";
        }

        if(!existeErro) {
            try {
                boolean ret;

                if(funcionarioExistente != null) {
                    ret = repositorioFuncionario.atualizarFuncionario(funcionarioExistente.getIdFuncionario(), nome, email, cpf, telefone, dataAdmissao);
                }else {
                    ret = repositorioFuncionario.cadastrarFuncionario(nome, email, cpf, telefone, dataAdmissao);
                }

                if(ret) {
                    msg = "[SUCESSO] - Funcionário cadastrado com sucesso!";
                    limpar();
                }else {
                    msg = "[ERRO] - Funcionário não cadastrado!";
                }
            }catch (SQLException e) {
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
        datePickerDataAdmissao.setValue(null);
    }
}