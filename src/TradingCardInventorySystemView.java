import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The view component of the Trading Card Inventory System.
 * Handles all user interface interactions, such as prompting inputs,
 * displaying menus and collections, and confirming actions.
 */
public class TradingCardInventorySystemView {
    private final Scanner sc;

    /**
     * Constructs the view and initializes the scanner.
     */
    public TradingCardInventorySystemView() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Prompts user to enter a menu choice.
     *
     * @return user's chosen menu number
     */
    public int getMenuChoice() {
        System.out.print("Enter the no. of your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        return choice;
    }
    /**
     * Prompts user to select a card by number.
     *
     * @return index of selected card (0-based)
     */
    public int getCardChoice() {
        System.out.print("Enter card number: ");
        int choice = sc.nextInt();
        sc.nextLine();

        return choice - 1;
    }
    /**
     * Prompts user to select a card from a list.
     *
     * @param cards list of cards to choose from
     * @return index of chosen card (0-based) or -1 if invalid
     */
    public int selectCardFromList(List<Card> cards) {
        System.out.print("Select a card (number): ");
        try {
            int choice = Integer.parseInt(sc.nextLine());
            if (choice > 0 && choice <= cards.size()) {
                return choice - 1;
            }
        } catch (NumberFormatException e) {
            return -1;
        }

        return -1;
    }
    /**
     * Asks user to confirm an action.
     *
     * @param prompt message to show the user
     * @return true if user confirms, false otherwise
     */
    public boolean confirmAction(String prompt) {
        System.out.print(prompt + " (yes/no): ");
        String response = sc.nextLine().trim().toLowerCase();

        return response.equals("yes") || response.equals("y");
    }
    public void returningToMainMenu() {
        System.out.println("Returning to main menu.");
    }
    public void pause() {
        System.out.print("\nPress Enter to continue...");
        sc.nextLine();
    }
    public void displayError(String error) {
        System.err.println("Error: " + error);
    }
    public void closeScanner() {
        sc.close();
    }


    /**
     * Displays the main menu based on existing system state.
     *
     * @param hasCards whether the collection has cards
     * @param hasBinders whether any binders exist
     * @param hasDecks whether any decks exist
     */
    public void displayMainMenu(boolean hasCards, boolean hasBinders, boolean hasDecks) {
        System.out.println("\n=== Trading Card Inventory System ===");
        System.out.println("1. Add a Card");

        if(hasBinders) {
            System.out.println("2. Manage Binders");
        } else {
            System.out.println("2. Create a new Binder");
        }

        if(hasDecks) {
            System.out.println("3. Manage Decks");
        } else {
            System.out.println("3. Create a new Deck");
        }

        if(hasCards) {
            System.out.println("4. Adjust Card Count");
            System.out.println("5. Display a Card/Display Collection");
            System.out.println("6. Sell Card");
        }

        System.out.println("0. Exit\n");
    }


    /**
     * Prompts the user to input a card name.
     *
     * @return trimmed name, or empty string to cancel
     */
    public String promptForCardName() {
        System.out.println("\n=== Add New Card ===");
        System.out.print("Enter card name (press enter only to return to main menu): ");

        return sc.nextLine().trim();
    }
    /**
     * Prompts the user to select a card rarity.
     *
     * @return selected CardRarity or null to go back
     */
    public CardRarity promptForCardRarity() {
        int index = 1;
        int choice = -1;
        CardRarity[] values = CardRarity.values();

        System.out.println("\nSelect card rarity:");

        for (CardRarity rarity : CardRarity.values()) {
            System.out.printf("%d. %s%n", index++, rarity.getName());
        }

        System.out.println("0. Back\n");
        System.out.print("Enter choice: ");

        do {
            try {
                choice = Integer.parseInt(sc.nextLine());

                if(choice == 0){
                    return null;
                }

                if(choice > 0 && choice <= values.length) {
                    return values[choice - 1];
                } else {
                    System.out.print("Invalid choice, try again.  ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid choice, try again.  ");
            }
        } while(choice < 0 || choice > 4);

        return CardRarity.COMMON;
    }
    /**
     * Prompts for a variant based on rarity.
     *
     * @param rarity selected card rarity
     * @return selected CardVariant or null to cancel
     */
    public CardVariant promptForCardVariant(CardRarity rarity) {
        System.out.println("\nSelect card variant:");
        List<CardVariant> availableVariants = new ArrayList<>();
        int choice = -1;

        if(!(rarity == CardRarity.RARE || rarity == CardRarity.LEGENDARY)) {
            System.out.println("1. " + CardVariant.NORMAL.getName());
            System.out.println("Note: Only Normal variant available for this rarity.");

            return CardVariant.NORMAL;
        }

        int index = 1;

        for(CardVariant variant : CardVariant.values()) {
            System.out.printf("%d. %s%n", index++, variant.getName());
            availableVariants.add(variant);
        }

        System.out.println("0. Back\n");
        System.out.print("Enter choice: ");

        do {
            try {
                choice = Integer.parseInt(sc.nextLine());

                if(choice == 0) {
                    return null;
                }

                if(choice > 0 && choice <= availableVariants.size()) {
                    return availableVariants.get(choice - 1);
                } else {
                    System.out.print("Invalid selection, try again.  ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid selection, try again.  ");
            }
        } while(choice < 0 || choice > availableVariants.size());

        return CardVariant.NORMAL;
    }
    /**
     * Prompts the user to enter a monetary value.
     *
     * @return BigDecimal value or null if invalid or 0
     */
    public BigDecimal promptForCardValue() {
        System.out.print("\nEnter card value (or a value <= 0 to return to main menu): ");

        try {
            BigDecimal value = new BigDecimal(sc.nextLine().trim());

            if(value.compareTo(BigDecimal.ZERO) <= 0) {
                return null; //return to main menu
            }

            return value;
        } catch (NumberFormatException e) {
            System.out.println("Invalid value. Using $0.00");
            return BigDecimal.ZERO;
        }
    }


    /**
     * Prompts user to adjust a card's count.
     *
     * @return adjustment value (positive or negative)
     */
    public int promptForCardAdjustment() {
        System.out.print("Enter count adjustment (+/-) or 0 to return to main menu: ");
        try {
            int adjustment = Integer.parseInt(sc.nextLine());
            if (adjustment == 0) {
                System.out.println("Returning to main menu...");
                return 0;
            }
            return adjustment;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. No adjustment made.");
            return 0;
        }
    }


    /**
     * Prompts the user to input a binder name.
     *
     * @return name entered, or empty string to cancel
     */
    public String promptForBinderName() {
        System.out.print("Enter binder name (press enter only to return to main menu): ");
        return sc.nextLine().trim();
    }

    public void displayManageBindersMenu(List<String> binderNames) {
        System.out.println("\n=== Manage Binders ===");// displays all existing binders

        for(int i = 0; i < binderNames.size(); i++) {
            System.out.println((i + 1) + ". " + binderNames.get(i));
        }

        System.out.println((binderNames.size() + 1) + ". Create a new Binder");
        System.out.println("0. Return to Main Menu\n");
    }

    // Update display method to show sell option only for sellable binders
    public void displayManageSingleBinderMenu(String binderName, boolean isSellable) {
        System.out.println("\n=== Binder: " + binderName + " ===");
        System.out.println("1. View Cards");
        System.out.println("2. Add Card");
        System.out.println("3. Remove Card");
        System.out.println("4. Trade Card");
        if(isSellable) {
            System.out.println("5. Sell Binder");
            System.out.println("6. Delete Binder");
        } else {
            System.out.println("5. Delete Binder");
        }
        System.out.println("0. Back\n");
    }
    /**
     * Displays cards inside a binder.
     *
     * @param cards list of cards in binder
     * @return true if cards exist, false if empty
     */
    public boolean displayBinderCards(List<Card> cards) {
        if(cards.isEmpty()){
            System.out.println("Binder is empty");
            return false;
        }
        System.out.println("\nCards in binder:");

        for(int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            System.out.printf("%d. %s [%s] (%s) - $%.2f%n",
                    i + 1, card.getName(), card.getRarity().getName(),
                    card.getVariant().getName(), card.getValue());
        }
        return true;
    }
    /**
     * Prompts the user for new card details.
     *
     * @return newly created card, or null if cancelled
     */
    public Card promptNewCardDetails() {
        System.out.println("\nEnter new card details:");

        // User can exit anytime while entering card details
        String name = promptForCardName();
        if(name.isEmpty()) {
            return null;
        }

        CardRarity rarity = promptForCardRarity();
        if(rarity == null) {
            return null;
        }

        CardVariant variant = promptForCardVariant(rarity);
        if(variant == null) {
            return null;
        }

        BigDecimal value = promptForCardValue();
        if(value == null) {
            return null;
        }

        return new Card(name, rarity, variant, value, 1);
    }
    /**
     * Confirms trades with a large value difference.
     *
     * @param incoming new card
     * @param outgoing existing card to trade
     * @return true if user confirms, false otherwise
     */
    public boolean confirmBigDiffTrade(Card incoming, Card outgoing) {
        BigDecimal diff = incoming.getValue().subtract(outgoing.getValue()).abs();

        if(diff.compareTo(BigDecimal.ONE) >= 0){
            System.out.printf("Warning: Value difference is $%.2f\n", diff);
            return confirmAction("Proceed with trade?");
        }

        return true;
    }


    /**
     * Prompts the user for deck name.
     *
     * @return deck name
     */
    public String promptForDeckName() {
        System.out.print("Enter deck name (press enter only to go back): ");
        return sc.nextLine().trim();
    }

    public void displayManageDecksMenu(List<String> deckNames) {
        System.out.println("\n=== Manage Decks ===");

        for(int i = 0; i < deckNames.size(); i++) {
            System.out.println((i + 1) + ". " + deckNames.get(i));
        }

        System.out.println((deckNames.size() + 1) + ". Create a new Deck");
        System.out.println("0. Return to Main Menu\n");
    }
    public void displayManageSingleDeckMenu(String deckName) {
        System.out.println("\n=== Deck: " + deckName + " ===");
        System.out.println("1. View Cards");
        System.out.println("2. Add Card");
        System.out.println("3. Remove Card");
        System.out.println("4. Delete Deck");
        System.out.println("0. Back\n");
    }
    /**
     * Displays all cards inside a deck.
     *
     * @param cards list of cards
     * @return true if deck has cards
     */
    public boolean displayDeckCards(List<Card> cards) {
        if(cards.isEmpty()) {
            System.out.println("Deck is empty.");
            return false;
        }

        System.out.println("\nCards in deck:");

        for(int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            System.out.printf("%d. %s [%s] (%s) - $%.2f%n",
                    i + 1, card.getName(), card.getRarity().getName(),
                    card.getVariant().getName(), card.getValue());
        }

        return true;
    }
    public void displayDeckCardDetails(Card card) {
        System.out.println("\n--- Card Details ---");
        System.out.println("Name: " + card.getName());
        System.out.println("Rarity: " + card.getRarity().getName());
        System.out.println("Variant: " + card.getVariant().getName());
        System.out.printf("Value: $%.2f\n", card.getValue());
        System.out.println("---------------------");
    }


    // Display Card or Collection
    public void displayCardOrCollectionMenu() {
        System.out.println("\n=== Display Card or Collection ===");
        System.out.println("1. Display a Card");
        System.out.println("2. Display Collection");
        System.out.println("0. Back\n");
    }
    public void displayCardDetails(Card card) {
        System.out.println("\n--- Card Details ---");
        System.out.println("Name: " + card.getName());
        System.out.println("Rarity: " + card.getRarity().getName());
        System.out.println("Variant: " + card.getVariant().getName());
        System.out.printf("Value: $%.2f\n", card.getValue());
        System.out.println("Count: " + card.getCount());
        System.out.println("---------------------");
    }
    /**
     * Displays the user's entire collection.
     *
     * @param cardCollection list of cards
     * @return true if cards exist, false otherwise
     */
    public boolean displayCollection(List<Card> cardCollection) {
        if (cardCollection.isEmpty()) {
            System.out.println("Your collection is empty.");
            return false;
        }

        System.out.println("\n=== Your Card Collection ===");
        System.out.print("#   Name                 Count\n");
        System.out.println("-------------------------------");

        for(int i = 0; i < cardCollection.size(); i++) {
            System.out.printf("%-3s %-20s %d\n", i + 1,
                    cardCollection.get(i).getName(), cardCollection.get(i).getCount());
        }

        return true;
    }

    /**
     * Displays sale confirmation prompt
     * @param card the card being sold
     * @return true if user confirms
     */
    public boolean confirmSale(Card card) {
        System.out.printf("Sell 1 %s [%s] (%s) for $%.2f? (yes/no): ",
                card.getName(),
                card.getRarity().getName(),
                card.getVariant().getName(),
                card.getValue());
        String response = sc.nextLine().trim().toLowerCase();
        return response.equals("yes") || response.equals("y");
    }

    /**
     * Displays sale result message
     * @param success true if sale was successful
     */
    public void displaySaleResult(boolean success) {
        if (success) {
            System.out.println("Card sold successfully!");
        } else {
            System.out.println("Sale failed - card not available");
        }
    }

    // Add this method to prompt for binder type
    public int promptForBinderType() {
        System.out.println("\nSelect binder type:");
        System.out.println("1. Non-curated Binder (basic, cannot be sold)");
        System.out.println("2. Pauper Binder (common/uncommon only, no tax)");
        System.out.println("3. Rares Binder (rare/legendary only, +10% tax)");
        System.out.println("4. Luxury Binder (non-normal rare/legendary, custom price)");
        System.out.println("5. Collector Binder (non-normal rare/legendary, cannot be sold)");
        System.out.print("Enter choice (0 to cancel): ");

        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Displays the binder type selection menu with subclass-specific descriptions.
     */
    /*public void displayBinderTypeMenu() {
        System.out.println("\n=== Binder Type Selection ===");
        System.out.println("1. Non-curated Binder");
        System.out.println("   - Can hold any card type");
        System.out.println("   - Cannot be sold");
        System.out.println("2. Pauper Binder");
        System.out.println("   - Only Common/Uncommon cards");
        System.out.println("   - No handling tax when sold");
        System.out.println("3. Rares Binder");
        System.out.println("   - Only Rare/Legendary cards");
        System.out.println("   - +10% handling tax when sold");
        System.out.println("4. Luxury Binder");
        System.out.println("   - Only non-normal Rare/Legendary (Extended/Full/Alt Art)");
        System.out.println("   - Custom pricing with +10% tax");
        System.out.println("5. Collector Binder");
        System.out.println("   - Only non-normal Rare/Legendary cards");
        System.out.println("   - Cannot be sold");
        System.out.println("0. Cancel and return to main menu");
        System.out.print("\nEnter your choice: ");
    }*/

    public BigDecimal promptForPrice(String message) {
        while (true) {
            System.out.print(message + ": ");
            try {
                return new BigDecimal(sc.nextLine()).setScale(2, RoundingMode.HALF_UP);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Example: 29.99");
            }
        }
    }
}