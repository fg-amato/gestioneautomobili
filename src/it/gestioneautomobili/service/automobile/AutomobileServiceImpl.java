package it.gestioneautomobili.service.automobile;

import java.util.List;

import it.gestioneautomobili.dao.automobile.AutomobileDAO;
import it.gestioneautomobili.dao.proprietario.ProprietarioDAO;
import it.gestioneautomobili.model.Automobile;

public class AutomobileServiceImpl implements AutomobileService {

	private AutomobileDAO automobileDAO;

	@Override
	public List<Automobile> listAllAutomobili() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Automobile caricaSingoloAutomobile(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void aggiorna(Automobile automobileInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void inserisciNuovo(Automobile automobileInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void rimuovi(Automobile automobileInstance) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAutomobileDAO(AutomobileDAO automobileDAO) {
		this.automobileDAO = automobileDAO;
	}

}
