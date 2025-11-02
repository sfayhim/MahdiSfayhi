package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de
	// l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l'argent
	void insertMoneyChangesBalance() {
		// GIVEN : une machine vierge (initialisée dans @BeforeEach)
		// WHEN On insère de l'argent
		machine.insertMoney(10);
		machine.insertMoney(20);
		// THEN La balance est mise à jour, les montants sont correctement additionnés
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");
	}

	@Test
	// S3 : on n'imprime pas le ticket si le montant inséré est insuffisant
	void cantPrintTicketWithInsufficientAmount() {
		// GIVEN : une machine vierge avec un montant insuffisant
		machine.insertMoney(PRICE - 10);
		// WHEN on essaie d'imprimer un ticket
		boolean result = machine.printTicket();
		// THEN l'impression échoue
		assertFalse(result, "Le ticket ne devrait pas s'imprimer avec un montant insuffisant");
	}

	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	void canPrintTicketWithSufficientAmount() {
		// GIVEN : une machine avec un montant suffisant
		machine.insertMoney(PRICE);
		// WHEN on imprime un ticket
		boolean result = machine.printTicket();
		// THEN l'impression réussit
		assertTrue(result, "Le ticket devrait s'imprimer avec un montant suffisant");
	}

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	void balanceDecreasesAfterPrinting() {
		// GIVEN : une machine avec plus que le montant nécessaire
		machine.insertMoney(PRICE + 10);
		// WHEN on imprime un ticket
		machine.printTicket();
		// THEN la balance est décrémentée du prix du ticket
		assertEquals(10, machine.getBalance(), "La balance devrait être décrémentée du prix du ticket");
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket
	void totalIsUpdatedAfterPrinting() {
		// GIVEN : une machine vierge avec le montant exact
		machine.insertMoney(PRICE);
		// WHEN on imprime un ticket
		machine.printTicket();
		// THEN le total est mis à jour
		assertEquals(PRICE, machine.getTotal(), "Le total devrait être mis à jour après impression");
	}

	@Test
	// S7 et S8 : refund() rend correctement la monnaie et remet la balance à zéro
	void refundGivesCorrectAmountAndResetsBalance() {
		// GIVEN : une machine avec de l'argent inséré
		machine.insertMoney(30);
		// WHEN on demande le remboursement
		int refunded = machine.refund();
		// THEN le montant remboursé est correct et la balance est à zéro
		assertEquals(30, refunded, "Le montant remboursé devrait être égal à la balance");
		assertEquals(0, machine.getBalance(), "La balance devrait être à zéro après remboursement");
	}

	@Test
	// S9 : on ne peut pas insérer un montant négatif
	void cantInsertNegativeAmount() {
		// WHEN on essaie d'insérer un montant négatif THEN une exception est levée
		assertThrows(IllegalArgumentException.class,
			() -> machine.insertMoney(-10),
			"L'insertion d'un montant négatif devrait lever une exception"
		);
	}

	@Test
	// S10 : on ne peut pas créer de machine avec un prix négatif
	void cantCreateMachineWithNegativePrice() {
		// WHEN on essaie de créer une machine avec un prix négatif THEN une exception est levée
		assertThrows(IllegalArgumentException.class,
			() -> new TicketMachine(-50),
			"La création d'une machine avec un prix négatif devrait lever une exception"
		);
	}
}
