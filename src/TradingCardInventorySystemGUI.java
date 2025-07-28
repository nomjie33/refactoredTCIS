// TradingCardInventorySystemGUI.java
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class TradingCardInventorySystemGUI {
    private final TradingCardInventorySystemController controller;
    private JFrame mainFrame;

    // Image paths (placeholder names - replace with your actual image files)
    private static final String ADD_CARD_IMG = "images/add_card.png";
    private static final String CREATE_BINDER_IMG = "images/create_binder.png";
    private static final String CREATE_DECK_IMG = "images/create_deck.png";
    private static final String BACK_BUTTON_IMG = "images/back.png";
    // Rarity buttons
    private static final String COMMON_RARITY_IMG = "images/common.png";
    private static final String UNCOMMON_RARITY_IMG = "images/uncommon.png";
    private static final String RARE_RARITY_IMG = "images/rare.png";
    private static final String LEGENDARY_RARITY_IMG = "images/legendary.png";
    // Variant buttons
    private static final String NORMAL_VARIANT_IMG = "images/normal.png";
    private static final String HOLO_VARIANT_IMG = "images/holo.png";
    private static final String FULLART_VARIANT_IMG = "images/fullart.png";
    private static final String SECRET_VARIANT_IMG = "images/secret.png";
    // Binder type buttons
    private static final String BASIC_BINDER_IMG = "images/basic_binder.png";
    private static final String PAUPER_BINDER_IMG = "images/pauper_binder.png";
    // ... more binder type images
    // Deck type buttons
    private static final String NORMAL_DECK_IMG = "images/normal_deck.png";
    private static final String SELLABLE_DECK_IMG = "images/sellable_deck.png";

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
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton addCardBtn = createImageButton(ADD_CARD_IMG, "Add New Card");
        addCardBtn.addActionListener(e -> showAddCardScreen());

        JButton createBinderBtn = createImageButton(CREATE_BINDER_IMG, "Create Binder");
        createBinderBtn.addActionListener(e -> showCreateBinderScreen());

        JButton createDeckBtn = createImageButton(CREATE_DECK_IMG, "Create Deck");
        createDeckBtn.addActionListener(e -> showCreateDeckScreen());

        panel.add(addCardBtn);
        panel.add(createBinderBtn);
        panel.add(createDeckBtn);

        updateMainFrame(panel);
    }

    // Add Card Screen
    private void showAddCardScreen() {
        JPanel panel = new JPanel(new BorderLayout());

        // Card name input
        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("Card Name:");
        JTextField nameField = new JTextField(20);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        // Rarity selection
        JPanel rarityPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        addRarityButton(rarityPanel, "Common", COMMON_RARITY_IMG, CardRarity.COMMON);
        addRarityButton(rarityPanel, "Uncommon", UNCOMMON_RARITY_IMG, CardRarity.UNCOMMON);
        addRarityButton(rarityPanel, "Rare", RARE_RARITY_IMG, CardRarity.RARE);
        addRarityButton(rarityPanel, "Legendary", LEGENDARY_RARITY_IMG, CardRarity.LEGENDARY);

        // Variant selection (initially hidden)
        JPanel variantPanel = new JPanel(new GridLayout(1, 4, 5, 5));
        variantPanel.setVisible(false);
        addVariantButton(variantPanel, "Normal", NORMAL_VARIANT_IMG, CardVariant.NORMAL);
        addVariantButton(variantPanel, "Holographic", HOLO_VARIANT_IMG, CardVariant.HOLOGRAPHIC);
        // ... more variants

        // Value input
        JPanel valuePanel = new JPanel();
        JLabel valueLabel = new JLabel("Card Value:");
        JTextField valueField = new JTextField(10);
        valuePanel.add(valueLabel);
        valuePanel.add(valueField);

        // Back button
        JButton backBtn = createImageButton(BACK_BUTTON_IMG, "Back to Main Menu");
        backBtn.addActionListener(e -> showInitialMenu());

        panel.add(namePanel, BorderLayout.NORTH);
        panel.add(rarityPanel, BorderLayout.CENTER);
        panel.add(variantPanel, BorderLayout.CENTER);
        panel.add(valuePanel, BorderLayout.SOUTH);
        panel.add(backBtn, BorderLayout.SOUTH);

        updateMainFrame(panel);
    }

    // Helper method to create image buttons
    private JButton createImageButton(String imagePath, String tooltip) {
        ImageIcon icon = new ImageIcon(imagePath);
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        return button;
    }

    // ... Similar methods for createBinderScreen and createDeckScreen
}