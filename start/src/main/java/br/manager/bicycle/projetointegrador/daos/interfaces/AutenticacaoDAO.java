package br.manager.bicycle.projetointegrador.daos.interfaces;

import br.manager.bicycle.projetointegrador.models.Usuario;

public interface AutenticacaoDAO {
    Usuario login(String login,String senha) throws Exception;
    boolean cadastrar(Usuario u) throws Exception;
}

