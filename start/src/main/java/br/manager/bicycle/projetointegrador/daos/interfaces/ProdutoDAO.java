package br.manager.bicycle.projetointegrador.daos.interfaces;

import java.util.ArrayList;
import br.manager.bicycle.projetointegrador.models.Produto;

public interface ProdutoDAO {
    boolean cadastrarProduto(Produto p) throws Exception;
    boolean atualizarProduto(int id, Produto p) throws Exception;
    boolean removerProduto(int id) throws Exception;
    ArrayList<Produto> listarProdutos() throws Exception;
    Produto buscarProduto(int id) throws Exception;
    Produto buscarProdutoItem(int idItem) throws Exception;
}

