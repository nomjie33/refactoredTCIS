import java.math.BigDecimal;
import java.util.*;
/**
 * The model component of the Trading Card Inventory System. Manages the core
 * data structures and the logic for a trading card inventory, including
 * the card collection, binders, and decks.
 */

public class TradingCardInventorySystemModel {
    private final ArrayList<Card> cardCollection;
    private final Map<String, Binder> binders;
    private final Map<String, Deck> decks;
    private BigDecimal collectorMoney = BigDecimal.ZERO;

    /**
     * Constructs a new TradingCardInventorySystemModel with empty card collection,
     * binders, and decks
     */
    public TradingCardInventorySystemModel() {
        this.cardCollection = new ArrayList<>();
        this.binders = new LinkedHashMap<>();
        this.decks = new LinkedHashMap<>();
    }
    /**
     * Checks if card collection has any cards.
     *
     * @return true if card collection is not empty, false otherwise
     */
    public boolean hasCards() {
        return !cardCollection.isEmpty();
    }
    /**
     * Checks if any binders exist.
     *
     * @return true if there are binders, false otherwise
     */
    public boolean hasBinders() {
        return !binders.isEmpty();
    }
    /**
     * Checks if any decks exist.
     *
     * @return true if there are decks, false otherwise
     */
    public boolean hasDecks() {
        return !decks.isEmpty();
    }

    /**
     * Adds a new card to the collection or increments its count if it already exists.
     *
     * @param name The name of the card.
     * @param rarity The rarity of the card.
     * @param variant The variant of the card.
     * @param value The monetary value of the card.
     */
    public void addCardToCollection(String name, CardRarity rarity, CardVariant variant, BigDecimal value) {
        Card card = new Card(name, rarity, variant, value, 1);

        if(cardCollection.contains(card)) {
            getCardFromCollection(card.getName()).setCount(card.getCount() + 1);
        } else {
            cardCollection.add(card);
        }
    }
    /**
     * Adds a card object to the collection or increments its count.
     *
     * @param card The card to add.
     */
    public void addCardToCollection(Card card) {
        if(cardCollection.contains(card)) {
            getCardFromCollection(card.getName()).setCount(card.getCount() + 1);
        } else {
            cardCollection.add(card);
        }
    }
    /**
     * Adjusts the count of a specific card.
     *
     * @param card The card to update.
     * @param adjustment The amount to adjust the count by.
     * @return true if adjustment is successful, false otherwise.
     */
    public boolean adjustCardCount(Card card, int adjustment) {
        int newCount = card.getCount() + adjustment;

        if (newCount >= 0) {
            card.setCount(newCount);
            return true;
        }

        return false;
    }
    /**
     * Returns the list of all cards in the collection.
     *
     * @return A list of cards.
     */
    public List<Card> getCardCollection() {
        return new ArrayList<>(cardCollection);
    }
    /**
     * Retrieves a card by its index in the collection.
     *
     * @param index The index of the card.
     * @return The card if found, null otherwise.
     */
    public Card getCardFromCollection(int index) {
        if(index >= 0 && index < cardCollection.size()) {
            return cardCollection.get(index);
        } else {
            return null;
        }
    }
    /**
     * Retrieves a card by name.
     *
     * @param name The name of the card.
     * @return The card if found, null otherwise.
     */
    public Card getCardFromCollection(String name){
        for(Card card : cardCollection){
            if(card.getName().equalsIgnoreCase(name)){
                return card;
            }
        }

        return null;
    }
    /**
     * Creates a new binder with the given name.
     *
     * @param name The name of the binder.
     */
    // Update createBinder to use subclass instances directly
    public boolean createBinder(String name, int binderType) {
        if (binders.containsKey(name)) {
            return false;
        }

        switch(binderType) {
            case 1:
                binders.put(name, new NonCuratedBinder(name));
                break;
            case 2:
                binders.put(name, new PauperBinder(name));
                break;
            case 3:
                binders.put(name, new RaresBinder(name));
                break;
            case 4:
                binders.put(name, new LuxuryBinder(name));
                break;
            case 5:
                binders.put(name, new CollectorBinder(name));
                break;
            default:
                return false;
        }
        return true;
    }
    /**
     * Retrieves a binder by index.
     *
     * @param index The index of the binder.
     * @return The binder if found, null otherwise.
     */
    public Binder getBinder(int index) {
        if(index >= 0 && index < binders.size()) {
            return binders.get(getBinderNames().get(index));
        } else {
            return null;
        }
    }

