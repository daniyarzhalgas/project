package Controller;

import DesignPattern.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import model.Fruit;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class CartController implements Initializable {


    public ListView<String> listViewCart;
    public Label totalLabel;
    public VBox chosenFruitCard;
    public Label fruitNameLable;
    public Label fruitPriceLabel;
    public ImageView fruitImg;
    public Label cost;
    public Label quantity;
    public ImageView homeButton;
    public ImageView emptyCartImage;
    public Button cartToPayment;

    Cart cart = MarketController.cart;
    ShoppingCartFacade cartFacade = new ShoppingCartFacade(cart);
    Fruit fruit;
    List<CartItem> cartItems;
    private static OrderContext orderContext = MarketController.getOrderContext();

    ArrayList<String> products;

    public static OrderContext getOrderContext() {
        return orderContext;
    }

    public void removeFromCartButton(ActionEvent actionEvent) {
        new RemoveFromCartCommand(cartFacade, fruit).execute();
// Wait for the removal operation to complete if necessary
        System.out.println(fruit.getName());
        this.cartItems = cartFacade.getCart().getItems();

// Continue with the rest of the code

        Parent p2;
        try {
            p2 = FXMLLoader.load(new File("C:\\Users\\daniy\\IdeaProjects\\Fruits-Market\\FruitMarket\\src\\views\\cart.fxml").toURI().toURL());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Scene scene = new Scene(p2);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void setChosenFruit(Fruit fruit) {
        this.fruit = fruit;
        chosenFruitCard.setVisible(true);
        fruitNameLable.setText(fruit.getName());
        fruitPriceLabel.setText(Main.CURRENCY + fruit.getPrice());
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(fruit.getImgSrc())));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");

        double Quantity = 0;
        for (String s : products) {
            if (s.split(" ")[0].equals(fruit.getName()))
                Quantity = Double.parseDouble(s.split(" ")[1]);
        }

        quantity.setText(String.valueOf(Quantity));
        cost.setText("$" + (Quantity * fruit.getPrice()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderContext.processOrder();

        cartToPayment.setOnAction(event -> {
            if (!cartItems.isEmpty()) {
                Parent p2;
                try {
                    p2 = FXMLLoader.load(new File("C:\\Users\\daniy\\IdeaProjects\\Fruits-Market\\FruitMarket\\src\\views\\payment.fxml").toURI().toURL());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                Scene scene = new Scene(p2);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
        });
        listViewCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            List<Fruit> fruits = MarketController.getData();

            for (Fruit f : fruits) {
                try {
                    if (f.getName().equals(newValue.split(" ")[0])) {
                        setChosenFruit(f);
                    }
                } catch (Exception e) {
                    break;
                }
            }
        });

        homeButton.setOnMouseClicked(event -> {
            Parent p2;
            try {
                p2 = FXMLLoader.load(new File("C:\\Users\\daniy\\IdeaProjects\\Fruits-Market\\FruitMarket\\src\\views\\market.fxml").toURI().toURL());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Scene scene = new Scene(p2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        });

        cartViewListMethod();
    }

    private void cartViewListMethod() {
        this.cartItems = cart.getItems();
        products = new ArrayList<>();
        if (!cartItems.isEmpty()) {
            for (CartItem item : cartItems) {
                products.add(item.getProduct().getName() + " " + item.getQuantity());
            }
            // Convert the ArrayList to an ObservableList
            ObservableList<String> observableList = FXCollections.observableArrayList(products);

            // Set the items of the ListView
            listViewCart.setItems(observableList);
            totalLabel.setText("total = " + Math.round(cart.getTotalPrice() * 100) * 1.0 / 100 + " shipping cost " + cartFacade.getShippingCost());
        }
    }
}
