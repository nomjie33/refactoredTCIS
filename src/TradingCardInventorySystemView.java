import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * The view component of the Trading Card Inventory System.
 * Handles all user interface interactions, such as prompting inputs,
 * displaying menus and collections, and confirming actions.
 */
public class TradingCardInventorySystemView {
    private final Scanner sc;
    private final JFrame mainFrame;
    private final JLabel menuTitle;
    private final JButton returnButton;
    private final JPanel contentPanel;
    private final CardLayout contentPanelLayout;

    // Main Menu panel
    private final JPanel mainMenuPanel;
    private final JLabel collectorMoneyLabel;
    private final JButton addCardButton;
    private final JButton manageBindersButton;
    private final JButton manageDecksButton;
    private final JButton adjustCardCountButton;
    private final JButton displayCardButton;
    private final JButton displayCollectionButton;
    private final JButton sellCardButton;

    // Add Card panel
    private final JPanel addCardPanel;
    private final JLabel nameFieldLabel;
    private final JTextField nameField;
    private final JLabel selectRarityLabel;
    private final JPanel rarityButtonsPanel;
    private final ButtonGroup rarityButtons;
    private final JToggleButton commonButton;
    private final JToggleButton uncommonButton;
    private final JToggleButton rareButton;
    private final JToggleButton legendaryButton;
    private final JLabel selectVariantLabel;
    private final JPanel variantButtonsPanel;
    private final ButtonGroup variantButtons;
    private final JToggleButton normalButton;
    private final JToggleButton extendedArtButton;
    private final JToggleButton fullArtButton;
    private final JToggleButton altArtButton;
    private final JLabel valueFieldLabel;
    private final JTextField valueField;
    private final JButton confirmAddCardButton;

    // Manage Binders Panel
    private JPanel manageBindersPanel;
    private JList<String> bindersList;
    private JButton selectBinderButton;

    // Single Binder Panel
    private JPanel singleBinderPanel;
    private JList<Card> binderCardsList;
    private JButton addCardToBinderButton;
    private JButton removeCardFromBinderButton;
    private JButton tradeOrSellButton;  // Text changes based on binder type
    private JButton deleteBinderButton;

    // Binder Type Selection Panel
    private JPanel binderTypesPanel;
    private JButton basicBinderButton;
    private JButton pauperBinderButton;
    private JButton raresBinderButton;
    private JButton luxuryBinderButton;
    private JButton collectorBinderButton;

    // Adjust Card Count panel
    private final JList<String> adjustCardCountList;
    private final JScrollPane adjustCardCountScrollPane;
    private final JLabel adjustCardCountFieldLabel;
    private final JTextField adjustCardCountField;
    private final JButton adjustCardCountConfirmButton;

    // Display Card panel
    private final JPanel displayCardPanel;
    JComboBox<Card> cardsDropDown;
    private final JTextArea displayCardTextArea;

