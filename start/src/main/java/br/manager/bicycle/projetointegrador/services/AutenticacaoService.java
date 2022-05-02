package br.manager.bicycle.projetointegrador.services;

import java.sql.SQLException;

import br.manager.bicycle.projetointegrador.daos.interfaces.AutenticacaoDAO;
import br.manager.bicycle.projetointegrador.models.Usuario;

public class AutenticacaoService {

    private Usuario logado;
    private AutenticacaoDAO autenticacaoDAO;

    public AutenticacaoService(AutenticacaoDAO autenticacaoDAO) {
        this.autenticacaoDAO = autenticacaoDAO;
    }

    public boolean logar(String login, String senha) throws Exception {
        try {
            this.logado = autenticacaoDAO.login(login, senha);
            return true;
        }catch (Exception e) {
            e.getMessage();
            return false;
        }
    }

    public boolean cadastrar(String login, String senha) throws Exception {
        Usuario u = new Usuario(login, senha);

        try {
            autenticacaoDAO.cadastrar(u);
            return true;
        }catch (Exception e) {
            throw new SQLException(e.getMessage());
        } 
    }

    public Usuario getLogado() {
        return this.logado;
    }

    public void logout() {
        this.logado = null;
    }

    public boolean estaLogado() {
        return this.logado != null;
    }
}