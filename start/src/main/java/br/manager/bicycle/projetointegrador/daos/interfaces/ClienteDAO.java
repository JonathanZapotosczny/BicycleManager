package br.manager.bicycle.projetointegrador.daos.interfaces;

import java.util.ArrayList;
import br.manager.bicycle.projetointegrador.models.Cliente;

public interface ClienteDAO {
    boolean cadastrarCliente(Cliente c) throws Exception;
    boolean atualizarCliente(int id, Cliente c) throws Exception;
    boolean removerCliente(int id) throws Exception;
    ArrayList<Cliente> listarClientes() throws Exception;
    Cliente buscarCliente(int id) throws Exception;
    Cliente buscarClienteOrdem(int idOrdemServico) throws Exception;
}

