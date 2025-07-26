import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
/**
     * Controller class for the Trading Card Inventory System.
     * Coordinates interactions between the model and the view.
     */
public class TradingCardInventorySystemController {
    private final TradingCardInventorySystemModel model;
    private final TradingCardInventorySystemView view;
    /**
     * Constructs a controller for the inventory system.
     *
     * @param model the data model
     * @param view the user interface view
     */
    public TradingCardInventorySystemController(TradingCardInventorySystemModel model, TradingCardInventorySystemView view) {
        this.model = model;
        this.view = view;
    }
    /**
     * Starts the main program loop and displays the menu system.
     */
    public void startProgram() {
        boolean running = true;

        while(running) {
            view.displayMainMenu(model.hasCards(), model.hasBinders(), model.hasDecks());
            int choice = view.getMenuChoice();

            switch(choice) {
                case 1 -> addCard();
                case 2 -> {
                    if (model.hasBinders()) {
                        manageBinders();
                    } else {
                        createBinder();
                    }
                }
                case 3 -> {
                    if (model.hasDecks()) {
                        manageDecks();
                    } else {
                        createDeck();
                    }
                }
                case 4 -> {
                    if (model.hasCards()) {
                        adjustCardCount();
                    } else {
                        view.displayError("Invalid option. ");
                    }
                }
                case 5 -> {
                    if(model.hasCards()) {
                        displayCardOrCollection();
                    } else {
                        view.displayError("Invalid option.");
                    }
                }
                case 6 -> {
                    if(model.hasCards()) {
                        sellCards();
                    }else{
                        view.displayError("Invalid option.");
                    }
                }
                case 0 -> {
                    System.out.println("Exiting program...");
                    view.closeScanner();
                    running = false;
                }
                default -> view.displayError("Invalid option. ");
            }

            if(!model.hasCards()) {
                if(choice >= 1 && choice <= 3) {
                    view.pause();
                }
            } else {
                if(choice >= 1 && choice <= 5) {
                    view.pause();
                }
            }
        }
    }
    /**
     * Adds a new card to the collection or adjusts count if duplicate exists.
     */
    private void addCard() {
        String name = view.promptForCardName();

        if(name == null || name.isBlank()) {
            view.returningToMainMenu();
            return;
        }

        Card existing = model.getCardFromCollection(name);

        if(existing != null) {
            System.out.println("A card with that name already exists.");

            if(view.confirmAction("Do you want to increase its count instead?")) {
                int adjustment = view.promptForCardAdjustment();

                if(model.adjustCardCount(existing, adjustment)) {
                    System.out.println("Card count updated.");
                } else {
                    view.displayError("Invalid adjustment.");
                }
            } else {
                System.out.println("No changes made.");
            }
            return;
        }

        CardRarity rarity = view.promptForCardRarity();
        if(rarity == null) {
            view.returningToMainMenu();
            return;
        }

        CardVariant variant = view.promptForCardVariant(rarity);
        if(variant == null) {
            view.returningToMainMenu();
            return;
        }

        BigDecimal value = view.promptForCardValue();
        if(value == null || value.compareTo(BigDecimal.ZERO) < 0) {
            view.returningToMainMenu();
            return;
        }

        model.addCardToCollection(name, rarity, variant, value);
        System.out.println("Card successfully added!");
    }


    /**
     * Prompts user to create a new binder.
     */
    // Update createBinder method
    private void createBinder() {
        String name = view.promptForBinderName();
        if (name == null || name.isEmpty()) {
            view.returningToMainMenu();
            return;
        }

        //view.displayBinderTypeMenu();
        int typeChoice = view.promptForBinderType();

        if (typeChoice < 1 || typeChoice > 5) {
            view.returningToMainMenu();
            return;
        }

        if (model.createBinder(name, typeChoice)) {
            System.out.println("Binder created successfully!");
        } else {
            view.displayError("Failed to create binder (name might exist)");
        }
    }

