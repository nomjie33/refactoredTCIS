import javax.swing.event.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.List;

/**
     * Controller class for the Trading Card Inventory System.
     * Coordinates interactions between the model and the view.
     */
public class TradingCardInventorySystemController implements ActionListener, DocumentListener {
    private final TradingCardInventorySystemModel model;
    private final TradingCardInventorySystemView view;

    /**
     * Constructs a controller for the inventory system.
     *
     * @param model the data model
     * @param view  the user interface view
     */
    public TradingCardInventorySystemController(TradingCardInventorySystemModel model, TradingCardInventorySystemView view) {
        this.model = model;
        this.view = view;

        view.setActionListener(this);
        view.setDocumentListener(this);
    }
    /**
     * Handles all action events from the view components.
     * Routes different actions to appropriate handler methods based on the action command.
     *
     * @param ae the action event triggered by user interaction
     *
     * Supported action commands:
     * <ul>
     *   <li>Menu navigation commands ("Return to Main Menu", "Exit", etc.)</li>
     *   <li>Card operations ("Add Card", "SELL_CARD", "CONFIRM_ADD_CARD")</li>
     *   <li>Binder operations ("Create a new Binder", "CREATE_BASIC_BINDER", etc.)</li>
     *   <li>Deck operations ("Create a new Deck", "CREATE_NORMAL_DECK", etc.)</li>
     *   <li>Collection operations ("Adjust Card Count", "Display Card", "Display Collection")</li>
     * </ul>
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        switch (ae.getActionCommand()) {
            case "Return to Main Menu" -> view.displayMainMenu(model.hasCards(), model.hasBinders(), model.hasDecks());
            case "Exit" -> System.exit(0);

            case "Add Card" -> view.displayAddCardMenu();
            case "SELL_CARD" -> handleSellCard();
            case "CONFIRM_ADD_CARD" -> {
                try {
                    String cardName = view.getAddCardName();
                    CardRarity rarity = view.getAddCardRarity();
                    CardVariant variant = view.getAddCardVariant();
                    BigDecimal value = new BigDecimal(view.getAddCardValue());

                    if (cardName.isEmpty()) {
                        view.displayErrorMessage("Card name cannot be empty.");
                    } else if (value.compareTo(BigDecimal.ZERO) <= 0) {
                        view.displayErrorMessage("Card value must be greater than zero.");
                    } else {
                        Card existingCard = model.getCardFromCollection(cardName);

                        if (existingCard != null) {
                            if (view.confirmAction("This card already exists. Increase count instead?")) {
                                int increase = view.promptForCardAdjustmentGUI();

                                if (increase > 0) {
                                    model.adjustCardCount(existingCard, increase);
                                    view.displayMessage("Count increased by " + increase);
                                }
                            }
                        } else {
                            model.addCardToCollection(cardName, rarity, variant, value);
                            view.displayMessage("Card added successfully!");
                        }

                        view.displayMainMenu(model.hasCards(), model.hasBinders(), model.hasDecks());
                    }
                } catch (NumberFormatException e) {
                    view.displayErrorMessage("Invalid card value.");
                }

            }

            case "Create a new Binder" -> view.displayCreateBinderMenu();
            case "CREATE_BASIC_BINDER" -> createBinder(BinderType.BASIC);
            case "CREATE_PAUPER_BINDER" -> createBinder(BinderType.PAUPER);
            case "CREATE_RARES_BINDER" -> createBinder(BinderType.RARES);
            case "CREATE_LUXURY_BINDER" -> createBinder(BinderType.LUXURY);
            case "CREATE_COLLECTOR_BINDER" -> createBinder(BinderType.COLLECTOR);
            case "Manage Binders" -> view.displayManageBindersMenu(model.getBinders());
            case "Select Binder" -> {
                int index = view.getSelectedBinderIndex();

                if (index >= 0) {
                    Binder binder = model.getBinder(index);

                    view.updateBinderCardsList(binder.getCards());
                    view.displaySingleBinderMenu(binder.getName(), model.isSellableBinder(binder));
                }
            }
            case "Return to Binders" -> view.displayManageBindersMenu(model.getBinders());
            case "ADD_CARD_TO_BINDER" -> {
                Binder binder = model.getBinder(view.getCurrentBinderName());

                if(model.getCardCollection().isEmpty()) {
                    view.displayErrorMessage("No cards in collection to add");
                    return;
                } else if(binder.getCardsCount() >= Binder.MAX_CARD_COUNT) {
                    view.displayErrorMessage("Binder is at max capacity.");
                    return;
                }

                Card selectedCard = view.showCardSelectionDialog(model.getCardCollection());

                if(selectedCard != null) {
                    if(model.addCardToBinder(binder, selectedCard)) {
                        view.updateBinderCardsList(binder.getCards());
                        view.displayMessage("Card added to binder.");
                    } else {
                        view.displayErrorMessage("Cannot add card - invalid type or count.");
                    }
                }
            }
            case "REMOVE_CARD_FROM_BINDER" -> {
                String binderName = view.getCurrentBinderName();

                if (model.getBinder(binderName).isEmpty()) {
                    view.displayErrorMessage("No cards in binder to remove.");
                    return;
                }
                Binder binder = model.getBinder(binderName);
                int selectedIndex = view.getSelectedBinderCardIndex();

                if (selectedIndex >= 0 && model.removeCardFromBinder(binder,
                        binder.getCards().get(selectedIndex))) {
                    view.updateBinderCardsList(binder.getCards());
                    view.displayMessage("Card removed.");
                }
            }
            case "TRADE_OR_SELL_BINDER" -> {
                String binderName = view.getCurrentBinderName();
                Binder binder = model.getBinder(binderName);

                if (model.isSellableBinder(binder)) {
                    handleSellBinder(binder);
                } else {
                    handleTradeCard(binder);
                }
            }
            case "DELETE_BINDER" -> {
                String binderName = view.getCurrentBinderName();
                Binder binder = model.getBinder(binderName);

                if (view.confirmAction("Delete binder and return cards to collection?")) {
                    if (model.deleteBinder(binder)) {
                        view.displayMessage("Binder deleted.");
                        view.displayManageBindersMenu(model.getBinders());
                    }
                }
            }
            case "VIEW_BINDER_CARD" -> {
                String binderName = view.getCurrentBinderName();

                if (model.getBinder(binderName).isEmpty()) {
                    view.displayErrorMessage("No cards in binder to view.");
                    return;
                }

                int selectedIndex = view.getSelectedBinderCardIndex();

                if (selectedIndex >= 0) {
                    Binder binder = model.getBinder(view.getCurrentBinderName());
                    Card selectedCard = binder.getCards().get(selectedIndex);
                    view.displayCardDetails(selectedCard);
                } else {
                    view.displayErrorMessage("No card selected.");
                }
            }

            case "Create a new Deck" -> view.displayCreateDeckMenu();
            case "CREATE_NORMAL_DECK" -> createDeck(DeckType.NORMAL);
            case "CREATE_SELLABLE_DECK" -> createDeck(DeckType.SELLABLE);
            case "Manage Decks" -> view.displayManageDecksMenu(model.getDecks());
            case "Select Deck" -> {
                int index = view.getSelectedDeckIndex();

                if (index >= 0) {
                    Deck deck = model.getDeck(index);

                    view.updateDeckCardsList(deck.getCards());
                    view.displaySingleDeckMenu(deck.getName(), model.isSellableDeck(deck));
                }
            }
            case "Return to Decks" -> view.displayManageDecksMenu(model.getDecks());
            case "ADD_CARD_TO_DECK" -> {
                Deck deck = model.getDeck(view.getCurrentDeckName());

                if(model.getCardCollection().isEmpty()) {
                    view.displayErrorMessage("No cards in collection to add.");
                    return;
                } else if(deck.getCardsCount() >= Deck.MAX_CARD_COUNT) {
                    view.displayErrorMessage("Deck is at max capacity.");
                    return;
                }

                Card selectedCard = view.showCardSelectionDialog(model.getCardCollection());

                if (selectedCard != null) {
                    if (model.addCardToDeck(deck, selectedCard)) {
                        view.updateDeckCardsList(deck.getCards());
                        view.displayMessage("Card added to deck.");
                    } else {
                        view.displayErrorMessage("Cannot add card - invalid type or count.");
                    }
                }
            }
            case "REMOVE_CARD_FROM_DECK" -> {
                String deckName = view.getCurrentDeckName();

                if (model.getDeck(deckName).isEmpty()) {
                    view.displayErrorMessage("No cards in deck to remove.");
                    return;
                }

                Deck deck = model.getDeck(deckName);
                int selectedIndex = view.getSelectedDeckCardIndex();

                if (selectedIndex >= 0 && model.removeCardFromDeck(deck,
                       deck.getCards().get(selectedIndex))) {
                    view.updateDeckCardsList(deck.getCards());
                    view.displayMessage("Card removed.");
                }
            }
            case "SELL_DECK" -> {
                Deck deck = model.getDeck(view.getCurrentDeckName());

                if (model.isSellableDeck(deck)) {
                    handleSellDeck(deck);
                }
            }
            case "DELETE_DECK" -> {
                Deck deck = model.getDeck(view.getCurrentDeckName());

                if (view.confirmAction("Delete deck and return cards to collection?")) {
                    if (model.deleteDeck(deck)) {
                        view.displayMessage("Deck deleted.");
                        view.displayManageDecksMenu(model.getDecks());
                    }
                }
            }
            case "VIEW_DECK_CARD" -> {
                String deckName = view.getCurrentDeckName();

                if (model.getDeck(deckName).isEmpty()) {
                    view.displayErrorMessage("No cards in deck to view.");
                    return;
                }

                int selectedIndex = view.getSelectedDeckCardIndex();

                if (selectedIndex >= 0) {
                    Deck deck = model.getDeck(deckName);
                    Card selectedCard = deck.getCards().get(selectedIndex);

                    view.displayCardDetails(selectedCard);
                } else {
                    view.displayErrorMessage("No card selected.");
                }
            }

            case "Adjust Card Count" -> view.displayAdjustCardCountMenu(model.getCardCollection());
            case "CONFIRM_ADJUST_CARD_COUNT" -> {
                try {
                    int cardCount = Integer.parseInt(view.getAdjustCardCountNumber());
                    Card card = model.getCardFromCollection(view.getAdjustCardCountName());

                    if(model.adjustCardCount(card, cardCount)) {
                        view.displayMessage("Card count adjusted successfully!");
                        view.displayMainMenu(model.hasCards(), model.hasBinders(), model.hasDecks());
                    } else {
                        view.displayErrorMessage("Adjustment cannot set card count below zero.");
                    }
                } catch(NumberFormatException e) {
                    view.displayErrorMessage("Invalid card count adjustment value.");
                }
            }

            case "Display Card" -> view.displayCardDetailsMenu(model.getCardCollection());
            case "Display Collection" -> view.displayCollectionGUI(model.getCardCollection());
        }
    }

    @Override
    public void insertUpdate(DocumentEvent de) {

    }

    @Override
    public void removeUpdate(DocumentEvent de) {

    }

    @Override
    public void changedUpdate(DocumentEvent de) {

    }
    /**
     * Creates a new binder of specified type after name validation.
     * @param type the type of binder to create
     */
    private void createBinder(BinderType type) {
        String name = view.promptForBinderName();

        if (!name.isEmpty()) {
            if (model.createBinder(name, type)) {
                view.displayManageBindersMenu(model.getBinders());
            } else {
                view.displayErrorMessage("That binder already exists.");
            }
        }
    }
    /**
     * Handles selling a binder with price validation and confirmation.
     * @param binder the binder to sell
     */
    private void handleSellBinder(Binder binder) {
        // Validate binder can be sold
        if (!model.isSellableBinder(binder)) {
            view.displayErrorMessage("This binder type cannot be sold.");
            return;
        }

        if (binder.getCards().isEmpty()) {
            view.displayErrorMessage("Cannot sell empty binder.");
            return;
        }

        // Calculate base price
        BigDecimal price = ((SellableBinder) binder).calculateValue();

        // Handle luxury binder custom pricing
        if (binder instanceof LuxuryBinder) {
            LuxuryBinder luxury = (LuxuryBinder) binder;
            BigDecimal baseValue = luxury.calculateBaseValue();

            if (view.confirmAction("Set custom price? (Base value: $" + baseValue + ")")) {
                String input = view.promptForCustomPrice(baseValue);

                // Validate custom price input
                if (input == null || input.trim().isEmpty()) {
                    return; // User cancelled
                }

                try {
                    BigDecimal customPrice = new BigDecimal(input);
                    if (customPrice.compareTo(baseValue) >= 0) {
                        luxury.setCustomPrice(customPrice);
                        price = customPrice;
                    } else {
                        view.displayErrorMessage("Price must be at least $" + baseValue);
                        return;
                    }
                } catch (NumberFormatException e) {
                    view.displayErrorMessage("Invalid price format");
                    return;
                }
            }
        }

        // Confirm final sale
        if (view.confirmAction("Sell '" + binder.getName() + "' for $" + price + "?")) {
            if (model.sellBinder(binder)) {
                view.setCollectorMoneyLabel(model.getCollectorMoney());
                view.displayMessage("Binder sold for $" + price);

                view.displayManageBindersMenu(model.getBinders());
            } else {
                view.displayErrorMessage("Failed to complete sale");
            }
        }
    }
    /**
     * Handles trading a card from binder after value difference check.
     * @param binder the binder containing card to trade
     */
    private void handleTradeCard(Binder binder) {
        int selectedIndex = view.getSelectedBinderCardIndex();
        if (selectedIndex < 0) {
            view.displayErrorMessage("No card selected");
            return;
        }

        Card outgoingCard = binder.getCards().get(selectedIndex);
        Card incomingCard = view.showTradeCardDialog();

        if (incomingCard != null) {
            BigDecimal difference = incomingCard.getValue().subtract(outgoingCard.getValue()).abs();

            if (difference.compareTo(new BigDecimal("1.00")) >= 0) {
                if (!view.confirmAction("Value difference is $" + difference + ". Proceed?")) {
                    return;
                }
            }

            if (model.executeTrade(binder, incomingCard, outgoingCard)) {
                view.updateBinderCardsList(binder.getCards());
                view.displayMessage("Trade successful!");
            } else {
                view.displayErrorMessage("Trade failed. Check binder requirements.");
            }
        }
    }
    /**
     * Creates new deck of specified type after name validation.
     * @param type the type of deck to create
     */
    private void createDeck(DeckType type) {
        String name = view.promptForDeckName();

        if (!name.isEmpty()) {
            if (model.createDeck(name, type)) {
                view.displayManageDecksMenu(model.getDecks());
            } else {
                view.displayErrorMessage("That deck already exists.");
            }
        }
    }
    /**
     * Handles the logic for selling a deck. Validates that the given deck is sellable and not empty,
     * calculates its value, and prompts the user for confirmation. If confirmed, attempts the sale
     * and updates the collector's money and binder list in the view.
     *
     * @param deck the {@code Deck} object to be sold
     */
    private void handleSellDeck(Deck deck) {
        // Validate deck can be sold
        if (!model.isSellableDeck(deck)) {
            view.displayErrorMessage("This deck type cannot be sold.");
            return;
        }

        if (deck.getCards().isEmpty()) {
            view.displayErrorMessage("Cannot sell empty deck.");
            return;
        }

        // Calculate base price
        BigDecimal price = ((SellableDeck) deck).calculateValue();

        // Confirm final sale
        if (view.confirmAction("Sell '" + deck.getName() + "' for $" + price + "?")) {
            if (model.sellDeck(deck)) {
                view.setCollectorMoneyLabel(model.getCollectorMoney());
                view.displayMessage("Deck sold for $" + price);

                view.displayManageDecksMenu(model.getDecks());
            } else {
                view.displayErrorMessage("Failed to complete sale");
            }
        }
    }
    /**
     * Handles selling individual card with availability checks.
     */
    private void handleSellCard() {
        List<Card> collection = model.getCardCollection();

        if (collection.isEmpty()) {
            view.displayErrorMessage("No cards in collection to sell.");
            return;
        }

        Card selectedCard = view.showCardSelectionDialog(collection);

        if (selectedCard == null) {
            return;
        }

        if (!model.isSellableCard(selectedCard)) {
            view.displayErrorMessage("Cannot sell card - it's in a binder or deck.");
            return;
        }

        if (!view.confirmAction("Sell 1 " + selectedCard.getName() + " for $" + selectedCard.getValue() + "?")) {
            return;
        }

        if (model.sellCard(selectedCard)) {
            view.setCollectorMoneyLabel(model.getCollectorMoney());

            view.displayMessage("Card sold successfully!");
        } else {
            view.displayErrorMessage("Failed to sell card.");
        }
    }
    /**
     * Starts the main program and displays the menu system.
     */
    public void startProgram() {
        view.displayMainMenu(model.hasCards(), model.hasBinders(), model.hasDecks());
    }
}