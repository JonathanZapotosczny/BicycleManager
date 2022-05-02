package br.manager.bicycle.projetointegrador.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import br.manager.bicycle.projetointegrador.daos.interfaces.FuncionarioDAO;
import br.manager.bicycle.projetointegrador.models.Funcionario;
import br.manager.bicycle.projetointegrador.utils.FabricaConexoes;

public class JDBCFuncionarioDAO implements FuncionarioDAO {
    
    private FabricaConexoes fabricaConexoes;

    public JDBCFuncionarioDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public boolean cadastrarFuncionario(Funcionario f) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_funcionario(nome,email,cpf,telefone,dataAdmissao) VALUES (?,?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, f.getNome());
        pstmt.setString(2, f.getEmail());
        pstmt.setString(3, f.getCpf());
        pstmt.setString(4, f.getTelefone());
        pstmt.setDate(5, java.sql.Date.valueOf(f.getDataAdmissao()));
        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();

        rsId.next();

        int id = rsId.getInt(1);

        rsId.close();
        pstmt.close();
        con.close();

        f.setIdFuncionario(id);

        return true;
    }

    @Override
    public boolean atualizarFuncionario(int id, Funcionario f) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_funcionario SET nome=?,email=?,cpf=?,telefone=?,dataAdmissao=? WHERE idFuncionario=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, f.getNome());
        pstmt.setString(2, f.getEmail());
        pstmt.setString(3, f.getCpf());
        pstmt.setString(4, f.getTelefone());
        pstmt.setString(5, f.getDataAdmissao()+"");
        pstmt.setInt(6, id);

        int ret = pstmt.executeUpdate();

        pstmt.close();
        con.close();

        return ret == 1;
    }

    @Override
    public boolean removerFuncionario(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_funcionario SET ativo=0 WHERE idFuncionario=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        return ret == 1;
    }

    private Funcionario montarFuncionario(ResultSet rs) throws Exception {
        int idFuncionario = rs.getInt("idFuncionario");

        String nome = rs.getString("nome");
        String email = rs.getString("email");
        String cpf = rs.getString("cpf");
        String telefone = rs.getString("telefone");
        LocalDate dataAdmissao = rs.getObject("dataAdmissao", LocalDate.class);

        Funcionario f = new Funcionario(idFuncionario, nome, email, cpf, telefone, dataAdmissao);

        return f;
    }

    @Override
    public ArrayList<Funcionario> listarFuncionarios() throws Exception {
        ArrayList<Funcionario> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "call relatorio_funcionario()";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            Funcionario f = montarFuncionario(rs);
            lista.add(f);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public Funcionario buscarFuncionario(int id) throws Exception {
        Funcionario f = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_funcionario WHERE idFuncionario=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1,id);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()){
            f = montarFuncionario(rs);
        }

        rs.close();
        pstmt.close();
        con.close();

        return f;
    }

    @Override
    public Funcionario buscarFuncionarioOrdem(int idOrdemServico) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sqlIdFuncionario = "SELECT idTecnicoResponsavel FROM projeto_ordem_servico WHERE idOrdemServico=?";

        PreparedStatement pstmt = con.prepareStatement(sqlIdFuncionario);

        pstmt.setInt(1, idOrdemServico);

        ResultSet rsIdFuncionario = pstmt.executeQuery();

        rsIdFuncionario.next();

        int idFuncionario = rsIdFuncionario.getInt(1);
        
        rsIdFuncionario.close();
        pstmt.close();
        con.close();

        return buscarFuncionario(idFuncionario);
    }
}