package Controller;

import DesignPattern.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {
    public RadioButton KaspiBankRB;
    public RadioButton halykBankRB;
    public RadioButton otherRB;
    public TextField cardNumberText;
    public TextField nameOnCardText;
    public PasswordField cvvField;
    public TextField monthField;
    public Button submitButton;
    public ListView<String> listView;
    public Text subTotalText;
    public Text shippingText;
    public Text feeText;
    public Text totalText;
    public VBox vBoxCardField;
    public Button payNow;
    boolean ok = false;

    Cart cart = MarketController.getCart();
    List<CartItem> cartItems = cart.getItems();
    ShoppingCartFacade cartFacade = new ShoppingCartFacade(cart);
    PaymentStrategy paymentStrategy;

    OrderContext orderContext = CartController.getOrderContext();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderContext.processOrder();

        submitButton.setOnAction(event -> {
            if ((KaspiBankRB.isSelected() || halykBankRB.isSelected() || otherRB.isSelected())
                    && !cardNumberText.getText().equals("") && !nameOnCardText.getText().equals("")
                    && !cvvField.getText().equals("") && !monthField.getText().equals("")) {
                vBoxCardField.setDisable(true);
                if (otherRB.isSelected()) paymentStrategy = new PayPalPayment();
                else paymentStrategy = new CreditCardPayment();
                ok = true;
                cartViewListMethod();
            }
        });

        payNow.setOnAction(event -> {
            if (ok) {
                Parent p2;
                try {
                    p2 = FXMLLoader.load(new File("C:\\Users\\daniy\\IdeaProjects\\Fruits-Market\\FruitMarket\\src\\views\\end.fxml").toURI().toURL());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Scene scene = new Scene(p2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        });

    }

    private void cartViewListMethod() {
        ArrayList<String> products = new ArrayList<>();
        for (CartItem item : cartItems) {
            products.add(item.getProduct().getName() + " " + item.getQuantity());
        }
        // Convert the ArrayList to an ObservableList
        ObservableList<String> observableList = FXCollections.observableArrayList(products);
        // Set the items of the ListView
        listView.setItems(observableList);
        subTotalText.setText("SubTotal: $" + Math.round(cart.getTotalPrice() * 100) * 1.0 / 100);
        shippingText.setText("Shipping: $" + Math.round(cartFacade.getShippingCost() * 100) * 1.0 / 100);
        feeText.setText("Card Fee: $" + Math.round(paymentStrategy.calculatePaymentFee(cart.getTotalPrice()) * 100) * 1.0 / 100);
        totalText.setText("Total: $" + Math.round((cartFacade.getOrderTotal() + paymentStrategy.calculatePaymentFee(cartFacade.getOrderTotal())) * 100) * 1.0 / 100);
    }
}
