// TradingCardInventorySystemGUI.java
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class TradingCardInventorySystemGUI {
    private final TradingCardInventorySystemController controller;
    private JFrame mainFrame;
    private JPanel variantPanel;
    private JPanel rarityPanel;
    private JTextField binderNameField;

    // Image paths (placeholder names - replace with your actual image files)
    private static final String ADD_CARD_IMG = "C:/Users/yomi/Downloads/src (5)/src/add_card.png";
    private static final String CREATE_BINDER_IMG = "C:/Users/yomi/Downloads/src (5)/src/create_binder.png";
    private static final String CREATE_DECK_IMG = "C:/Users/yomi/Downloads/src (5)/src/create_deck.png";
    private static final String BACK_BUTTON_IMG = "C:/Users/yomi/Downloads/src (5)/src/back.png";
    private static final String MANAGE_BINDERS_IMG = "C:/Users/yomi/Downloads/src (5)/src/manage_binders.png";
    private static final String MANAGE_DECKS_IMG = "C:/Users/yomi/Downloads/src (5)/src/manage_decks.png";
    private static final String ADJUST_COUNT_IMG = "C:/Users/yomi/Downloads/src (5)/src/adjust_count.png";
    private static final String DISPLAY_IMG = "C:/Users/yomi/Downloads/src (5)/src/display.png";
    private static final String SELL_IMG = "C:/Users/yomi/Downloads/src (5)/src/sellcard.png";
    private static final String EXIT_IMG = "C:/Users/yomi/Downloads/src (5)/src/exit.png";
    // Rarity buttons
    private static final String COMMON_RARITY_IMG = "C:/Users/yomi/Downloads/src (5)/src/common.png";
    private static final String UNCOMMON_RARITY_IMG = "C:/Users/yomi/Downloads/src (5)/src/uncommon.png";
    private static final String RARE_RARITY_IMG = "C:/Users/yomi/Downloads/src (5)/src/rare.png";
    private static final String LEGENDARY_RARITY_IMG = "C:/Users/yomi/Downloads/src (5)/src/legendary.png";
    // Variant buttons
    private static final String NORMAL_VARIANT_IMG = "C:/Users/yomi/Downloads/src (5)/src/normal.png";
    private static final String EXTENDED_ART_VARIANT_IMG = "C:/Users/yomi/Downloads/src (5)/src/extart.png";
    private static final String FULLART_VARIANT_IMG = "C:/Users/yomi/Downloads/src (5)/src/fullart.png";
    private static final String ALT_VARIANT_IMG = "C:/Users/yomi/Downloads/src (5)/src/altart.png";
    // Binder type buttons
    private static final String BASIC_BINDER_IMG = "C:/Users/yomi/Downloads/src (5)/src/basic_binder.png";
    private static final String PAUPER_BINDER_IMG = "C:/Users/yomi/Downloads/src (5)/src/pauper_binder.png";
    private static final String RARES_BINDER_IMG = "C:/Users/yomi/Downloads/src (5)/src/rares_binder.png";
    private static final String LUXURY_BINDER_IMG = "C:/Users/yomi/Downloads/src (5)/src/luxury_binder.png";
    private static final String COLLECTOR_BINDER_IMG = "C:/Users/yomi/Downloads/src (5)/src/collector_binder.png";
    // Deck type buttons
    private static final String NORMAL_DECK_IMG = "C:/Users/yomi/Downloads/src (5)/src/normal_deck.png";
    private static final String SELLABLE_DECK_IMG = "C:/Users/yomi/Downloads/src (5)/src/sellable_deck.png";

    public TradingCardInventorySystemGUI(TradingCardInventorySystemController controller) {
        this.controller = controller;
        initializeMainFrame();
    }

    private void initializeMainFrame() {
        mainFrame = new JFrame("Trading Card Inventory System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        showInitialMenu();
        mainFrame.setVisible(true);
    }

    // Initial menu (shown when no cards/binders/decks exist)
    public void showInitialMenu() {
        boolean hasCards = controller.systemHasCards();
        boolean hasBinders = controller.systemHasBinders();
        boolean hasDecks = controller.systemHasDecks();

        // Use GridBagLayout for more flexible layout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        // Title
        JLabel titleLabel = new JLabel("Trading Card Inventory System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, gbc);

        // 1. Always show Add Card
        JButton addCardBtn = createImageButton(ADD_CARD_IMG, "Add New Card");
        addCardBtn.addActionListener(e -> showAddCardScreen());
        panel.add(addCardBtn, gbc);

        // 2. Binder option (Manage or Create)
        JButton binderBtn;
        if (hasBinders) {
            binderBtn = createImageButton(MANAGE_BINDERS_IMG, "Manage Binders");
            //TODO: binderBtn.addActionListener(e -> manageBindersScreen());
        } else {
            binderBtn = createImageButton(CREATE_BINDER_IMG, "Create Binder");
            binderBtn.addActionListener(e -> showCreateBinderScreen());
        }
        panel.add(binderBtn, gbc);

        // 3. Deck option (Manage or Create)
        JButton deckBtn;
        if (hasDecks) {
            deckBtn = createImageButton(MANAGE_DECKS_IMG, "Manage Decks");
            //TODO: deckBtn.addActionListener(e -> manageDecksScreen());
        } else {
            deckBtn = createImageButton(CREATE_DECK_IMG, "Create Deck");
            deckBtn.addActionListener(e -> showCreateDeckScreen());
        }
        panel.add(deckBtn, gbc);

        // Additional options if cards exist
        if (hasCards) {
            JButton adjustCountBtn = createImageButton(ADJUST_COUNT_IMG, "Adjust Card Count");
            //TODO: adjustCountBtn.addActionListener(e -> adjustCardCountScreen());
            panel.add(adjustCountBtn, gbc);

            JButton displayBtn = createImageButton(DISPLAY_IMG, "Display Collection");
            //TODO: displayBtn.addActionListener(e -> displayCardOrCollectionScreen());
            panel.add(displayBtn, gbc);

            JButton sellBtn = createImageButton(SELL_IMG, "Sell Card");
            //TODO: sellBtn.addActionListener(e -> sellCardsScreen());
            panel.add(sellBtn, gbc);
        }

        // Exit button
        JButton exitBtn = createImageButton(EXIT_IMG, "Exit");
        exitBtn.addActionListener(e -> System.exit(0));
        panel.add(exitBtn, gbc);

        updateMainFrame(panel);
    }
    // Helper method to create image buttons
    private JButton createImageButton(String imagePath, String tooltip) {
        ImageIcon icon = new ImageIcon(imagePath);
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        return button;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addRarityButton(JPanel panel, String text, String imagePath, CardRarity rarity) {
        JButton btn = createImageButton(imagePath, text);
        btn.addActionListener(e -> {
            selectedRarity = rarity;
            // Show variant panel only for rare/legendary cards
            variantPanel.setVisible(rarity == CardRarity.RARE || rarity == CardRarity.LEGENDARY);
        });
        panel.add(btn);
    }

    private void addVariantButton(JPanel panel, String text, String imagePath, CardVariant variant) {
        JButton btn = createImageButton(imagePath, text);
        btn.addActionListener(e -> {
            selectedVariant = variant;
        });
        panel.add(btn);
    }

    private void updateMainFrame(JPanel newPanel) {
        mainFrame.getContentPane().removeAll();
        mainFrame.getContentPane().add(newPanel);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private CardRarity selectedRarity;
    private CardVariant selectedVariant;
    private int selectedBinderType;

    public void showAddCardScreen() {
        // Initialize selections
        selectedRarity = null;
        selectedVariant = CardVariant.NORMAL; // Default variant

        JPanel panel = new JPanel(new BorderLayout(10, 10)); // Added gaps between components

        // Card Name Input
        JTextField nameField = new JTextField(20);
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Card Name:"));
        namePanel.add(nameField);

        // Rarity Selection
        rarityPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        addRarityButton(rarityPanel, "Common", COMMON_RARITY_IMG, CardRarity.COMMON);
        addRarityButton(rarityPanel, "Uncommon", UNCOMMON_RARITY_IMG, CardRarity.UNCOMMON);
        addRarityButton(rarityPanel, "Rare", RARE_RARITY_IMG, CardRarity.RARE);
        addRarityButton(rarityPanel, "Legendary", LEGENDARY_RARITY_IMG, CardRarity.LEGENDARY);

        // Variant Selection (initially hidden)
        variantPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        variantPanel.setVisible(false);
        addVariantButton(variantPanel, "Normal", NORMAL_VARIANT_IMG, CardVariant.NORMAL);
        addVariantButton(variantPanel, "Extended Art", EXTENDED_ART_VARIANT_IMG, CardVariant.EXTENDED_ART);
        addVariantButton(variantPanel, "Full Art", FULLART_VARIANT_IMG, CardVariant.FULL_ART);
        addVariantButton(variantPanel, "Alt Art", ALT_VARIANT_IMG, CardVariant.ALT_ART);

        // Value Input
        JTextField valueField = new JTextField(10);
        JPanel valuePanel = new JPanel();
        valuePanel.add(new JLabel("Value: $"));
        valuePanel.add(valueField);

        // Submit and Back Buttons
        JButton submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> {
            controller.handleAddCardFromGUI(
                    nameField.getText(),
                    selectedRarity,
                    selectedVariant,
                    valueField.getText()
            );
        });

        JButton backBtn = createImageButton(BACK_BUTTON_IMG, "Back");
        backBtn.addActionListener(e -> showInitialMenu());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitBtn);
        buttonPanel.add(backBtn);

        // Create a container panel for the bottom components
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(valuePanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Create a container panel for the rarity and variant panels
        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.add(rarityPanel, BorderLayout.NORTH);
        selectionPanel.add(variantPanel, BorderLayout.CENTER);

        // Layout
        panel.add(namePanel, BorderLayout.NORTH);
        panel.add(selectionPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        updateMainFrame(panel);
    }

    public void showCreateBinderScreen() {
        JPanel panel = new JPanel(new BorderLayout());

        // Binder Name Input
        binderNameField = new JTextField(20); // Store as field instead of local variable
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Binder Name:"));
        namePanel.add(binderNameField);

        // Binder Type Selection
        JPanel typePanel = new JPanel(new GridLayout(5, 1, 5, 5));

        addBinderTypeButton(typePanel, "Basic Binder", BASIC_BINDER_IMG, 1,
                "Holds any cards, cannot be sold");
        addBinderTypeButton(typePanel, "Pauper Binder", PAUPER_BINDER_IMG, 2,
                "Only common/uncommon cards, no tax");
        addBinderTypeButton(typePanel, "Rares Binder", RARES_BINDER_IMG, 3,
                "Only rare/legendary cards, +10% tax");
        addBinderTypeButton(typePanel, "Luxury Binder", LUXURY_BINDER_IMG, 4,
                "Special variants only, custom pricing");
        addBinderTypeButton(typePanel, "Collector Binder", COLLECTOR_BINDER_IMG, 5,
                "Special variants only, cannot be sold");

        // Back Button
        JButton backBtn = createImageButton(BACK_BUTTON_IMG, "Back");
        backBtn.addActionListener(e -> showInitialMenu());

        // Layout
        panel.add(namePanel, BorderLayout.NORTH);
        panel.add(typePanel, BorderLayout.CENTER);
        panel.add(backBtn, BorderLayout.SOUTH);

        updateMainFrame(panel);
    }

    // Helper method for binder type buttons
    private void addBinderTypeButton(JPanel panel, String text, String imagePath,
                                     int type, String description) {
        JButton btn = createImageButton(imagePath, text);
        btn.addActionListener(e -> {
            controller.handleCreateBinderFromGUI(
                    binderNameField.getText(), // Now using the class field
                    type
            );
        });

        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.add(btn, BorderLayout.CENTER);
        btnPanel.add(new JLabel(description), BorderLayout.SOUTH);
        panel.add(btnPanel);
    }

    private void showCreateDeckScreen() {
        JPanel panel = new JPanel(new BorderLayout());

        // Deck Name Input
        JTextField nameField = new JTextField(20);
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Deck Name:"));
        namePanel.add(nameField);

        // Deck Type Selection
        JPanel typePanel = new JPanel(new GridLayout(2, 1, 5, 5));

        JButton normalDeckBtn = createImageButton(NORMAL_DECK_IMG, "Normal Deck");
        normalDeckBtn.addActionListener(e -> {
            controller.handleCreateDeckFromGUI(nameField.getText(), false);
        });

        JButton sellableDeckBtn = createImageButton(SELLABLE_DECK_IMG, "Sellable Deck");
        sellableDeckBtn.addActionListener(e -> {
            controller.handleCreateDeckFromGUI(nameField.getText(), true);
        });

        typePanel.add(normalDeckBtn);
        typePanel.add(sellableDeckBtn);

        // Back Button
        JButton backBtn = createImageButton(BACK_BUTTON_IMG, "Back");
        backBtn.addActionListener(e -> showInitialMenu());

        // Layout
        panel.add(namePanel, BorderLayout.NORTH);
        panel.add(typePanel, BorderLayout.CENTER);
        panel.add(backBtn, BorderLayout.SOUTH);

        updateMainFrame(panel);
    }

    // ... Similar methods for createBinderScreen and createDeckScreen
}