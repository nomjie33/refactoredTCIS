public class Main {
    public static void main(String[] args) {
        TradingCardInventorySystemView view = new TradingCardInventorySystemView();
        TradingCardInventorySystemModel model = new TradingCardInventorySystemModel();
        TradingCardInventorySystemController controller = new TradingCardInventorySystemController(model, view);

        controller.startProgram();
    }
}