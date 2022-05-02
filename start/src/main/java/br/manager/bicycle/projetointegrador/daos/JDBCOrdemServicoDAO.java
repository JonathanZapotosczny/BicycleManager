package br.manager.bicycle.projetointegrador.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import br.manager.bicycle.projetointegrador.daos.interfaces.OrdemServicoDAO;
import br.manager.bicycle.projetointegrador.models.ItemServico;
import br.manager.bicycle.projetointegrador.models.OrdemServico;
import br.manager.bicycle.projetointegrador.utils.FabricaConexoes;


public class JDBCOrdemServicoDAO implements OrdemServicoDAO {

    private FabricaConexoes fabricaConexoes;

    public JDBCOrdemServicoDAO(FabricaConexoes fabricaConexoes) {
        this.fabricaConexoes = fabricaConexoes;
    }

    private void salvarItens(OrdemServico ordemServico) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_itens(idProduto,idOrdemServico,quantidade,valorUnitario) VALUES (?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql);

        for(ItemServico item:ordemServico.getItensServico()) {
            pstmt.setInt(1, item.getProduto().getIdProduto());
            pstmt.setInt(2, ordemServico.getIdOrdemServico());
            pstmt.setInt(3,item.getQuantidade());
            pstmt.setDouble(4,item.getValor());
            pstmt.execute();
        }

        pstmt.close();
        con.close();
    }

    private ArrayList<ItemServico> carregarItensOrdem(int IdOrdemServico) throws Exception {
        ArrayList<ItemServico> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_itens WHERE idOrdemServico=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, IdOrdemServico);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()) {
            int idItens = rs.getInt("idItens");
            int quantidade = rs.getInt("quantidade");
            double valorUnitario = rs.getDouble("valorUnitario");

            ItemServico item = new ItemServico(idItens,null,quantidade,valorUnitario);

            lista.add(item);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public boolean salvarOrdemServico(OrdemServico ordemServico) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "INSERT INTO projeto_ordem_servico(idCliente,idTecnicoResponsavel,dataHora,dataEntrega,garantia,observacao,valorTotal,maoObra) VALUES (?,?,?,?,?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

        pstmt.setInt(1, ordemServico.getCliente().getIdCliente());
        pstmt.setInt(2, ordemServico.getTecnicoResponsavel().getIdFuncionario());
        pstmt.setTimestamp(3, Timestamp.valueOf(ordemServico.getDataHora()));
        pstmt.setString(4, ordemServico.getDataEntrega()+"");
        pstmt.setString(5, ordemServico.getGarantia());
        pstmt.setString(6, ordemServico.getObservacao());
        pstmt.setDouble(7, ordemServico.calcularTotal());
        pstmt.setDouble(8, ordemServico.getMaoObra());
        pstmt.execute();

        ResultSet rsId = pstmt.getGeneratedKeys();

        rsId.next();

        int id = rsId.getInt(1);

        ordemServico.setIdOrdemServico(id);

        rsId.close();
        pstmt.close();
        con.close();

        salvarItens(ordemServico);

        return true;
    }

    @Override
    public ArrayList<OrdemServico> listarOrdensServico() throws Exception {
        ArrayList<OrdemServico> lista = new ArrayList<>();

        Connection con = fabricaConexoes.getConnection();

        String sql = "SELECT * FROM projeto_ordem_servico WHERE ativo = 1";

        PreparedStatement pstmt = con.prepareStatement(sql);

        ResultSet rs = pstmt.executeQuery();

        while(rs.next()) {
            int idOrdemServico = rs.getInt("idOrdemServico");
            LocalDateTime dataHora = rs.getTimestamp("dataHora").toLocalDateTime();
            double valorTotal = rs.getDouble("valorTotal");
            LocalDate dataEntrega = rs.getDate("DataEntrega").toLocalDate();
            String garantia = rs.getString("garantia");
            String observacao = rs.getString("observacao");
            double maoObra = rs.getDouble("maoObra");

            ArrayList<ItemServico> itens = carregarItensOrdem(idOrdemServico);

            OrdemServico ordemServico = new OrdemServico(idOrdemServico, null, null, dataHora, dataEntrega, garantia, observacao, valorTotal, maoObra, itens);

            lista.add(ordemServico);
        }

        rs.close();
        pstmt.close();
        con.close();

        return lista;
    }

    @Override
    public boolean removerOrdemServico(int id) throws Exception {
        Connection con = fabricaConexoes.getConnection();

        String sql = "UPDATE projeto_ordem_servico SET ativo=0 WHERE idOrdemServico=?";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setInt(1, id);

        int ret = pstmt.executeUpdate();

        return ret == 1;
    }
}