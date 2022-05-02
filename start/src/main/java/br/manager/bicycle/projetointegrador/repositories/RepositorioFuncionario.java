package br.manager.bicycle.projetointegrador.repositories;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import br.manager.bicycle.projetointegrador.daos.interfaces.FuncionarioDAO;
import br.manager.bicycle.projetointegrador.models.Funcionario;

public class RepositorioFuncionario {

    private ArrayList<Funcionario> funcionarios;
    private FuncionarioDAO funcionarioDAO;

    public RepositorioFuncionario(FuncionarioDAO funcionarioDAO) {
        this.funcionarioDAO = funcionarioDAO;
        funcionarios = new ArrayList<>();
    }

    public boolean cadastrarFuncionario(String nome, String email, String cpf, String telefone, LocalDate dataAdmissao) throws SQLException {
        Funcionario f = new Funcionario(nome, email, cpf, telefone, dataAdmissao);

        try {
            funcionarioDAO.cadastrarFuncionario(f);
            this.funcionarios.add(f);
            return true;
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean atualizarFuncionario(int id, String nome, String email, String cpf, String telefone, LocalDate dataAdmissao) throws SQLException {
        Funcionario funcionario = new Funcionario(nome, email, cpf, telefone, dataAdmissao);

        try {
            return funcionarioDAO.atualizarFuncionario(id, funcionario);
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public boolean removerFuncionario(int id) throws SQLException {
        try {
            return funcionarioDAO.removerFuncionario(id);
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        }
    }

    public ArrayList<Funcionario> listarFuncionarios() throws Exception {
        return funcionarioDAO.listarFuncionarios();
    }
}