    /**
     * Constructs the view and initializes the scanner.
     */
    public TradingCardInventorySystemView() {
        this.sc = new Scanner(System.in);
        
        mainFrame = new JFrame("Trading Card Inventory System");
        mainFrame.setLayout(new BorderLayout());

        // Title label for current menu
        menuTitle = new JLabel();
        menuTitle.setPreferredSize(new Dimension(100, 80));
        menuTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
        menuTitle.setHorizontalAlignment(SwingConstants.CENTER);
        menuTitle.setVerticalAlignment(SwingConstants.CENTER);
        mainFrame.add(menuTitle, BorderLayout.NORTH);

        // South panel (for return to main menu/exit button)
        returnButton = new JButton();
        returnButton.setPreferredSize(new Dimension(150, 30));
        collectorMoneyLabel = new JLabel("Collector Money: $0.00");
        JPanel southPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        southPanel.add(Box.createRigidArea(new Dimension(0, 0)));
        southPanel.add(returnButton);
        southPanel.add(collectorMoneyLabel);
        mainFrame.add(southPanel, BorderLayout.SOUTH);

        // Center panel for menu contents
        contentPanel = new JPanel();
        contentPanel.setLayout(new CardLayout());
        mainFrame.add(contentPanel, BorderLayout.CENTER);


        // Main menu components
        addCardButton = new JButton("Add Card");
        manageBindersButton = new JButton();
        manageDecksButton = new JButton();
        adjustCardCountButton = new JButton("Adjust Card Count");
        displayCardButton = new JButton("Display Card");
        displayCollectionButton = new JButton("Display Collection");
        sellCardButton = new JButton("Sell Card");
        // Resize buttons
        JButton[] buttons = {addCardButton, manageBindersButton, manageDecksButton,
                adjustCardCountButton, displayCardButton, displayCollectionButton, sellCardButton};
        for(JButton button : buttons) {
            button.setPreferredSize(new Dimension(200, 50));
            button.setFont(new Font("Tahoma", Font.PLAIN, 16));
        }

        // Panel for main menu; holds main menu buttons
        mainMenuPanel = new JPanel();
        mainMenuPanel.setLayout(new GridBagLayout());
        mainMenuPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        mainMenuPanel.add(addCardButton, gbc);
        mainMenuPanel.add(manageBindersButton, gbc);
        mainMenuPanel.add(manageDecksButton, gbc);
        mainMenuPanel.add(adjustCardCountButton, gbc);
        mainMenuPanel.add(displayCardButton, gbc);
        mainMenuPanel.add(displayCollectionButton, gbc);
        mainMenuPanel.add(sellCardButton, gbc);


        // Add Card menu components
        nameFieldLabel = new JLabel("Name");
        nameFieldLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
//        JLabel nameWarningLabel = new JLabel("Invalid name.");
//        nameWarningLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
//        nameWarningLabel.setForeground(Color.RED);
//        nameWarningLabel.setVisible(false);
//        nameLabelPanel = new JPanel(new GridLayout(1, 2, 50, 0));
//        nameLabelPanel.setBackground(Color.LIGHT_GRAY);
//        nameLabelPanel.add(nameFieldLabel);
//        nameLabelPanel.add(nameWarningLabel);

        nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(150, 30));
        nameField.setFont(new Font("Tahoma", Font.PLAIN, 14));

        selectRarityLabel = new JLabel("Select Rarity");
        selectRarityLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        commonButton = new JToggleButton("Common");
        commonButton.setActionCommand("COMMON");
        uncommonButton = new JToggleButton("Uncommon");
        uncommonButton.setActionCommand("UNCOMMON");
        rareButton = new JToggleButton("Rare");
        rareButton.setActionCommand("RARE");
        legendaryButton = new JToggleButton("Legendary");
        legendaryButton.setActionCommand("LEGENDARY");
        rarityButtons = new ButtonGroup();
        rarityButtons.add(commonButton);
        rarityButtons.add(uncommonButton);
        rarityButtons.add(rareButton);
        rarityButtons.add(legendaryButton);
        rarityButtonsPanel = new JPanel(new GridLayout(1, 4));
        rarityButtonsPanel.add(commonButton);
        rarityButtonsPanel.add(uncommonButton);
        rarityButtonsPanel.add(rareButton);
        rarityButtonsPanel.add(legendaryButton);

        selectVariantLabel = new JLabel("Select Variant");
        selectVariantLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        sellCardButton.setActionCommand("SELL_CARD");
        normalButton = new JToggleButton("Normal");
        normalButton.setActionCommand("NORMAL");
        extendedArtButton = new JToggleButton("Extended-art");
        extendedArtButton.setActionCommand("EXTENDED_ART");
        fullArtButton = new JToggleButton("Full-art");
        fullArtButton.setActionCommand("FULL_ART");
        altArtButton = new JToggleButton("Alt-art");
        altArtButton.setActionCommand("ALT_ART");
        variantButtons = new ButtonGroup();
        commonButton.setSelected(true);     // set default rarity
        variantButtons.add(normalButton);
        variantButtons.add(extendedArtButton);
        variantButtons.add(fullArtButton);
        variantButtons.add(altArtButton);
        variantButtonsPanel = new JPanel(new GridLayout(1, 4));
        normalButton.setSelected(true);     // set default variant
        variantButtonsPanel.add(normalButton);
        variantButtonsPanel.add(extendedArtButton);
        variantButtonsPanel.add(fullArtButton);
        variantButtonsPanel.add(altArtButton);

