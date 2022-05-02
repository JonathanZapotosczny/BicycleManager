package br.manager.bicycle.projetointegrador.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import br.manager.bicycle.projetointegrador.daos.interfaces.ProdutoDAO;
import br.manager.bicycle.projetointegrador.models.Produto;
import br.manager.bicycle.projetointegrador.utils.FabricaConexoes;

public class JDBCProdutoDAO implements ProdutoDAO {

    private FabricaConexoes fabricaConexoes;

    public JDBCProdutoDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public boolean cadastrarProduto(Produto p) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_produtos(nome,descricao,valor) VALUES (?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, p.getNome());
        pstmt.setString(2, p.getDescricao());
        pstmt.setDouble(3, p.getValor());
        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();

        rsId.next();

        int id = rsId.getInt(1);

        rsId.close();
        pstmt.close();
        con.close();

        p.setIdProduto(id);

        return true;
    }

    @Override
    public boolean atualizarProduto(int id, Produto p) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_produtos SET nome=?,descricao=?,valor=? WHERE idProduto=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, p.getNome());
        pstmt.setString(2, p.getDescricao());
        pstmt.setDouble(3, p.getValor());
        pstmt.setInt(4, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean removerProduto(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_produtos SET ativo=0 WHERE idProduto=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        return ret == 1;
    }

    private Produto montarProduto(ResultSet rs) throws Exception {
        int idProduto = rs.getInt("idProduto");
        String nome = rs.getString("nome");
        String descricao = rs.getString("descricao");
        Double valor = rs.getDouble("valor");

        Produto p = new Produto (idProduto, nome, descricao, valor);

        return p;
    }

    @Override
    public ArrayList<Produto> listarProdutos() throws Exception {
        ArrayList<Produto> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();
        
        String sql = "call relatorio_produto()";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            Produto p = montarProduto(rs);

            lista.add(p);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Produto buscarProduto(int id) throws Exception {
        Produto p = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_produtos WHERE idProduto=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        ResultSet rs = pstmt.executeQuery();
        
        while(rs.next()){
            p = montarProduto(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return p;
    }

    @Override
    public Produto buscarProdutoItem(int idItem) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sqlIdProduto = "SELECT idProduto FROM projeto_itens WHERE idItens=?";

        PreparedStatement pstmt = con.prepareStatement(sqlIdProduto);

        pstmt.setInt(1, idItem);

        ResultSet rsIdProduto = pstmt.executeQuery();

        rsIdProduto.next();

        int idProduto = rsIdProduto.getInt(1);

        rsIdProduto.close();
        pstmt.close();
        con.close();

        return buscarProduto(idProduto);
    }
}