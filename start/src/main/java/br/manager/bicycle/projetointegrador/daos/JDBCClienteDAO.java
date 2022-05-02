package br.manager.bicycle.projetointegrador.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import br.manager.bicycle.projetointegrador.daos.interfaces.ClienteDAO;
import br.manager.bicycle.projetointegrador.models.Cliente;
import br.manager.bicycle.projetointegrador.utils.FabricaConexoes;

public class JDBCClienteDAO implements ClienteDAO {

    private FabricaConexoes fabricaConexoes;

    public JDBCClienteDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public boolean cadastrarCliente(Cliente c) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_cliente(nome,email,cpf,telefone) VALUES (?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, c.getNome());
        pstmt.setString(2, c.getEmail());
        pstmt.setString(3, c.getCpf());
        pstmt.setString(4, c.getTelefone());
        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();

        rsId.next();

        int id = rsId.getInt(1);

        rsId.close();
        pstmt.close();
        con.close();

        c.setIdCliente(id);

        return true;
    }

    @Override
    public boolean atualizarCliente(int id, Cliente c) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_cliente SET nome=?,email=?,cpf=?,telefone=? WHERE idCliente=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, c.getNome());
        pstmt.setString(2, c.getEmail());
        pstmt.setString(3, c.getCpf());
        pstmt.setString(4, c.getTelefone());
        pstmt.setInt(5, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean removerCliente(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_cliente SET ativo=0 WHERE idCliente=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        return ret == 1;
    }

    private Cliente montarCliente(ResultSet rs) throws Exception {

        int idCliente = rs.getInt("idCliente");
        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String cpf = rs.getString("cpf");
        String telefone = rs.getString("telefone");

        Cliente c = new Cliente(idCliente, nome, email, cpf, telefone);

        return c;
    }

    @Override
    public ArrayList<Cliente> listarClientes() throws Exception {
        ArrayList<Cliente> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "call relatorio_cliente()";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            Cliente c = montarCliente(rs);
            lista.add(c);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Cliente buscarCliente(int id) throws Exception {
        Cliente c = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_cliente WHERE idCliente=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1,id);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            c = montarCliente(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return c;
    }

    @Override
    public Cliente buscarClienteOrdem(int idOrdemServico) throws Exception {

        Connection con = fabricaConexoes.getConnection();

        String sqlIdCliente = "SELECT idCliente FROM projeto_ordem_servico WHERE idOrdemServico=?";

        PreparedStatement pstmt = con.prepareStatement(sqlIdCliente);

        pstmt.setInt(1, idOrdemServico);

        ResultSet rsIdCliente = pstmt.executeQuery();

        rsIdCliente.next();

        int idCliente = rsIdCliente.getInt(1);

        rsIdCliente.close();
        pstmt.close();
        con.close();

        return buscarCliente(idCliente);
    }
}