    /**
     * Displays binder management menu and handles user choices.
     */
    private void manageBinders() {
        boolean exit = false;

        while (!exit) {
            view.displayManageBindersMenu(model.getBinderNames());
            int choice = view.getMenuChoice();

            if(choice != 0) {
                if(choice == model.getBinderCount() + 1) {
                    createBinder();
                } else if (choice > 0 && choice <= model.getBinderCount()) {
                    manageSingleBinder(choice - 1);
                } else {
                    view.displayError("Invalid choice.");
                }
            } else {
                exit = true;
            }
        }
    }
    /**
     * Manages a specific binder by index.
     *
     * @param binderIdx the index of the binder
     */
    // Update manageSingleBinder to handle selling
    private void manageSingleBinder(int binderIdx) {
        Binder binder = model.getBinder(binderIdx);
        boolean managing = true;

        while(managing) {
            view.displayManageSingleBinderMenu(binder.getName(), model.isSellableBinder(binder));

            int choice = view.getMenuChoice();

            switch(choice) {
                case 1 -> view.displayBinderCards(binder.getCards());
                case 2 -> {  // Add Card
                    if (binder.getCardsCount() < Binder.MAX_CARD_COUNT) {
                        List<Card> collection = model.getCardCollection();
                        if (view.displayCollection(collection)) {
                            int index = view.getCardChoice();
                            if (index >= 0 && index < collection.size()) {
                                Card selected = model.getCardFromCollection(index);
                                if (!model.addCardToBinder(binder, selected)) {
                                    view.displayError("Cannot add card - invalid type or count");
                                } else {
                                    System.out.println("Card added successfully!");
                                }
                            }
                        }
                    } else {
                        System.out.println("Binder is full.");
                    }
                }
                case 3 -> {  // Remove Card
                    List<Card> binderCards = binder.getCards();
                    if(view.displayBinderCards(binderCards)) {
                        System.out.println("Enter num not in list to go back.");
                        int index = view.getCardChoice();
                        if (index >= 0 && index < binderCards.size()) {
                            Card selected = binderCards.get(index);
                            model.removeCardFromBinder(binder, selected);
                            System.out.println("Card removed from binder.");
                        }
                    }
                }
                case 4 -> {  // Trade OR Sell
                    if (model.isSellableBinder(binder)) {
                        sellBinder(binder);
                        managing = false;
                    } else {
                        List<Card> binderCards = binder.getCards();
                        if(view.displayBinderCards(binderCards)) {
                            tradeCardInBinder(binder);
                        }
                    }
                }
                case 5 -> {  // Delete Binder
                    if(view.confirmAction("Delete this binder and return all cards to collection?")) {
                        boolean deleted = model.deleteBinder(binder);
                        if(deleted) {
                            System.out.println("Binder deleted.");
                            managing = false;
                        } else {
                            view.displayError("Failed to delete binder.");
                        }
                    }
                }
                case 0 -> managing = false;
                default -> view.displayError("Invalid choice.");
            }
        }
    }
    /**
     * Handles trading cards within a binder.
     *
     * @param binder the binder to operate on
     */
    public void tradeCardInBinder(Binder binder) {
        List<Card> binderCards = binder.getCards();

        System.out.println("Enter num not in list to go back.");
        int outgoingIndex = view.selectCardFromList(binderCards);

        if(outgoingIndex < 0 || outgoingIndex >= binderCards.size()) {
            view.returningToMainMenu();
            return;
        }

        Card outgoing = binderCards.get(outgoingIndex);
        Card incoming = view.promptNewCardDetails();

        if(incoming == null) {
            System.out.println("Trade cancelled.");
            return;
        }

        boolean confirm = view.confirmBigDiffTrade(incoming, outgoing);

        if(!confirm) {
            System.out.println("Trade cancelled.");
            return;
        }

        boolean success = model.executeTrade(binder, incoming, outgoing);

        if(success) {
            System.out.println("Trade successful!");
        }
        else {
            view.displayError("Trade failed.");
        }
    }

