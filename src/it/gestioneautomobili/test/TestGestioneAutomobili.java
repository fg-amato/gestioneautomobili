package it.gestioneautomobili.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import it.gestioneautomobili.dao.EntityManagerUtil;
import it.gestioneautomobili.model.Automobile;
import it.gestioneautomobili.model.Proprietario;
import it.gestioneautomobili.service.MyServiceFactory;
import it.gestioneautomobili.service.automobile.AutomobileService;
import it.gestioneautomobili.service.proprietario.ProprietarioService;

public class TestGestioneAutomobili {

	public static void main(String[] args) {

		AutomobileService automobileService = MyServiceFactory.getAutomobileServiceInstance();
		ProprietarioService proprietarioService = MyServiceFactory.getProprietarioServiceInstance();

		try {

			// ora con il service posso fare tutte le invocazioni che mi servono
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			testInserisciProprietario(proprietarioService);
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			testRimozioneProprietario(proprietarioService);
			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			testInserisciAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			testRimozioneAutomobile(proprietarioService, automobileService);
			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}
	}

	private static void testInserisciProprietario(ProprietarioService proprietarioService) throws Exception {
		System.out.println(".......testInserisciProprietario inizio.............");
		// creo nuovo proprietario
		Date dataDiNascitaProprietarioFlavioAmato = new SimpleDateFormat("dd-MM-yyyy").parse("02-12-1999");
		Proprietario nuovoProprietarioFlavioAmato = new Proprietario("Flavio", "Amato", "MTAFVG99FIS",
				dataDiNascitaProprietarioFlavioAmato);
		if (nuovoProprietarioFlavioAmato.getId() != null)
			throw new RuntimeException("testInserisciProprietario FALLITO: record già presente ");

		// salvo
		proprietarioService.inserisciNuovo(nuovoProprietarioFlavioAmato);

		if (nuovoProprietarioFlavioAmato.getId() == null)
			throw new RuntimeException("testInserisciProprietario FALLITO ");

		System.out.println(".......testInserisciProprietario fine: PASSED.............");
	}

	private static void testInserisciAutomobile(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println(".......testInserisciAutomobile inizio.............");

		// creo nuova automobile ma prima mi serve un proprietario
		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();
		if (listaProprietariPresenti.isEmpty())
			throw new RuntimeException("testInserisciAutomobile FALLITO: non ci sono proprietari a cui collegarci ");

		Automobile nuovoAutomobile = new Automobile("Fiat", "Tipo", "XX123YY", 2019);
		// lo lego al primo proprietario che trovo
		nuovoAutomobile.setProprietario(listaProprietariPresenti.get(0));

		// salvo il nuovo abitante
		automobileService.inserisciNuovo(nuovoAutomobile);

		if (nuovoAutomobile.getId() == null)
			throw new RuntimeException("testInserisciAutomobile FALLITO ");

		// il test fallisce anche se non è riuscito a legare i due oggetti
		if (nuovoAutomobile.getProprietario() == null)
			throw new RuntimeException("testInserisciAutomobile FALLITO: non ha collegato il proprietario ");

		System.out.println(".......testInserisciAutomobile fine: PASSED.............");
	}

	private static void testRimozioneAutomobile(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println(".......testRimozioneAutomobile inizio.............");

		// inserisco un abitante che rimuoverò
		// creo nuovo abitante ma prima mi serve un proprietario
		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();
		if (listaProprietariPresenti.isEmpty())
			throw new RuntimeException("testRimozioneAutomobile FALLITO: non ci sono proprietari a cui collegarci ");

		Automobile automobileDaRimuovere = new Automobile("Nissan", "Qashqai", "CC456BB", 2017);
		// lo lego al primo proprietario che trovo
		automobileDaRimuovere.setProprietario(listaProprietariPresenti.get(0));

		// salvo il nuovo abitante
		automobileService.inserisciNuovo(automobileDaRimuovere);

		Long idAutomobileInserita = automobileDaRimuovere.getId();
		automobileService.rimuovi(automobileService.caricaSingoloAutomobile(idAutomobileInserita));
		// proviamo a vedere se è stato rimosso
		if (automobileService.caricaSingoloAutomobile(idAutomobileInserita) != null)
			throw new RuntimeException("testRimozioneAutomobile FALLITO: record non cancellato ");
		System.out.println(".......testRimozioneAutomobile fine: PASSED.............");
	}

	private static void testRimozioneProprietario(ProprietarioService proprietarioService) throws Exception {
		System.out.println(".......testRimozioneProprietario inizio.............");
		// creo nuovo proprietario
		Date dataDiNascitaProprietarioGiovanniNeri = new SimpleDateFormat("dd-MM-yyyy").parse("15-02-1985");
		Proprietario nuovoProprietarioGiovanniNeri = new Proprietario("Giovanni", "Neri", "NRIGVN85FIS",
				dataDiNascitaProprietarioGiovanniNeri);
		if (nuovoProprietarioGiovanniNeri.getId() != null)
			throw new RuntimeException("testRimozioneProprietario FALLITO: record già presente ");

		// salvo
		proprietarioService.inserisciNuovo(nuovoProprietarioGiovanniNeri);

		if (nuovoProprietarioGiovanniNeri.getId() == null)
			throw new RuntimeException("testRimozioneProprietario FALLITO ");

		Long idGiovanniNeriProprietario = nuovoProprietarioGiovanniNeri.getId();

		proprietarioService.rimuovi(proprietarioService.caricaSingoloProprietario(idGiovanniNeriProprietario));

		if (proprietarioService.caricaSingoloProprietario(idGiovanniNeriProprietario) != null)
			throw new RuntimeException("testRimozioneProprietario FALLITO: record non cancellato ");

		System.out.println(".......testRimozioneProprietario fine: PASSED.............");
	}
}
