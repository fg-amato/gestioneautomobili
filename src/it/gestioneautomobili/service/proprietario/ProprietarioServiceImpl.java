package it.gestioneautomobili.service.proprietario;

import java.util.List;

import it.gestioneautomobili.dao.proprietario.ProprietarioDAO;
import it.gestioneautomobili.model.Proprietario;

public class ProprietarioServiceImpl implements ProprietarioService {

	private ProprietarioDAO proprietarioDAO;

	@Override
	public List<Proprietario> listAllAutomobili() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Proprietario caricaSingoloProprietario(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aggiorna(Proprietario proprietarioInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void inserisciNuovo(Proprietario proprietarioInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void rimuovi(Proprietario proprietarioInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProprietarioDAO(ProprietarioDAO proprietarioDAO) {
		this.proprietarioDAO = proprietarioDAO;
	}

}