    /**
     * Prompts user to create a new deck.
     */
    private void createDeck() {
        String name = view.promptForDeckName();
        if (name == null || name.isEmpty()) return;

        int typeChoice = view.promptForDeckType();

        if (typeChoice == 1) {
            model.createDeck(name, false);
        } else if (typeChoice == 2) {
            model.createDeck(name, true);
        } else {
            view.displayError("Invalid deck type");
            return;
        }
        System.out.println("Deck created successfully!");
    }
    /**
     * Displays deck management menu and handles user choices.
     */
    private void manageDecks() {
        boolean exit = false;

        while(!exit) {
            view.displayManageDecksMenu(model.getDeckNames());
            int choice = view.getMenuChoice();

            if(choice != 0) {
                if(choice == model.getDeckCount() + 1) {
                    createDeck();
                } else if (choice > 0 && choice <= model.getDeckCount()) {
                    manageSingleDeck(choice - 1);
                } else {
                    view.displayError("Invalid choice.");
                }
            } else {
                exit = true;
            }
        }
    }

    /**
     * Manages a specific deck by index.
     *
     * @param deckIdx the index of the deck
     */
    private void manageSingleDeck(int deckIdx) {
        Deck deck = model.getDeck(deckIdx);
        boolean managing = true;

        while(managing) {
            view.displayManageSingleDeckMenu(deck.getName(),model.isSellableDeck(deck));
            int choice = view.getMenuChoice();

            switch (choice) {
                case 1 -> {
                    if(view.displayDeckCards(deck.getCards())) {
                        System.out.println("Enter num not in list to go back.");
                        int index = view.getCardChoice();

                        // Returns to Manage Decks menu if index is invalid
                        if(index >= 0 && index < deck.getCards().size()) {
                            view.displayDeckCardDetails(deck.getCards().get(index));
                        }
                    }
                }
                case 2 -> {
                    if(deck.getCardsCount() >= Deck.MAX_CARD_COUNT) {
                        System.out.println("Deck is full. Please select a different deck.");
                    } else {
                        List<Card> collection = model.getCardCollection();

                        // Only ask for card to select if list of cards isn't empty
                        if(view.displayCollection(collection)) {
                            System.out.println("Enter num not in list to go back.");
                            int index = view.getCardChoice();

                            // Returns to Manage Decks menu if index is invalid
                            if(index >= 0 && index < collection.size()) {
                                Card selected = model.getCardFromCollection(index);

                                if (model.isDeckCardDupe(deck, selected)) {
                                    view.displayError("Duplicate card.");
                                } else {
                                    if(model.addCardToDeck(deck, selected)) {
                                        System.out.println("Card added to deck.");
                                    }
                                }
                            }
                        }
                    }
                }
                case 3 -> {
                    List<Card> deckCards = deck.getCards();
                    view.displayDeckCards(deckCards);

                    if(!deckCards.isEmpty()){
                        System.out.println("Enter num not in list to go back.");
                        int index = view.getCardChoice();

                        // Returns to Manage Decks menu if index is invalid
                        if (index >= 0 && index < deckCards.size()) {
                            Card selected = deckCards.get(index);
                            model.removeCardFromDeck(deck, selected);

                            System.out.println("Card removed from deck.");
                        }
                    }
                }
                case 4 -> {
                    if(view.confirmAction("Delete this deck and return all its cards to collection?")) {
                        boolean deleted = model.deleteDeck(deck);

                        if(deleted) {
                            System.out.println("Deck deleted.");
                        } else {
                            view.displayError("Failed to delete deck.");
                        }

                        managing = false;
                    }
                }
                // New option 5: Sell Deck (only for sellable decks)
                case 5 -> {
                    if (model.isSellableDeck(deck)) {
                        sellDeck(deck);
                        managing = false; // Exit after selling
                    } else {
                        view.displayError("Invalid option.");
                    }
                }
                case 0 -> managing = false;
                default -> view.displayError("Invalid choice.");
            }
        }
    }

