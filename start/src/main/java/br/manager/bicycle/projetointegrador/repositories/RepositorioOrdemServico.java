package br.manager.bicycle.projetointegrador.repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import br.manager.bicycle.projetointegrador.daos.interfaces.ClienteDAO;
import br.manager.bicycle.projetointegrador.daos.interfaces.FuncionarioDAO;
import br.manager.bicycle.projetointegrador.daos.interfaces.OrdemServicoDAO;
import br.manager.bicycle.projetointegrador.daos.interfaces.ProdutoDAO;
import br.manager.bicycle.projetointegrador.models.Cliente;
import br.manager.bicycle.projetointegrador.models.Funcionario;
import br.manager.bicycle.projetointegrador.models.ItemServico;
import br.manager.bicycle.projetointegrador.models.OrdemServico;
import br.manager.bicycle.projetointegrador.models.Produto;

public class RepositorioOrdemServico {
    
    private OrdemServicoDAO ordemServicoDAO;
    private ClienteDAO clienteDAO;
    private FuncionarioDAO funcionarioDAO;
    private ProdutoDAO produtoDAO;
    private OrdemServico ordemServico;

    public RepositorioOrdemServico(OrdemServicoDAO ordemServicoDAO, ClienteDAO clienteDAO, FuncionarioDAO funcionarioDAO, ProdutoDAO produtoDAO) {
        this.ordemServicoDAO = ordemServicoDAO;
        this.clienteDAO = clienteDAO;
        this.funcionarioDAO = funcionarioDAO;
        this.produtoDAO = produtoDAO;
    }

    public void iniciaOrdemServico(Cliente cliente, Funcionario funcionario) {
        LocalDateTime dataHora = LocalDateTime.now();
        ordemServico = new OrdemServico(cliente, funcionario, dataHora);
    }

    public void adicionaProduto(Produto produto, int quantidade) {
        ItemServico itemServico = new ItemServico(produto, quantidade, produto.getValor());
        ordemServico.adicionarItem(itemServico);
    }

    public void insereInfoAdicionais(LocalDate dataEntrega, String garantia, String observacao, Double maoObra) {
        ordemServico.setDataEntrega(dataEntrega);
        ordemServico.setGarantia(garantia);
        ordemServico.setObservacao(observacao);
        ordemServico.setMaoObra(maoObra);
    }

    public boolean finalizaOrdem() throws Exception {
        ordemServico.calcularTotal();
        ordemServicoDAO.salvarOrdemServico(ordemServico);
        ordemServico = null;
        return true;
    }

    public Double finalizaTotal() {
        return ordemServico.calcularTotal();
    }

    public Double finalizaTotalMaoObra() {
        return ordemServico.calcularTotalMaoObra();
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public ArrayList<OrdemServico> listar() throws Exception {
        ArrayList<OrdemServico> lista = ordemServicoDAO.listarOrdensServico();

        for(OrdemServico ordem:lista) {
            ordem.setCliente(clienteDAO.buscarClienteOrdem(ordem.getIdOrdemServico()));
            ordem.setTecnicoResponsavel(funcionarioDAO.buscarFuncionarioOrdem(ordem.getIdOrdemServico()));
            for(ItemServico item:ordem.getItensServico()){
                item.setProduto(produtoDAO.buscarProdutoItem(item.getIdItensServico()));
            }
        }
        return lista;
    }

    public boolean removerOrdemServico(int id) throws SQLException {
        try {
            return ordemServicoDAO.removerOrdemServico(id);
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }
}