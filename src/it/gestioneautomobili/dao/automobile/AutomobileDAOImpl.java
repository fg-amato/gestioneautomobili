package it.gestioneautomobili.dao.automobile;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.gestioneautomobili.model.Automobile;

public class AutomobileDAOImpl implements AutomobileDAO {
	private EntityManager entityManager;

	@Override
	public List<Automobile> list() throws Exception {
		return entityManager.createQuery("from Automobile", Automobile.class).getResultList();
	}

	@Override
	public Automobile get(Long id) throws Exception {
		return entityManager.find(Automobile.class, id);
	}

	@Override
	public void update(Automobile automobileInstance) throws Exception {
		if (automobileInstance == null) {
			throw new Exception("Problema valore in input");
		}

		automobileInstance = entityManager.merge(automobileInstance);

	}

	@Override
	public void insert(Automobile automobileInstance) throws Exception {
		if (automobileInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.persist(automobileInstance);
	}

	@Override
	public void delete(Automobile automobileInstance) throws Exception {
		if (automobileInstance == null) {
			throw new Exception("Problema valore in input");
		}

		entityManager.remove(entityManager.merge(automobileInstance));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public int countProprietariConAutomobileImmatricolataDopo(int annoDiImmatricolazioneInput) {
//		TypedQuery<Automobile> query = entityManager.createQuery(
//				"select count(a.proprietario.id), a from Automobile a where a.annoImmatricolazione > ?1",
//				Automobile.class);
//		return query.setParameter(1, annoDiImmatricolazioneInput).getResultList();
		return -1;
	}

	@Override
	public List<Automobile> findAllByCodFisProprietarioStartsWith(String inizialiInput) {
		TypedQuery<Automobile> query = entityManager
				.createQuery("from Automobile a where a.proprietario.codiceFiscale like ?1", Automobile.class);
		return query.setParameter(1, inizialiInput + "%").getResultList();
	}

}
