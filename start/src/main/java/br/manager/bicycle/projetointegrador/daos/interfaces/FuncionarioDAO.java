package br.manager.bicycle.projetointegrador.daos.interfaces;

import java.util.ArrayList;
import br.manager.bicycle.projetointegrador.models.Funcionario;

public interface FuncionarioDAO {
    boolean cadastrarFuncionario(Funcionario f) throws Exception;
    boolean atualizarFuncionario(int id, Funcionario f) throws Exception;
    boolean removerFuncionario(int id) throws Exception;
    ArrayList<Funcionario> listarFuncionarios() throws Exception;
    Funcionario buscarFuncionario(int id) throws Exception;
    Funcionario buscarFuncionarioOrdem(int idOrdemServico) throws Exception;
}
