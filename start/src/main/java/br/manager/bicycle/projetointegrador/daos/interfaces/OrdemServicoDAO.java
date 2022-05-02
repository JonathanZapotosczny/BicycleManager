package br.manager.bicycle.projetointegrador.daos.interfaces;

import java.util.ArrayList;
import br.manager.bicycle.projetointegrador.models.OrdemServico;

public interface OrdemServicoDAO {
    boolean salvarOrdemServico(OrdemServico ordemServico) throws Exception;
    ArrayList<OrdemServico> listarOrdensServico() throws Exception;
    boolean removerOrdemServico(int id) throws Exception;
}