    /**
     * Returns a list of all binder names.
     *
     * @return A list of binder names.
     */
    public List<String> getBinderNames() {
        return new ArrayList<>(binders.keySet());
    }
    /**
     * Gets total number of binders.
     *
     * @return The number of binders.
     */
    public int getBinderCount() {
        return binders.size();
    }
    /**
     * Deletes a binder and returns its cards to the collection
     *
     * @param binder The binder to delete.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteBinder(Binder binder) {
        List<Card> binderCards = new ArrayList<>(binder.getCards());

        for(Card card : binderCards) {
            removeCardFromBinder(binder, card);
        }

        return binders.remove(binder.getName()) != null;
    }
    /**
     * Adds a card to the specified binder if it is available in the collection.
     *
     * @param binder The binder to add card to.
     * @return true if card was added successfully, false otherwise.
     */
    public boolean addCardToBinder(Binder binder, Card card) {
        // Existing count check
        if (card.getCount() <= 0) {
            return false;
        }

        // Delegate validation to binder subclass
        if (!binder.addCard(card)) {
            return false;
        }

        // Deduct from collection
        cardCollection.get(cardCollection.indexOf(card)).setCount(
                cardCollection.get(cardCollection.indexOf(card)).getCount() - 1);
        return true;
    }
    /**
     * Removes card from the specified binder and returns it to the collection.
     * Checks first if the collection has the card; if the collection does,
     * then it increments the count of the card in the collection instead.
     *
     * @param binder The binder to remove card from.
     * @param card The card to remove in binder.
     */
    public void removeCardFromBinder(Binder binder, Card card) {
        binder.removeCard(card);

        if(cardCollection.contains(card)) {
            cardCollection.get(cardCollection.indexOf(card)).setCount
                    (cardCollection.get(cardCollection.indexOf(card)).getCount() + 1);
        } else {
            card.setCount(1);
            cardCollection.add(card);
        }
    }
    /**
     * Executes a trade by swapping an outgoing card with an incoming card in the binder.
     * The incoming card will also be added to the collection with a count of 0;
     * essentially, the incoming card gets its own new "slot" in the collection.
     *
     * @param binder The binder where the trade occurs.
     * @param incoming The card to be added, assumed to be outside the binder and collection
     * @param outgoing The card to be removed.
     * @return true if the trade was successful, false otherwise.
     */
    public boolean executeTrade(Binder binder, Card incoming, Card outgoing) {
        if(!binder.containsCard(outgoing)) {
            return false;
        }

        incoming.setCount(0);
        addCardToCollection(incoming);
        binder.addCard(incoming);
        binder.removeCard(outgoing);

        return true;
    }


