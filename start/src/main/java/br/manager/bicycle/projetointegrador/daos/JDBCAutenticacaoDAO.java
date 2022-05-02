package br.manager.bicycle.projetointegrador.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.manager.bicycle.projetointegrador.daos.interfaces.AutenticacaoDAO;
import br.manager.bicycle.projetointegrador.models.Usuario;
import br.manager.bicycle.projetointegrador.utils.FabricaConexoes;

public class JDBCAutenticacaoDAO implements AutenticacaoDAO {

    private FabricaConexoes fabricaConexoes;

    public JDBCAutenticacaoDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    @Override
    public Usuario login(String login, String senha) throws Exception {
        Usuario u = null;

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * from projeto_usuario WHERE login=? and senha=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, login);
        pstmt.setString(2, senha);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()) {
            int idUsuario = rs.getInt("idUsuario");
            String loginUsuario = rs.getString("login");
            String senhaUsuario = rs.getString("senha");
            u = new Usuario(idUsuario, loginUsuario, senhaUsuario);
        }

        rs.close();
        pstmt.close();
        con.close();

        return u;
    }

    @Override
    public boolean cadastrar(Usuario u) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_usuario (login, senha) VALUES (?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pstmt.setString(1, u.getLogin());
        pstmt.setString(2, u.getSenha());
        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();

        rsId.next();

        int id = rsId.getInt(1);

        rsId.close();
        pstmt.close();
        con.close();

        u.setIdUsuario(id);

        return true;
    }
}