        valueFieldLabel = new JLabel("Value (in $)");
        valueFieldLabel.setFont(new Font("Tahoma", Font.BOLD, 16));

        valueField = new JTextField(5);
        valueField.setPreferredSize(new Dimension(150, 30));
        valueField.setFont(new Font("Tahoma", Font.PLAIN, 14));

        confirmAddCardButton = new JButton("Confirm");
        confirmAddCardButton.setActionCommand("CONFIRM_ADD_CARD");

        // Panel for Add Card menu; holds Add Card buttons
        addCardPanel = new JPanel();
        addCardPanel.setLayout(new GridBagLayout());
        addCardPanel.setBackground(Color.LIGHT_GRAY);

        gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        gbc.insets = new Insets(0, 0, 0, 190);
        addCardPanel.add(nameFieldLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 20, 0);
        addCardPanel.add(nameField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        addCardPanel.add(selectRarityLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 20, 0);
        addCardPanel.add(rarityButtonsPanel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        addCardPanel.add(selectVariantLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 20, 0);
        addCardPanel.add(variantButtonsPanel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        addCardPanel.add(valueFieldLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 30, 0);
        addCardPanel.add(valueField, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        addCardPanel.add(confirmAddCardButton, gbc);


        // Adjust Card Count menu components
        adjustCardCountList = new JList<>();
        adjustCardCountList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultListCellRenderer dlcr = (DefaultListCellRenderer) adjustCardCountList.getCellRenderer();
        dlcr.setHorizontalAlignment(SwingConstants.CENTER);

        adjustCardCountScrollPane = new JScrollPane(adjustCardCountList);
        adjustCardCountScrollPane.setPreferredSize(new Dimension(250, 250));

        adjustCardCountFieldLabel = new JLabel("Enter count (+/-):");
        adjustCardCountFieldLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        adjustCardCountField = new JTextField(5);

        adjustCardCountConfirmButton = new JButton("Confirm");
        adjustCardCountConfirmButton.setActionCommand("CONFIRM_ADJUST_CARD_COUNT");

        // Panel for Adjust Card Count menu
        JPanel adjustCardCountPanel = new JPanel(new GridBagLayout());
        adjustCardCountPanel.setBackground(Color.LIGHT_GRAY);

        gbc.insets = new Insets(0, 0, 50, 50);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        adjustCardCountPanel.add(adjustCardCountScrollPane, gbc);

        gbc.insets = new Insets(70, 0, 0, 0);
        gbc.gridx++;
        gbc.gridheight = 1;
        adjustCardCountPanel.add(adjustCardCountFieldLabel, gbc);

        gbc.insets = new Insets(5, 0, 0, 0);
        gbc.gridy++;
        adjustCardCountPanel.add(adjustCardCountField, gbc);

        gbc.insets = new Insets(0, 0, 100, 0);
        gbc.gridy++;
        adjustCardCountPanel.add(adjustCardCountConfirmButton, gbc);


        // Display Card menu components
        JLabel cardsDropDownLabel = new JLabel("Select Card");
        cardsDropDownLabel.setFont(new Font("Tahoma", Font.BOLD, 24));

        cardsDropDown = new JComboBox<>();

        displayCardTextArea = new JTextArea("""
                Rarity:
                
                Variant:
                
                Value:
                
                Count:
                """);
        displayCardTextArea.setBackground(Color.LIGHT_GRAY);
        displayCardTextArea.setFont(new Font("Tahoma", Font.PLAIN, 18));
        displayCardTextArea.setPreferredSize(new Dimension(300, 300));
        displayCardTextArea.setEditable(false);
        displayCardTextArea.setFocusable(false);
        displayCardTextArea.setLineWrap(true);

        // Panel for Display Card menu
        displayCardPanel = new JPanel(new GridBagLayout());
        displayCardPanel.setBackground(Color.LIGHT_GRAY);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        displayCardPanel.add(cardsDropDownLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0);
        displayCardPanel.add(cardsDropDown, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(100, 0, 0, 0);
        displayCardPanel.add(displayCardTextArea, gbc);


        // Initialize Binder Panels
        initManageBindersPanel();
        initSingleBinderPanel();
        initBinderTypesPanel();

        // Add all menu panels to the main content panel
        contentPanel.add(mainMenuPanel, "MAIN_MENU");
        contentPanel.add(addCardPanel, "ADD_CARD");
        contentPanel.add(manageBindersPanel, "MANAGE_BINDERS");
        contentPanel.add(singleBinderPanel, "SINGLE_BINDER");
        contentPanel.add(binderTypesPanel, "BINDER_TYPE");
        contentPanel.add(adjustCardCountPanel, "ADJUST_CARD_COUNT");
        contentPanel.add(displayCardPanel, "DISPLAY_CARD");

        contentPanelLayout = (CardLayout) contentPanel.getLayout();



        // Initialize main GUI window
        mainFrame.setSize(540, 699);
        mainFrame.setVisible(true);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setActionListener(ActionListener al) {
        returnButton.addActionListener(al);

        // Main menu buttons
        addCardButton.addActionListener(al);
        manageBindersButton.addActionListener(al);
        manageDecksButton.addActionListener(al);
        adjustCardCountButton.addActionListener(al);
        displayCardButton.addActionListener(al);
        displayCollectionButton.addActionListener(al);
        sellCardButton.addActionListener(al);

        // Add Card menu buttons
        // Common and uncommon cards cannot have variants
        ItemListener disableVariantsIfSelected = ie -> {
            boolean selected = (ie.getStateChange() == ItemEvent.SELECTED);

            if (selected) {
                normalButton.setSelected(true);
            }

            extendedArtButton.setEnabled(!selected);
            fullArtButton.setEnabled(!selected);
            altArtButton.setEnabled(!selected);
        };

        commonButton.addItemListener(disableVariantsIfSelected);
        uncommonButton.addItemListener(disableVariantsIfSelected);
        commonButton.addActionListener(al);
        uncommonButton.addActionListener(al);
        rareButton.addActionListener(al);
        legendaryButton.addActionListener(al);
        normalButton.addActionListener(al);
        extendedArtButton.addActionListener(al);
        fullArtButton.addActionListener(al);
        altArtButton.addActionListener(al);
        confirmAddCardButton.addActionListener(al);

        // Binder Management Listeners
        manageBindersButton.addActionListener(al);  // Reused for Create/Manage
        selectBinderButton.addActionListener(al);
        addCardToBinderButton.addActionListener(al);
        removeCardFromBinderButton.addActionListener(al);
        tradeOrSellButton.addActionListener(al);
        deleteBinderButton.addActionListener(al);

        // Binder type buttons
        basicBinderButton.addActionListener(al);
        pauperBinderButton.addActionListener(al);
        raresBinderButton.addActionListener(al);
        luxuryBinderButton.addActionListener(al);
        collectorBinderButton.addActionListener(al);

        // Adjust Card Count
        adjustCardCountConfirmButton.addActionListener(al);

        // Display Card
        cardsDropDown.addActionListener(_ -> {
            Card selected = (Card) cardsDropDown.getSelectedItem();

            if(selected != null) {
                displayCardTextArea.setText(
                        "Rarity:  " + selected.getRarity().getName() +
                        "\n\nVariant:  " + selected.getVariant().getName() +
                        "\n\nValue:  $" + selected.getValue().toString() +
                        "\n\nCount:  " + selected.getCount());
            }
        });
    }
    public void setDocumentListener(DocumentListener dl) {
        // Add Card menu text fields
        nameField.getDocument().addDocumentListener(dl);
        valueField.getDocument().addDocumentListener(dl);
    }

    // Take input for card to add to the collection
    public String getAddCardName() {
        return nameField.getText();
    }
    public CardRarity getAddCardRarity() {
        ButtonModel selected = rarityButtons.getSelection();

        return CardRarity.valueOf(selected.getActionCommand());
    }
    public CardVariant getAddCardVariant() {
        ButtonModel selected = variantButtons.getSelection();

        return CardVariant.valueOf(selected.getActionCommand());
    }
    public String getAddCardValue() {
        return valueField.getText();
    }

    private void initManageBindersPanel() {
        manageBindersPanel = new JPanel(new BorderLayout());

        // Binders List
        bindersList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(bindersList);

        // Select Button (only used when binders exist)
        selectBinderButton = new JButton("Select Binder");
        selectBinderButton.setActionCommand("SELECT_BINDER");

        manageBindersPanel.add(scrollPane, BorderLayout.CENTER);
        manageBindersPanel.add(selectBinderButton, BorderLayout.SOUTH);
        bindersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initSingleBinderPanel() {
        singleBinderPanel = new JPanel(new BorderLayout());

        // Cards in Binder List
        binderCardsList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(binderCardsList);

        // Action Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        addCardToBinderButton = new JButton("Add Card");
        removeCardFromBinderButton = new JButton("Remove Card");
        tradeOrSellButton = new JButton(); // Text set dynamically
        deleteBinderButton = new JButton("Delete Binder");

        // Set action commands
        addCardToBinderButton.setActionCommand("ADD_CARD_TO_BINDER");
        removeCardFromBinderButton.setActionCommand("REMOVE_CARD_FROM_BINDER");
        tradeOrSellButton.setActionCommand("TRADE_OR_SELL_BINDER");
        deleteBinderButton.setActionCommand("DELETE_BINDER");

        buttonPanel.add(addCardToBinderButton);
        buttonPanel.add(removeCardFromBinderButton);
        buttonPanel.add(tradeOrSellButton);
        buttonPanel.add(deleteBinderButton);

        singleBinderPanel.add(scrollPane, BorderLayout.CENTER);
        singleBinderPanel.add(buttonPanel, BorderLayout.EAST);
        binderCardsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }
    public int getSelectedBinderIndex() {
        return bindersList.getSelectedIndex();
    }
    public int getSelectedBinderCardIndex() {
        return binderCardsList.getSelectedIndex();
    }
    public String getCurrentBinderName() {
        return menuTitle.getText().replace("Binder: ", "");
    }

    public void displayManageBindersMenu(List<String> binderNames) {
        menuTitle.setText("Manage Binders");
        bindersList.setListData(binderNames.toArray(new String[0]));
        contentPanelLayout.show(contentPanel, "MANAGE_BINDERS");
        returnButton.setText("Return to Main Menu");
    }

    public void displaySingleBinderMenu(String binderName, boolean isSellable) {
        menuTitle.setText("Binder: " + binderName);
        tradeOrSellButton.setText(isSellable ? "Sell Binder" : "Trade Card");
        contentPanelLayout.show(contentPanel, "SINGLE_BINDER");
        returnButton.setText("Return to Binders");
    }

    private void initBinderTypesPanel() {
        binderTypesPanel = new JPanel(new GridBagLayout());
        binderTypesPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 0, 10, 0); // Spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        menuTitle.setText("Select Binder Type");

        // Buttons (matches main menu style)
        basicBinderButton = createMenuButton("1. Basic Binder");
        pauperBinderButton = createMenuButton("2. Pauper Binder");
        raresBinderButton = createMenuButton("3. Rares Binder");
        luxuryBinderButton = createMenuButton("4. Luxury Binder");
        collectorBinderButton = createMenuButton("5. Collector Binder");

        // Set action commands
        basicBinderButton.setActionCommand("CREATE_BASIC_BINDER");
        pauperBinderButton.setActionCommand("CREATE_PAUPER_BINDER");
        raresBinderButton.setActionCommand("CREATE_RARES_BINDER");
        luxuryBinderButton.setActionCommand("CREATE_LUXURY_BINDER");
        collectorBinderButton.setActionCommand("CREATE_COLLECTOR_BINDER");

        // Add to panel
        gbc.gridy = 0;
        binderTypesPanel.add(basicBinderButton, gbc);
        gbc.gridy++;
        binderTypesPanel.add(pauperBinderButton, gbc);
        gbc.gridy++;
        binderTypesPanel.add(raresBinderButton, gbc);
        gbc.gridy++;
        binderTypesPanel.add(luxuryBinderButton, gbc);
        gbc.gridy++;
        binderTypesPanel.add(collectorBinderButton, gbc);
    }

    public void displayBinderTypeMenu(){
        menuTitle.setText("Select Binder Type");
        contentPanelLayout.show(contentPanel, "BINDER_TYPE");
        returnButton.setText("Return to Main Menu");
    }

    public void displayAdjustCardCountMenu(List<Card> cards) {
        List<String> cardListNames = new ArrayList<>();
        for (Card card : cards) {
            if(card.getCount() == 1) {
                cardListNames.add(card.getName() + " - " + card.getCount() + " copy");
            } else {
                cardListNames.add(card.getName() + " - " + card.getCount() + " copies");
            }
        }

        adjustCardCountList.setListData(cardListNames.toArray(new String[0]));
        adjustCardCountList.setSelectedIndex(0);

        menuTitle.setText("Adjust Card Count");
        contentPanelLayout.show(contentPanel, "ADJUST_CARD_COUNT");
        returnButton.setText("Return to Main Menu");
    }
    public String getAdjustCardCountName() {
        String cardNameInList = adjustCardCountList.getSelectedValue();
        int idx = cardNameInList.lastIndexOf(" - ");

        return cardNameInList.substring(0, idx);
    }
    public String getAdjustCardCountNumber() {
        return adjustCardCountField.getText();
    }

    public void displayCardDetailsMenu(List<Card> cards) {
        cardsDropDown.removeAllItems();

        for(Card card : cards) {
            cardsDropDown.addItem(card);
        }

        menuTitle.setText("Display Card Details");
        contentPanelLayout.show(contentPanel, "DISPLAY_CARD");
        returnButton.setText("Return to Main Menu");
    }

    public void setCollectorMoneyLabel(BigDecimal collectorMoney) {
        collectorMoneyLabel.setText("Collector Money: $" + collectorMoney);
    }



    //TODO: delete all methods used for CLI
    public String promptForBinderName() {
        String name = JOptionPane.showInputDialog(
                mainFrame,
                "Enter binder name:",
                "Create New Binder",
                JOptionPane.PLAIN_MESSAGE
        );

        return (name != null && !name.trim().isEmpty()) ? name.trim() : null;
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Tahoma", Font.PLAIN, 14));
        button.setFocusPainted(false);
        return button;
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Message", JOptionPane.INFORMATION_MESSAGE);

    }

    public void displayErrorMessage(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public Card showCardSelectionDialog(List<Card> cards) {
        // Create a panel to hold the card list
        JPanel panel = new JPanel(new BorderLayout());

        // Create a list model and populate it with card names
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Card card : cards) {
            listModel.addElement(card.getName() + " [" + card.getRarity() + "] - $" + card.getValue());
        }

        // Create the JList with the model
        JList<String> cardList = new JList<>(listModel);
        cardList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Add the list to a scroll pane
        JScrollPane scrollPane = new JScrollPane(cardList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Show the dialog
        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                panel,
                "Select Card to Sell",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        // Return the selected card if user clicked OK
        if (result == JOptionPane.OK_OPTION && !cardList.isSelectionEmpty()) {
            return cards.get(cardList.getSelectedIndex());
        }
        return null;
    }

    public void displayCollectionGUI(List<Card> cards){
        List<Card> sortedCards = new ArrayList<>();
        sortedCards.sort(Comparator.comparing(Card::getName));//sort cards alphabetically by name

        JPanel panel = new JPanel(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for(Card card : sortedCards){
            listModel.addElement(card.getName() + "(x" + card.getCount() + ")");
        }

        JList<String> cardList= new JList<>(listModel);

        cardList.setFont(new Font ("Tahoma", Font.PLAIN, 16));
        cardList.setFixedCellHeight(24);

        JScrollPane scrollPane = new JScrollPane(cardList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(mainFrame, panel, "Your Collection", JOptionPane.PLAIN_MESSAGE);
     }

    public boolean confirmActionGUI(String message) {
        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                message,
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );
        return result == JOptionPane.YES_OPTION;
    }

    public int promptForCardAdjustmentGUI() {
        while (true) {  // Keep asking until valid input or cancel
            String input = JOptionPane.showInputDialog(
                    mainFrame,
                    "Enter amount to increase count:",
                    "Increase Count",
                    JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) {
                return 0;
            }

            try {
                int value = Integer.parseInt(input.trim());
                if (value <= 0) {
                    displayErrorMessage("Please enter a positive number");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                displayErrorMessage("Invalid input - please enter digits only");
            }
        }
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


        menuTitle.setText("Main Menu");
        contentPanelLayout.show(contentPanel, "MAIN_MENU");
        returnButton.setText("Exit");

        if(hasBinders) {
            manageBindersButton.setText("Manage Binders");
        } else {
            manageBindersButton.setText("Create a new Binder");
        }

        if(hasDecks) {
            manageDecksButton.setText("Manage Decks");
        } else {
            manageDecksButton.setText("Create a new Deck");
        }

        if(!hasCards) {
            adjustCardCountButton.setVisible(false);
            displayCardButton.setVisible(false);
            displayCollectionButton.setVisible(false);
            sellCardButton.setVisible(false);
        } else {
            adjustCardCountButton.setVisible(true);
            displayCardButton.setVisible(true);
            displayCollectionButton.setVisible(true);
            sellCardButton.setVisible(true);
        }
    }

    public void displayAddCardMenu() {
        menuTitle.setText("Add Card");
        contentPanelLayout.show(contentPanel, "ADD_CARD");
        returnButton.setText("Return to Main Menu");
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
    /*public String promptForBinderName() {
        System.out.print("Enter binder name (press enter only to return to main menu): ");
        return sc.nextLine().trim();
    }*/

    /*public void displayManageBindersMenu(List<String> binderNames) {
        System.out.println("\n=== Manage Binders ===");// displays all existing binders

        for(int i = 0; i < binderNames.size(); i++) {
            System.out.println((i + 1) + ". " + binderNames.get(i));
        }

        System.out.println((binderNames.size() + 1) + ". Create a new Binder");
        System.out.println("0. Return to Main Menu\n");
    }*/

    // Update display method to show sell option only for sellable binders
    public void displayManageSingleBinderMenu(String binderName, boolean isSellable) {
        System.out.println("\n=== Binder: " + binderName + " ===");
        System.out.println("1. View Cards");
        System.out.println("2. Add Card");
        System.out.println("3. Remove Card");
        System.out.println("4. " + (isSellable ? "Sell Binder" : "Trade Card"));
        System.out.println("5. Delete Binder");
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
    public void displayManageSingleDeckMenu(String deckName, boolean isSellable) {
        System.out.println("\n=== Deck: " + deckName + " ===");
        System.out.println("1. View Cards");
        System.out.println("2. Add Card");
        System.out.println("3. Remove Card");
        System.out.println("4. Delete Deck");

        // Only show option 5 if deck is sellable
        if (isSellable) {
            System.out.println("5. Sell Deck");
        }

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

    // Add deck type selection
    public int promptForDeckType() {
        System.out.println("\nSelect deck type:");
        System.out.println("1. Normal Deck (cannot be sold)");
        System.out.println("2. Sellable Deck (can be sold for card value)");
        System.out.print("Enter choice: ");

        try {
            return Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

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