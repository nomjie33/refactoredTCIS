public class Main {
    public static void main(String[] args) {
        TradingCardInventorySystemModel model = new TradingCardInventorySystemModel();
        TradingCardInventorySystemView view = new TradingCardInventorySystemView();
        TradingCardInventorySystemController controller = new TradingCardInventorySystemController(model, view);

        //if (args.length > 0 && args[0].equalsIgnoreCase("gui")) {
            TradingCardInventorySystemGUI gui = new TradingCardInventorySystemGUI(controller);
            controller.setGUI(gui);
       // } else {
            controller.startProgram();
       // }
    }
}