    /**
     * Prompts the user to adjust the count of a selected card.
     */
    private void adjustCardCount() {
        List<Card> cards = model.getCardCollection();

        view.displayCollection(cards);
        System.out.println("Enter num not in list to go back.");
        int index = view.selectCardFromList(cards);

        if(index >= 0) {
            Card card = cards.get(index);

            int adjustment = view.promptForCardAdjustment();

            if(adjustment != 0){
                if (model.adjustCardCount(card, adjustment)) {
                    System.out.println("Card count updated!");
                } else {
                    view.displayError("Invalid adjustment - count cannot become negative.");
                }
            }
        } else {
            view.returningToMainMenu();
        }
    }

    /**
     * Displays a single card or the entire collection, depending on user input.
     */
    private void displayCardOrCollection() {
        boolean exit = false;
        int choice;

        while(!exit) {
            view.displayCardOrCollectionMenu();
            choice = view.getMenuChoice();

            if(choice == 1) {
                view.displayCollection(model.getCardCollection());
                System.out.println("Enter num not in list to go back.");

                int index = view.getCardChoice();

                if(index >= 0 && index < model.getCardCollection().size()) {
                    view.displayCardDetails(model.getCardFromCollection(index));
                    exit = true;
                }
            } else if (choice == 2) {
                view.displayCollection(model.getCardCollection());
                exit = true;
            } else if (choice == 0) {
                view.returningToMainMenu();
                exit = true;
            } else {
                view.displayError("Invalid choice.");
            }
        }
    }

    /**
     * Handles selling a single card from collection
     */
    private void sellCards() {
        List<Card> collection = model.getCardCollection();
        if (view.displayCollection(collection)) {
            System.out.println("Enter card number to sell (0 to cancel):");
            int index = view.getCardChoice();

            if (index >= 0 && index < collection.size()) {
                Card card = collection.get(index);

                if(model.isSellableCard(card)){
                    if (view.confirmSale(card)) {
                        boolean success = model.sellCard(card);
                        view.displaySaleResult(success);
                    } else {
                        System.out.println("Sale cancelled");
                    }
                } else {
                    view.displayError("Cannot sell card in deck/binder");
                }
            }
        }
    }

    private void sellBinder(Binder binder) {
        if (!model.isSellableBinder(binder) || binder.isEmpty()) {
            view.displayError("This binder cannot be sold");
            return;
        }

        SellableBinder sellable = (SellableBinder) binder;

        // Special handling for Luxury Binder pricing
        if (binder instanceof LuxuryBinder) {
            LuxuryBinder luxury = (LuxuryBinder) binder;

            // Show current pricing
            BigDecimal currentValue = luxury.calculateBaseValue();
            System.out.printf("Current card value: $%.2f%n", currentValue);

            // Prompt for custom price
            if (view.confirmAction("Set custom price?")) {
                BigDecimal customPrice = view.promptForPrice("Enter custom price (minimum $" + currentValue + ")");

                if (!luxury.setCustomPrice(customPrice)) {
                    view.displayError("Price must be ≥ $" + currentValue);
                    return;
                }
            }
        }

        BigDecimal price = sellable.calculatePrice();
        System.out.printf("Sell '%s' for $%.2f ?%n",
                binder.getName(), price);

        if (view.confirmAction("Confirm sale")) {
            boolean success = model.sellBinder(binder);
            if (success) {
                System.out.println("Binder sold successfully!");
                System.out.printf("Earned: $%.2f", price);
            } else {
                view.displayError("Failed to complete sale");
            }
        }
    }

    // Add sell deck option
    private void sellDeck(Deck deck) {
        if (!model.isSellableDeck(deck)) {
            view.displayError("This deck type cannot be sold");
            return;
        }

        BigDecimal value = ((SellableDeck)deck).calculateValue();
        System.out.printf("Sell '%s' for $%.2f?%n", deck.getName(), value);

        if (view.confirmAction("Confirm sale")) {
            boolean success = model.sellDeck(deck);
            if (success) {
                System.out.println("Deck sold successfully!");
            } else {view.displayError("Cannot sell this deck");}
        }
    }
}