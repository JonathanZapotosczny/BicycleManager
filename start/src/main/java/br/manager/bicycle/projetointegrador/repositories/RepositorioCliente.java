package br.manager.bicycle.projetointegrador.repositories;

import java.sql.SQLException;
import java.util.ArrayList;

import br.manager.bicycle.projetointegrador.daos.interfaces.ClienteDAO;
import br.manager.bicycle.projetointegrador.models.Cliente;

public class RepositorioCliente {

    private ArrayList<Cliente> clientes;
    private ClienteDAO clienteDAO;

    public RepositorioCliente(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
        clientes = new ArrayList<>();
    }

    public boolean cadastrarCliente(String nome, String email, String cpf, String telefone) throws SQLException {
        Cliente c = new Cliente(nome, email, cpf, telefone);

        try {
            clienteDAO.cadastrarCliente(c);
            this.clientes.add(c);
            return true;
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        } 
    }

    public boolean atualizarCliente(int id, String nome, String email, String cpf, String telefone) throws SQLException {
        Cliente cliente = new Cliente(nome, email, cpf, telefone);

        try {
            return clienteDAO.atualizarCliente(id, cliente);
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerCliente(int id) throws SQLException {
        try {
            return clienteDAO.removerCliente(id);
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Cliente> listarClientes() throws Exception {
        return clienteDAO.listarClientes();
    }
}