    /**
     * Creates a new deck with the given name.
     *
     * @param name The name of the deck.
     */
    public void createDeck(String name, boolean sellable) {
        if (sellable) {
            decks.put(name, new SellableDeck(name));
        } else {
            decks.put(name, new NormalDeck(name));
        }
    }
    /**
     * Retrieves a deck by index
     *
     * @param index The name of the deck.
     * @return The deck if found, null otherwise.
     */
    public Deck getDeck(int index) {
        if(index >= 0 && index < decks.size()) {
            return decks.get(getDeckNames().get(index));
        } else {
            return null;
        }
    }
    /**
     * Returns a list of all deck names
     *
     * @return A list of deck names
     */
    public List<String> getDeckNames() {
        return new ArrayList<>(decks.keySet());
    }
    /**
     * Gets the total number of decks.
     *
     * @return The number of decks
     */
    public int getDeckCount() {
        return decks.size();
    }
    /**
     * Checks if a card already exists in the deck.
     *
     * @param deck The deck to check.
     * @param card The card to look for.
     * @return true if the card is already in the deck, false otherwise.
     */
    public boolean isDeckCardDupe(Deck deck, Card card) {
        return deck.getCards().contains(card);
    }
    /**
     * Deletes a deck and returns its cards to the collection.
     *
     * @param deck The deck to delete.
     * @return true if deletion was successful, false otherwise.
     */
    public boolean deleteDeck(Deck deck) {
        List<Card> cardsInDeck = new ArrayList<>(deck.getCards());

        for(Card card : cardsInDeck) {
            removeCardFromDeck(deck, card);
        }

        return decks.remove(deck.getName()) != null;
    }
    /**
     * Adds a card to the specified deck if it is not already present
     * and if it is available in the collection.
     *
     * @param deck The deck to add the card to.
     * @param card The card to add.
     * @return true if the card was added successfully, false otherwise.
     */
    public boolean addCardToDeck(Deck deck, Card card) {
        if(card.getCount() <= 0) {
            System.err.println("Card count = 0, cannot add card.");
            return false;
        }

        if(isDeckCardDupe(deck, card)) {
            System.out.println("Card is already in the deck.");
            return false;
        }

        getCardFromCollection(card.getName()).setCount
                (getCardFromCollection(card.getName()).getCount() - 1);
        deck.addCard(card);

        return true;
    }
    /**
     * Removes a card from the deck and returns it to the collection.
     * Checks first if the collection has the card; if the collection does,
     * then it increments the count of the card in the collection instead.
     *
     * @param deck The deck to remove the card from.
     * @param card The card to remove.
     */
    public void removeCardFromDeck(Deck deck, Card card) {
        deck.removeCard(card);

        if(cardCollection.contains(card)) {
            cardCollection.get(cardCollection.indexOf(card)).setCount
                    (cardCollection.get(cardCollection.indexOf(card)).getCount() + 1);
        } else {
            card.setCount(1);
            cardCollection.add(card);
        }
    }
    /**
     * Gets the collector's current money (for future GUI use)
     * @return current money amount
     */
    public BigDecimal getCollectorMoney() {
        return collectorMoney;
    }
    /**
     * Adds money to collector's balance
     * @param amount amount to add (must be positive)
     */
    private void addMoney(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            collectorMoney = collectorMoney.add(amount);
        }
    }
    /**
     * Sells one copy of a card from the collection
     * @param card the card to sell
     * @return true if sale was successful
     */
    public boolean sellCard(Card card) {
        if (cardCollection.contains(card)) {
            Card collectionCard = cardCollection.get(cardCollection.indexOf(card));
            if (collectionCard.getCount() > 0) {
                addMoney(collectionCard.getValue()); // Add card value to collector's money
                collectionCard.setCount(collectionCard.getCount() - 1);
                if (collectionCard.getCount() == 0) {
                    cardCollection.remove(collectionCard);
                }
                return true;
            }
        }
        return false;
    }

    public boolean isSellableCard(Card card){
        if(card.getCount() > 0) {
            return true;
        }
        return false;
    }

    public boolean isSellableBinder(Binder binder) {
        // Only check the binder type, not emptiness
        return binder.isSellable();
    }

    // Add method to sell binder
    /**
     * Sells a binder and removes its cards from collection if count is zero
     * @param binder The binder to sell
     * @return true if sale was successful
     */
    public boolean sellBinder(Binder binder) {
        if (!(binder instanceof SellableBinder)) {
            return false;
        }

        SellableBinder sellable = (SellableBinder) binder;
        BigDecimal price = sellable.calculatePrice();

        // Process each card in the binder
        new ArrayList<>(binder.getCards()).forEach(card -> {
            // Remove from binder first
            binder.removeCard(card);

            // Check if card exists in collection
            if (cardCollection.contains(card)) {
                Card collectionCard = cardCollection.get(cardCollection.indexOf(card));

                // Only remove from collection if count would reach zero
                if (collectionCard.getCount() == 0) {
                    cardCollection.remove(collectionCard);
                }
            }
        });

        // Complete the sale
        collectorMoney = collectorMoney.add(price);
        binders.remove(binder.getName());
        return true;
    }

    // Add deck selling logic
    public boolean sellDeck(Deck deck) {
        if (!deck.isSellable() || ((SellableDeck) deck).calculateValue().compareTo(BigDecimal.ZERO) <= 0) return false;

        BigDecimal value = ((SellableDeck)deck).calculateValue();
        collectorMoney = collectorMoney.add(value);

        // Remove cards (same logic as binder selling)
        new ArrayList<>(deck.getCards()).forEach(card -> {
            deck.removeCard(card);
            if (cardCollection.contains(card)) {
                Card collectionCard = cardCollection.get(cardCollection.indexOf(card));
                if (collectionCard.getCount() == 1) {
                    cardCollection.remove(collectionCard);
                } else {
                    collectionCard.setCount(collectionCard.getCount() - 1);
                }
            }
        });

        decks.remove(deck.getName());
        return true;
    }

    public boolean isSellableDeck(Deck deck) {
        return deck.isSellable() && !deck.getCards().isEmpty();
    }

}