package br.manager.bicycle.projetointegrador.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import br.manager.bicycle.projetointegrador.daos.interfaces.ProdutoDAO;
import br.manager.bicycle.projetointegrador.models.Produto;

public class RepositorioProduto {
    
    private ArrayList<Produto> produtos;
    private ProdutoDAO produtoDAO;

    public RepositorioProduto(ProdutoDAO produtoDAO) {
        this.produtoDAO = produtoDAO;
        produtos = new ArrayList<>();
    }

    public boolean cadastrarProduto(String nome, String descricao, Double valor) throws SQLException {
        Produto p = new Produto(nome, descricao, valor);

        try {
            produtoDAO.cadastrarProduto(p);
            this.produtos.add(p);
            return true;
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean atualizarProduto(int id, String nome, String descricao, Double valor) throws SQLException {
        Produto produto = new Produto(nome, descricao, valor);

        try {
            return produtoDAO.atualizarProduto(id, produto);
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerProduto(int id) throws SQLException {
        try {
            return produtoDAO.removerProduto(id);
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Produto> listarProdutos() throws Exception {
        return produtoDAO.listarProdutos();
    }
}