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
//			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
//					+ " elementi.");
//
//			testInserisciProprietario(proprietarioService);
//			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
//					+ " elementi.");
//
//			testRimozioneProprietario(proprietarioService);
//			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
//					+ " elementi.");
//
//			testUpdateProprietario(proprietarioService);
//			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
//					+ " elementi.");

//			System.out.println(
//					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");
//
//			testInserisciAutomobile(proprietarioService, automobileService);
//			System.out.println(
//					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");
//
//			testRimozioneAutomobile(proprietarioService, automobileService);
//			System.out.println(
//					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");
//
//			testUpdateAutomobile(proprietarioService, automobileService);
//			System.out.println(
//					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");

			testFindAllProprietariCodFisIniziaPer(proprietarioService, automobileService);

			System.out.println(
					"In tabella Automobile ci sono " + automobileService.listAllAutomobili().size() + " elementi.");

			System.out.println("In tabella Proprietario ci sono " + proprietarioService.listAllProprietari().size()
					+ " elementi.");
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
		// creo nuovo automobile ma prima mi serve un proprietario
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

	private static void testUpdateProprietario(ProprietarioService proprietarioService) throws Exception {
		System.out.println(".......testUpdateProprietario inizio.............");
		// creo nuovo proprietario
		Date dataDiNascitaProprietarioMarioRossi = new SimpleDateFormat("dd-MM-yyyy").parse("24-01-1990");
		Proprietario nuovoProprietarioMarioRossi = new Proprietario("Mario", "Rossi", "RSSMRI90FIS",
				dataDiNascitaProprietarioMarioRossi);
		if (nuovoProprietarioMarioRossi.getId() != null)
			throw new RuntimeException("testUpdateProprietario FALLITO: record già presente ");

		// salvo
		proprietarioService.inserisciNuovo(nuovoProprietarioMarioRossi);

		if (nuovoProprietarioMarioRossi.getId() == null)
			throw new RuntimeException("testUpdateProprietario FALLITO ");

		nuovoProprietarioMarioRossi.setNome("Paolo");
		nuovoProprietarioMarioRossi.setCognome("Bianchi");
		nuovoProprietarioMarioRossi.setCodiceFiscale("BNCPLO90FIS");

		Long idProprietarioDaAggiornare = nuovoProprietarioMarioRossi.getId();

		proprietarioService.aggiorna(nuovoProprietarioMarioRossi);

		Proprietario proprietarioModificato = proprietarioService.caricaSingoloProprietario(idProprietarioDaAggiornare);

		if (!nuovoProprietarioMarioRossi.equals(proprietarioModificato)) {
			throw new RuntimeException("testUpdateProprietario FALLITO: record non aggiornato");
		}
		System.out.println(".......testUpdateProprietario fine: PASSED.............");
	}

	private static void testUpdateAutomobile(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println(".......testUpdateAutomobile inizio.............");

		// inserisco un automobile che rimuoverò
		// creo nuovo automobile ma prima mi serve un proprietario
		List<Proprietario> listaProprietariPresenti = proprietarioService.listAllProprietari();
		if (listaProprietariPresenti.isEmpty())
			throw new RuntimeException("testUpdateAutomobile FALLITO: non ci sono proprietari a cui collegarci ");

		Automobile automobileDaAggiornare = new Automobile("Audi", "A3", "MM789NN", 2015);
		// lo lego al primo proprietario che trovo
		automobileDaAggiornare.setProprietario(listaProprietariPresenti.get(0));

		automobileService.inserisciNuovo(automobileDaAggiornare);

		if (automobileDaAggiornare.getId() == null)
			throw new RuntimeException("testUpdateAutomobile FALLITO ");

		// aggiorno i campi dopo l'inserimento
		if (listaProprietariPresenti.size() > 1) {
			automobileDaAggiornare.setProprietario(listaProprietariPresenti.get(listaProprietariPresenti.size() - 1));
		}

		automobileDaAggiornare.setAnnoDiImmatricolazione(2012);
		automobileDaAggiornare.setMarca("BMW");
		automobileDaAggiornare.setModello("X3");

		automobileService.aggiorna(automobileDaAggiornare);

		Long idAutomobileDaAggiornare = automobileDaAggiornare.getId();

		Automobile automobileAggiornata = automobileService.caricaSingoloAutomobile(idAutomobileDaAggiornare);

		if (!automobileAggiornata.equals(automobileDaAggiornare)) {
			throw new RuntimeException("testUpdateAutomobile FALLITO: record non aggiornato");
		}

		System.out.println(".......testUpdateAutomobile fine: PASSED.............");
	}

	private static void testCountProprietariWithAutomobileImmatricolataDopo(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println(".......testCountProprietariWithAutomobileImmatricolataDopo inizio.............");

		// istanzio 3 proprietari
		Date dataDiNascitaProprietarioPeppeVerdi = new SimpleDateFormat("dd-MM-yyyy").parse("02-10-1995");
		Proprietario nuovoProprietarioPeppeVerdi = new Proprietario("Peppe", "Verdi", "VRDPPP95FIS",
				dataDiNascitaProprietarioPeppeVerdi);

		Date dataDiNascitaProprietarioMimmoMammi = new SimpleDateFormat("dd-MM-yyyy").parse("15-05-1993");
		Proprietario nuovoProprietarioMimmoMammi = new Proprietario("Mimmo", "Mammi", "MMMMMM93FIS",
				dataDiNascitaProprietarioMimmoMammi);

		Date dataDiNascitaProprietarioLucaGialli = new SimpleDateFormat("dd-MM-yyyy").parse("30-11-1979");
		Proprietario nuovoProprietarioLucaGialli = new Proprietario("Luca", "Gialli", "LCAGLL79FIS",
				dataDiNascitaProprietarioLucaGialli);

		proprietarioService.inserisciNuovo(nuovoProprietarioPeppeVerdi);
		proprietarioService.inserisciNuovo(nuovoProprietarioMimmoMammi);
		proprietarioService.inserisciNuovo(nuovoProprietarioLucaGialli);

		if (nuovoProprietarioLucaGialli.getId() == null || nuovoProprietarioMimmoMammi.getId() == null
				|| nuovoProprietarioPeppeVerdi.getId() == null) {
			throw new RuntimeException(
					"testCountProprietariWithAutomobileImmatricolataDopo FALLITO: record già presente ");
		}

		Automobile primaAutomobileLucaGialli = new Automobile("Fiat", "Idea", "KM123MK", 2021);
		Automobile secondaAutomobileLucaGialli = new Automobile("Fiat", "500X", "LG444GL", 2022);

		primaAutomobileLucaGialli.setProprietario(nuovoProprietarioLucaGialli);
		secondaAutomobileLucaGialli.setProprietario(nuovoProprietarioLucaGialli);

		automobileService.inserisciNuovo(primaAutomobileLucaGialli);
		automobileService.inserisciNuovo(secondaAutomobileLucaGialli);

		if (primaAutomobileLucaGialli.getId() == null || secondaAutomobileLucaGialli.getId() == null)
			throw new RuntimeException("testCountProprietariWithAutomobileImmatricolataDopo FALLITO ");

		Automobile primaAutomobileMimmoMammi = new Automobile("Fiat", "Idea", "KM123MK", 2010);
		Automobile secondaAutomobileMimmoMammi = new Automobile("Fiat", "500X", "LG444GL", 2012);

		primaAutomobileMimmoMammi.setProprietario(nuovoProprietarioMimmoMammi);
		secondaAutomobileMimmoMammi.setProprietario(nuovoProprietarioMimmoMammi);

		automobileService.inserisciNuovo(primaAutomobileMimmoMammi);
		automobileService.inserisciNuovo(secondaAutomobileMimmoMammi);

		if (primaAutomobileMimmoMammi.getId() == null || secondaAutomobileMimmoMammi.getId() == null)
			throw new RuntimeException("testCountProprietariWithAutomobileImmatricolataDopo FALLITO ");

		Automobile primaAutomobilePeppeVerdi = new Automobile("Fiat", "Idea", "KM123MK", 2025);
		Automobile secondaAutomobilePeppeVerdi = new Automobile("Fiat", "500X", "LG444GL", 2026);

		primaAutomobilePeppeVerdi.setProprietario(nuovoProprietarioPeppeVerdi);
		secondaAutomobilePeppeVerdi.setProprietario(nuovoProprietarioPeppeVerdi);

		automobileService.inserisciNuovo(primaAutomobilePeppeVerdi);
		automobileService.inserisciNuovo(secondaAutomobilePeppeVerdi);

		if (primaAutomobilePeppeVerdi.getId() == null || secondaAutomobilePeppeVerdi.getId() == null)
			throw new RuntimeException("testCountProprietariWithAutomobileImmatricolataDopo FALLITO ");

		int numeroDiProprietariConAutoImmatricolateDopoIl2020 = automobileService
				.countProprietariConAutomobiliImmatricolateDopoLAnno(2020);

		if (numeroDiProprietariConAutoImmatricolateDopoIl2020 != 2) {
			throw new RuntimeException("testCountProprietariWithAutomobileImmatricolataDopo FALLITO");
		}

		// ripulisco

		automobileService.rimuovi(primaAutomobilePeppeVerdi);
		automobileService.rimuovi(secondaAutomobilePeppeVerdi);
		automobileService.rimuovi(primaAutomobileMimmoMammi);
		automobileService.rimuovi(secondaAutomobileMimmoMammi);
		automobileService.rimuovi(primaAutomobileLucaGialli);
		automobileService.rimuovi(secondaAutomobileLucaGialli);

		boolean autoRimosseDiPeppeVerdi = (primaAutomobilePeppeVerdi.getId() == null
				&& secondaAutomobilePeppeVerdi.getId() == null);

		boolean autoRimosseDiMimmoMammi = (primaAutomobileMimmoMammi.getId() == null
				&& secondaAutomobileMimmoMammi.getId() == null);

		boolean autoRimosseDiLucaGialli = (primaAutomobileLucaGialli.getId() == null
				&& secondaAutomobileLucaGialli.getId() == null);

		if (!autoRimosseDiPeppeVerdi || !autoRimosseDiMimmoMammi || !autoRimosseDiLucaGialli) {
			throw new RuntimeException("testCountProprietariWithAutomobileImmatricolataDopo FALLITO");
		}

		proprietarioService.rimuovi(nuovoProprietarioLucaGialli);
		proprietarioService.rimuovi(nuovoProprietarioMimmoMammi);
		proprietarioService.rimuovi(nuovoProprietarioPeppeVerdi);

		if (nuovoProprietarioLucaGialli.getId() != null || nuovoProprietarioMimmoMammi.getId() != null
				|| nuovoProprietarioPeppeVerdi.getId() != null) {
			throw new RuntimeException("testCountProprietariWithAutomobileImmatricolataDopo FALLITO");
		}
		System.out.println(".......testCountProprietariWithAutomobileImmatricolataDopo fine: PASSED.............");
	}

	private static void testFindAllProprietariCodFisIniziaPer(ProprietarioService proprietarioService,
			AutomobileService automobileService) throws Exception {
		System.out.println(".......testFindAllProprietariCodFisIniziaPer inizio.............");

		// istanzio 3 proprietari
		Date dataDiNascitaProprietarioPeppeVerdi = new SimpleDateFormat("dd-MM-yyyy").parse("02-10-1995");
		Proprietario nuovoProprietarioPeppeVerdi = new Proprietario("Peppe", "Verdi", "popopopo",
				dataDiNascitaProprietarioPeppeVerdi);

		Date dataDiNascitaProprietarioMimmoMammi = new SimpleDateFormat("dd-MM-yyyy").parse("15-05-1993");
		Proprietario nuovoProprietarioMimmoMammi = new Proprietario("Mimmo", "Mammi", "popopopo",
				dataDiNascitaProprietarioMimmoMammi);

		Date dataDiNascitaProprietarioLucaGialli = new SimpleDateFormat("dd-MM-yyyy").parse("30-11-1979");
		Proprietario nuovoProprietarioLucaGialli = new Proprietario("Luca", "Gialli", "popopopo",
				dataDiNascitaProprietarioLucaGialli);

		proprietarioService.inserisciNuovo(nuovoProprietarioPeppeVerdi);
		proprietarioService.inserisciNuovo(nuovoProprietarioMimmoMammi);
		proprietarioService.inserisciNuovo(nuovoProprietarioLucaGialli);

		if (nuovoProprietarioLucaGialli.getId() == null || nuovoProprietarioMimmoMammi.getId() == null
				|| nuovoProprietarioPeppeVerdi.getId() == null) {
			throw new RuntimeException("testFindAllProprietariCodFisIniziaPer FALLITO: record già presente ");
		}

		Automobile primaAutomobileLucaGialli = new Automobile("Fiat", "Idea", "KM123MK", 2021);
		Automobile secondaAutomobileLucaGialli = new Automobile("Fiat", "500X", "LG444GL", 2022);

		primaAutomobileLucaGialli.setProprietario(nuovoProprietarioLucaGialli);
		secondaAutomobileLucaGialli.setProprietario(nuovoProprietarioLucaGialli);

		automobileService.inserisciNuovo(primaAutomobileLucaGialli);
		automobileService.inserisciNuovo(secondaAutomobileLucaGialli);

		if (primaAutomobileLucaGialli.getId() == null || secondaAutomobileLucaGialli.getId() == null)
			throw new RuntimeException("testFindAllProprietariCodFisIniziaPer FALLITO ");

		Automobile primaAutomobileMimmoMammi = new Automobile("Fiat", "Idea", "KM123MK", 2010);
		Automobile secondaAutomobileMimmoMammi = new Automobile("Fiat", "500X", "LG444GL", 2012);

		primaAutomobileMimmoMammi.setProprietario(nuovoProprietarioMimmoMammi);
		secondaAutomobileMimmoMammi.setProprietario(nuovoProprietarioMimmoMammi);

		automobileService.inserisciNuovo(primaAutomobileMimmoMammi);
		automobileService.inserisciNuovo(secondaAutomobileMimmoMammi);

		if (primaAutomobileMimmoMammi.getId() == null || secondaAutomobileMimmoMammi.getId() == null)
			throw new RuntimeException("testFindAllProprietariCodFisIniziaPer FALLITO ");

		Automobile primaAutomobilePeppeVerdi = new Automobile("Fiat", "Idea", "KM123MK", 2025);
		Automobile secondaAutomobilePeppeVerdi = new Automobile("Fiat", "500X", "LG444GL", 2026);

		primaAutomobilePeppeVerdi.setProprietario(nuovoProprietarioPeppeVerdi);
		secondaAutomobilePeppeVerdi.setProprietario(nuovoProprietarioPeppeVerdi);

		automobileService.inserisciNuovo(primaAutomobilePeppeVerdi);
		automobileService.inserisciNuovo(secondaAutomobilePeppeVerdi);

		if (primaAutomobilePeppeVerdi.getId() == null || secondaAutomobilePeppeVerdi.getId() == null)
			throw new RuntimeException("testFindAllProprietariCodFisIniziaPer FALLITO ");

		List<Automobile> automobiliProprietarioCodFisStartsWithPopopopo = automobileService
				.listAllAutomobiliConCodFisProprietarioCheIniziaPer("popopopo");

		for (Automobile automobiliProprietarioCodFisStartsWithPopopopoItem : automobiliProprietarioCodFisStartsWithPopopopo) {
			if (!automobiliProprietarioCodFisStartsWithPopopopoItem.getProprietario().getCodiceFiscale()
					.startsWith("popopopo")) {
				throw new RuntimeException("testFindAllProprietariCodFisIniziaPer FALLITO");
			}
		}
		// ripulisco

		automobileService.rimuovi(primaAutomobilePeppeVerdi);
		automobileService.rimuovi(secondaAutomobilePeppeVerdi);
		automobileService.rimuovi(primaAutomobileMimmoMammi);
		automobileService.rimuovi(secondaAutomobileMimmoMammi);
		automobileService.rimuovi(primaAutomobileLucaGialli);
		automobileService.rimuovi(secondaAutomobileLucaGialli);

		proprietarioService.rimuovi(nuovoProprietarioLucaGialli);
		proprietarioService.rimuovi(nuovoProprietarioMimmoMammi);
		proprietarioService.rimuovi(nuovoProprietarioPeppeVerdi);

		System.out.println(".......testFindAllProprietariCodFisIniziaPer fine: PASSED.............");
	}
}
