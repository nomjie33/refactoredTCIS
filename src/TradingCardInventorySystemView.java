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
    private JButton createBinderButton;

    // Single Binder Panel
    private JPanel singleBinderPanel;
    private JList<Card> binderCardsList;
    private JButton addCardToBinderButton;
    private JButton removeCardFromBinderButton;
    private JButton tradeOrSellButton;  // Text changes based on binder type
    private JButton deleteBinderButton;
    private JButton viewBinderCardButton;

    // Create Binder panel
    private JPanel createBinderPanel;
    private JButton basicBinderButton;
    private JButton pauperBinderButton;
    private JButton raresBinderButton;
    private JButton luxuryBinderButton;
    private JButton collectorBinderButton;


    // Deck Type selection panel
    private JPanel createDeckPanel;
    private JButton normalDeckButton;
    private JButton sellableDeckButton;

    // Manage Decks panel
    private JPanel manageDecksPanel;
    private JList<String> decksList;
    private JButton selectDeckButton;
    private JButton createDeckButton;

    // Single Deck panel
    private JPanel singleDeckPanel;
    private JList<Card> deckCardsList;
    private JButton addCardToDeckButton;
    private JButton removeCardFromDeckButton;
    private JButton sellDeckButton;
    private JButton deleteDeckButton;
    private JButton viewDeckCardButton;


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
        for (JButton button : buttons) {
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
        initCreateBinderPanel();
        initManageBindersPanel();
        initSingleBinderPanel();

        // Initialize Deck Panels
        initCreateDeckPanel();
        initManageDecksPanel();
        initSingleDeckPanel();


        // Add all menu panels to the main content panel
        contentPanel.add(mainMenuPanel, "MAIN_MENU");
        contentPanel.add(addCardPanel, "ADD_CARD");
        contentPanel.add(createBinderPanel, "CREATE_BINDER");
        contentPanel.add(manageBindersPanel, "MANAGE_BINDERS");
        contentPanel.add(singleBinderPanel, "SINGLE_BINDER");
        contentPanel.add(createDeckPanel, "CREATE_DECK");
        contentPanel.add(manageDecksPanel, "MANAGE_DECKS");
        contentPanel.add(singleDeckPanel, "SINGLE_DECK");
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

        // Create Binder buttons
        basicBinderButton.addActionListener(al);
        pauperBinderButton.addActionListener(al);
        raresBinderButton.addActionListener(al);
        luxuryBinderButton.addActionListener(al);
        collectorBinderButton.addActionListener(al);

        // Manage Binders buttons
        selectBinderButton.addActionListener(al);
        addCardToBinderButton.addActionListener(al);
        removeCardFromBinderButton.addActionListener(al);
        tradeOrSellButton.addActionListener(al);
        deleteBinderButton.addActionListener(al);
        viewBinderCardButton.addActionListener(al);
        createBinderButton.addActionListener(al);


        // Create Deck buttons
        normalDeckButton.addActionListener(al);
        sellableDeckButton.addActionListener(al);

        // Manage Decks buttons
        selectDeckButton.addActionListener(al);
        addCardToDeckButton.addActionListener(al);
        removeCardFromDeckButton.addActionListener(al);
        sellDeckButton.addActionListener(al);
        deleteDeckButton.addActionListener(al);
        viewDeckCardButton.addActionListener(al);
        createDeckButton.addActionListener(al);

        // Adjust Card Count
        adjustCardCountConfirmButton.addActionListener(al);

        // Display Card
        cardsDropDown.addActionListener(_ -> {
            Card selected = (Card) cardsDropDown.getSelectedItem();

            if (selected != null) {
                displayCardTextArea.setText(
                        "Rarity:  " + selected.getRarity().getName() +
                                "\n\nVariant:  " + selected.getVariant().getName() +
                                "\n\nValue:  $" + selected.getValue().toString() +
                                "\n\nCount:  " + selected.getCount());
            }
        });

        // Display Collection
        displayCollectionButton.addActionListener(al);
    }

    public void setDocumentListener(DocumentListener dl) {
        // Add Card menu text fields
        nameField.getDocument().addDocumentListener(dl);
        valueField.getDocument().addDocumentListener(dl);
    }

    // Take input for card to add to the collection
    public String getAddCardName() {
        return nameField.getText().trim();
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
        return valueField.getText().trim();
    }

    private void initManageBindersPanel() {
        manageBindersPanel = new JPanel(new BorderLayout());

        // Binders List
        bindersList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(bindersList);
        DefaultListCellRenderer dlcr = (DefaultListCellRenderer) bindersList.getCellRenderer();
        dlcr.setHorizontalAlignment(SwingConstants.CENTER);

        // Button Panel (for select and create buttons)
        JPanel singleBinderButtonsPanel = new JPanel(new GridLayout(1, 2));
        selectBinderButton = new JButton("Select Binder");
        selectBinderButton.setPreferredSize(new Dimension(100, 50));
        createBinderButton = new JButton("Create a new Binder");
        singleBinderButtonsPanel.add(selectBinderButton);
        singleBinderButtonsPanel.add(createBinderButton);

        manageBindersPanel.add(scrollPane, BorderLayout.CENTER);
        manageBindersPanel.add(singleBinderButtonsPanel, BorderLayout.SOUTH);
        bindersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initSingleBinderPanel() {
        singleBinderPanel = new JPanel(new BorderLayout());

        // Cards in Binder List
        binderCardsList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(binderCardsList);

        // Action Buttons
        JPanel singleBinderButtonsPanel = new JPanel(new GridLayout(4, 1));
        viewBinderCardButton = new JButton("View Card");
        addCardToBinderButton = new JButton("Add Card");
        removeCardFromBinderButton = new JButton("Remove Card");
        tradeOrSellButton = new JButton(); // Text set dynamically
        deleteBinderButton = new JButton("Delete Binder");

        // Set action commands
        viewBinderCardButton.setActionCommand("VIEW_BINDER_CARD");
        addCardToBinderButton.setActionCommand("ADD_CARD_TO_BINDER");
        removeCardFromBinderButton.setActionCommand("REMOVE_CARD_FROM_BINDER");
        tradeOrSellButton.setActionCommand("TRADE_OR_SELL_BINDER");
        deleteBinderButton.setActionCommand("DELETE_BINDER");

        singleBinderButtonsPanel.add(viewBinderCardButton);
        singleBinderButtonsPanel.add(addCardToBinderButton);
        singleBinderButtonsPanel.add(removeCardFromBinderButton);
        singleBinderButtonsPanel.add(tradeOrSellButton);
        singleBinderButtonsPanel.add(deleteBinderButton);

        singleBinderPanel.add(scrollPane, BorderLayout.CENTER);
        singleBinderPanel.add(singleBinderButtonsPanel, BorderLayout.EAST);
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

    public void displayManageBindersMenu(List<Binder> binders) {
        List<String> binderListNames = new ArrayList<>();
        for (Binder b : binders) {
            if (b instanceof NonCuratedBinder) {
                binderListNames.add(b.getName() + " - " + "Non-curated Binder");
            } else if (b instanceof PauperBinder) {
                binderListNames.add(b.getName() + " - " + "Pauper Binder");
            } else if (b instanceof RaresBinder) {
                binderListNames.add(b.getName() + " - " + "Rares Binder");
            } else if (b instanceof LuxuryBinder) {
                binderListNames.add(b.getName() + " - " + "Luxury Binder");
            } else if (b instanceof CollectorBinder) {
                binderListNames.add(b.getName() + " - " + "Collector Binder");
            }
        }

        menuTitle.setText("Manage Binders");
        bindersList.setListData(binderListNames.toArray(new String[0]));
        contentPanelLayout.show(contentPanel, "MANAGE_BINDERS");
        returnButton.setText("Return to Main Menu");
    }

    public void displaySingleBinderMenu(String binderName, boolean isSellable) {
        menuTitle.setText("Binder: " + binderName);
        tradeOrSellButton.setText(isSellable ? "Sell Binder" : "Trade Card");
        contentPanelLayout.show(contentPanel, "SINGLE_BINDER");
        returnButton.setText("Return to Binders");
    }

    private void initCreateBinderPanel() {
        createBinderPanel = new JPanel(new GridBagLayout());
        createBinderPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(15, 0, 15, 0); // Spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Components
        JLabel selectBinderTypeLabel = new JLabel("Select Binder Type");
        selectBinderTypeLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        basicBinderButton = createMenuButton("Basic Binder");
        pauperBinderButton = createMenuButton("Pauper Binder");
        raresBinderButton = createMenuButton("Rares Binder");
        luxuryBinderButton = createMenuButton("Luxury Binder");
        collectorBinderButton = createMenuButton("Collector Binder");

        // Set action commands
        basicBinderButton.setActionCommand("CREATE_BASIC_BINDER");
        pauperBinderButton.setActionCommand("CREATE_PAUPER_BINDER");
        raresBinderButton.setActionCommand("CREATE_RARES_BINDER");
        luxuryBinderButton.setActionCommand("CREATE_LUXURY_BINDER");
        collectorBinderButton.setActionCommand("CREATE_COLLECTOR_BINDER");

        // Add to panel
        gbc.gridy = 0;
        createBinderPanel.add(selectBinderTypeLabel, gbc);

        gbc.gridy++;
        createBinderPanel.add(basicBinderButton, gbc);

        gbc.gridy++;
        createBinderPanel.add(pauperBinderButton, gbc);

        gbc.gridy++;
        createBinderPanel.add(raresBinderButton, gbc);

        gbc.gridy++;
        createBinderPanel.add(luxuryBinderButton, gbc);

        gbc.gridy++;
        createBinderPanel.add(collectorBinderButton, gbc);
    }

    public void displayCreateBinderMenu() {
        menuTitle.setText("Create a New Binder");
        contentPanelLayout.show(contentPanel, "CREATE_BINDER");
        returnButton.setText("Return to Main Menu");
    }

    public void initCreateDeckPanel() {
        createDeckPanel = new JPanel(new GridBagLayout());
        createDeckPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(20, 0, 20, 0); // Spacing
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        menuTitle.setText("Select Deck Type");

        // Buttons (matches main menu style)
        normalDeckButton = createMenuButton("1. Normal Deck");
        sellableDeckButton = createMenuButton("2. Sellable Deck");

        // Set action commands
        normalDeckButton.setActionCommand("CREATE_NORMAL_DECK");
        sellableDeckButton.setActionCommand("CREATE_SELLABLE_DECK");

        // Add to panel
        gbc.gridy = 0;
        createDeckPanel.add(normalDeckButton, gbc);
        gbc.gridy++;
        createDeckPanel.add(sellableDeckButton, gbc);
    }

    private void initManageDecksPanel() {
        manageDecksPanel = new JPanel(new BorderLayout());

        // Decks List
        decksList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(decksList);
        DefaultListCellRenderer dlcr = (DefaultListCellRenderer) decksList.getCellRenderer();
        dlcr.setHorizontalAlignment(SwingConstants.CENTER);

        // Button Panel (for select and create buttons)
        JPanel singleBinderButtonsPanel = new JPanel(new GridLayout(1, 2));
        selectDeckButton = new JButton("Select Deck");
        createDeckButton = new JButton("Create a new Deck");
        singleBinderButtonsPanel.add(selectDeckButton);
        singleBinderButtonsPanel.add(createDeckButton);

        manageDecksPanel.add(scrollPane, BorderLayout.CENTER);
        manageDecksPanel.add(singleBinderButtonsPanel, BorderLayout.SOUTH);
        decksList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void initSingleDeckPanel() {
        singleDeckPanel = new JPanel(new BorderLayout());

        // Cards in Deck List
        deckCardsList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(deckCardsList);

        // Action Buttons
        JPanel singleDeckButtonsPanel = new JPanel(new GridLayout(4, 1));
        viewDeckCardButton = new JButton("View Card");
        addCardToDeckButton = new JButton("Add Card");
        removeCardFromDeckButton = new JButton("Remove Card");
        sellDeckButton = new JButton("Sell Deck");
        deleteDeckButton = new JButton("Delete Deck");

        // Set action commands
        viewDeckCardButton.setActionCommand("VIEW_DECK_CARD");
        addCardToDeckButton.setActionCommand("ADD_CARD_TO_DECK");
        removeCardFromDeckButton.setActionCommand("REMOVE_CARD_FROM_DECK");
        sellDeckButton.setActionCommand("SELL_DECK");
        deleteDeckButton.setActionCommand("DELETE_DECK");

        singleDeckButtonsPanel.add(viewDeckCardButton);
        singleDeckButtonsPanel.add(addCardToDeckButton);
        singleDeckButtonsPanel.add(removeCardFromDeckButton);
        singleDeckButtonsPanel.add(sellDeckButton);
        singleDeckButtonsPanel.add(deleteDeckButton);

        singleDeckPanel.add(scrollPane, BorderLayout.CENTER);
        singleDeckPanel.add(singleDeckButtonsPanel, BorderLayout.EAST);
        deckCardsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public void displayCreateDeckMenu() {
        menuTitle.setText("Create a new Deck");
        contentPanelLayout.show(contentPanel, "CREATE_DECK");
        returnButton.setText("Return to Main Menu");
    }

    public void displayManageDecksMenu(List<Deck> decks) {
        List<String> deckListNames = new ArrayList<>();
        for (Deck d : decks) {
            if (d instanceof NormalDeck) {
                deckListNames.add(d.getName() + " - Normal Deck");
            } else if (d instanceof SellableDeck) {
                deckListNames.add(d.getName() + " - Sellable Deck");
            }
        }

        decksList.setListData(deckListNames.toArray(new String[0]));

        menuTitle.setText("Manage Decks");
        contentPanelLayout.show(contentPanel, "MANAGE_DECKS");
        returnButton.setText("Return to Main Menu");
    }

    public void displaySingleDeckMenu(String deckName, boolean isSellable) {
        menuTitle.setText("Deck: " + deckName);
        sellDeckButton.setEnabled(isSellable);
        contentPanelLayout.show(contentPanel, "SINGLE_DECK");
        returnButton.setText("Return to Decks");
    }

    public int getSelectedDeckIndex() {
        return decksList.getSelectedIndex();
    }

    public int getSelectedDeckCardIndex() {
        return deckCardsList.getSelectedIndex();
    }

    public String getCurrentDeckName() {
        return menuTitle.getText().replace("Deck: ", "");
    }


    public void displayAdjustCardCountMenu(List<Card> cards) {
        List<String> cardListNames = new ArrayList<>();
        for (Card card : cards) {
            if (card.getCount() == 1) {
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
        return adjustCardCountField.getText().trim();
    }

    public void displayCardDetailsMenu(List<Card> cards) {
        cardsDropDown.removeAllItems();

        for (Card card : cards) {
            cardsDropDown.addItem(card);
        }

        menuTitle.setText("Display Card Details");
        contentPanelLayout.show(contentPanel, "DISPLAY_CARD");
        returnButton.setText("Return to Main Menu");
    }

    public void setCollectorMoneyLabel(BigDecimal collectorMoney) {
        collectorMoneyLabel.setText("Collector Money: $" + collectorMoney);
    }

    public void displayCardDetails(Card card) {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(
                "Name: " + card.getName() + "\n" +
                        "Rarity: " + card.getRarity() + "\n" +
                        "Variant: " + card.getVariant() + "\n" +
                        "Value: $" + card.getValue() + "\n" +
                        "Count: " + card.getCount()
        );
        textArea.setEditable(false);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
                mainFrame,
                panel,
                "Card Details",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    //TODO: delete all methods used for CLI
    public String promptForBinderName() {
        String name = JOptionPane.showInputDialog(
                mainFrame,
                "Enter binder name:",
                "Create New Binder",
                JOptionPane.PLAIN_MESSAGE
        );

        return (name != null) ? name.trim() : "";
    }

    public String promptForDeckName() {
        String name = JOptionPane.showInputDialog(
                mainFrame,
                "Enter deck name:",
                "Create New Deck",
                JOptionPane.PLAIN_MESSAGE
        );

        return (name != null) ? name.trim() : "";
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
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Card card : cards) {
            listModel.addElement(card.getName() + " [" + card.getRarity() + "] - $" + card.getValue());
        }

        JList<String> cardList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(cardList);

        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                scrollPane,
                "Select Card to Add",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION && !cardList.isSelectionEmpty()) {
            return cards.get(cardList.getSelectedIndex());
        }
        return null;
    }

    public Card showTradeCardDialog() {
        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
        JTextField nameField = new JTextField();
        JComboBox<CardRarity> rarityCombo = new JComboBox<>(CardRarity.values());
        JComboBox<CardVariant> variantCombo = new JComboBox<>(CardVariant.values());
        JTextField valueField = new JTextField();

        panel.add(new JLabel("New Card Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Rarity:"));
        panel.add(rarityCombo);
        panel.add(new JLabel("Variant:"));
        panel.add(variantCombo);
        panel.add(new JLabel("Value:"));
        panel.add(valueField);

        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                panel,
                "Enter New Card Details",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                return new Card(
                        nameField.getText().trim(),
                        (CardRarity) rarityCombo.getSelectedItem(),
                        (CardVariant) variantCombo.getSelectedItem(),
                        new BigDecimal(valueField.getText()),
                        1
                );
            } catch (NumberFormatException e) {
                displayMessage("Invalid value entered");
                return null;
            }
        }
        return null;
    }

    public void displayCollectionGUI(List<Card> cards) {
        cards.sort(Comparator.comparing(Card::getName)); //sort cards alphabetically by name

        JPanel panel = new JPanel(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Card card : cards) {
            if (card.getCount() == 1) {
                listModel.addElement(card.getName() + " - " + card.getCount() + " copy");
            } else {
                listModel.addElement(card.getName() + " - " + card.getCount() + " copies");
            }
        }

        JList<String> cardList = new JList<>(listModel);

        cardList.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cardList.setFixedCellHeight(24);

        JScrollPane scrollPane = new JScrollPane(cardList);
        panel.add(scrollPane, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(mainFrame, panel, "Your Collection", JOptionPane.PLAIN_MESSAGE);
    }

    public boolean confirmAction(String message) {
        int result = JOptionPane.showConfirmDialog(
                mainFrame,
                message,
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );
        return result == JOptionPane.YES_OPTION;
    }

    public String promptForCustomPrice(BigDecimal minPrice) {
        return JOptionPane.showInputDialog(
                mainFrame,
                "Enter custom price (minimum $" + minPrice + "):",
                "Custom Price",
                JOptionPane.QUESTION_MESSAGE
        );
    }

    public void updateBinderCardsList(List<Card> cards) {
        binderCardsList.setListData(cards.toArray(new Card[0]));
    }

    public void updateDeckCardsList(List<Card> cards) {
        deckCardsList.setListData(cards.toArray(new Card[0]));
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
     * Displays the main menu based on existing system state.
     *
     * @param hasCards   whether the collection has cards
     * @param hasBinders whether any binders exist
     * @param hasDecks   whether any decks exist
     */
    public void displayMainMenu(boolean hasCards, boolean hasBinders, boolean hasDecks) {
        menuTitle.setText("Main Menu");
        contentPanelLayout.show(contentPanel, "MAIN_MENU");
        returnButton.setText("Exit");

        if (hasBinders) {
            manageBindersButton.setText("Manage Binders");
        } else {
            manageBindersButton.setText("Create a new Binder");
        }

        if (hasDecks) {
            manageDecksButton.setText("Manage Decks");
        } else {
            manageDecksButton.setText("Create a new Deck");
        }

        if (!hasCards) {
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
}