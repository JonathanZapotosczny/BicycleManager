package br.manager.bicycle.projetointegrador.screens;

import br.manager.bicycle.projetointegrador.models.Produto;
import br.manager.bicycle.projetointegrador.repositories.RepositorioProduto;

import javafx.fxml.FXML;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class CadastroProduto {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfDescricao;

    @FXML
    private TextField tfValor;

    @FXML
    private Button btCadastrar;

    @FXML
    private Button btLimpar;

    private RepositorioProduto repositorioProduto;
    private Produto produtoExistente = null;

    public CadastroProduto (RepositorioProduto repositorioProduto) {
        this.repositorioProduto = repositorioProduto;
    }

    public CadastroProduto (Produto produtoExistente, RepositorioProduto repositorioProduto) {
        this.produtoExistente = produtoExistente;
        this.repositorioProduto = repositorioProduto;
    }

    public void initialize() {

        if(produtoExistente != null) {
            tfNome.setText(produtoExistente.getNome());
            tfDescricao.setText(produtoExistente.getDescricao());
            tfValor.setText(""+produtoExistente.getValor());

            btCadastrar.setText("ATUALIZAR");
        }
    }
    
    @FXML
    private void cadastrar() {

        String nome = tfNome.getText();
        String descricao = tfDescricao.getText();
        String valor = tfValor.getText();
        Double valorDouble = 0.0;
        boolean existeErro = false;
        String msg = "";

        if (nome.isEmpty() || nome.isBlank()) {
            existeErro = true;
            msg += "NOME não pode ser vazio!\n";
        }

        if (descricao.isEmpty() || descricao.isBlank()) {
            existeErro = true;
            msg += "DESCRIÇÃO não pode ser vazio!\n";
        }

        if (valor.isEmpty() || valor.isBlank()) {
            existeErro = true;
            msg += "VALOR não pode ser vazio!\n";
        }

        try {
            valorDouble = Double.parseDouble(valor);
        }catch(NumberFormatException e) {
            existeErro = true;
            msg += "Valor inválido!";
        }

        if(!existeErro) {

            try {
                boolean ret;

                if(produtoExistente != null) {
                    ret = repositorioProduto.atualizarProduto(produtoExistente.getIdProduto(), nome, descricao, valorDouble);
                }else {
                    ret = repositorioProduto.cadastrarProduto(nome, descricao, valorDouble);
                }

                if(ret) {
                    msg = "[SUCESSO] - Produto cadastrado com sucesso!";
                    limpar();
                }else {
                    msg = "[ERRO] - Produto não cadastrado!";
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
        tfDescricao.clear();
        tfValor.clear();
    }
}