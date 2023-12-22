package Controller;

import DesignPattern.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Main;
import main.MyListener;
import model.Fruit;
import model.Product;
import model.ProductFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MarketController implements Initializable {
    public Button addToCartButton;
    public TextField QuantityField;
    public ImageView cartButton;
    @FXML
    private VBox chosenFruitCard;

    @FXML
    private Label fruitNameLable;

    @FXML
    private Label fruitPriceLabel;

    @FXML
    private ImageView fruitImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private List<Fruit> fruits = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    Fruit fruit;
    static Cart cart = new Cart();

    public static Cart getCart() {
        return cart;
    }

    private static OrderContext orderContext = new OrderContext();

    public static OrderContext getOrderContext() {
        return orderContext;
    }

    public void addToCartAction() {
        ShoppingCartFacade shoppingCartFacade = new ShoppingCartFacade(cart);
        if (!QuantityField.getText().equals("")) {
            try {
                new AddToCartCommand(shoppingCartFacade, fruit, Double.parseDouble(QuantityField.getText())).execute();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setContentText("Number format Exception");
                alert.show();
            }
        }
    }

    private void setChosenFruit(Fruit fruit) {
        this.fruit = fruit;
        fruitNameLable.setText(fruit.getName());
        QuantityField.setText("");
        fruitPriceLabel.setText(Main.CURRENCY + fruit.getPrice());
        image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(fruit.getImgSrc())));
        fruitImg.setImage(image);
        chosenFruitCard.setStyle("-fx-background-color: #" + fruit.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderContext.processOrder();

        cartButton.setOnMouseClicked(event -> {
            Parent p2;
            try {
                p2 = FXMLLoader.load(new File("C:\\Users\\daniy\\IdeaProjects\\Fruits-Market\\FruitMarket\\src\\views\\cart.fxml").toURI().toURL());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Scene scene = new Scene(p2);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        });

        fruits.addAll(getData());
        if (fruits.size() > 0) {
            setChosenFruit(fruits.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Fruit fruit) {
                    setChosenFruit(fruit);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < fruits.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/views/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(fruits.get(i), myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Fruit> getData() {
        List<Fruit> fruits = new ArrayList<>();
        Product product;

        ProductFactory productFactory = new ProductFactory();
        product = productFactory.createProduct("Fruit", "Kiwi", "/img/kiwi.png", 2.99, "6A7324");
        fruits.add((Fruit) product);

        product = productFactory.createProduct("Fruit", "Coconut", "/img/coconut.png", 3.99, "A7745B");
        fruits.add((Fruit) product);

        product = productFactory.createProduct("Fruit", "Peach", "/img/peach.png", 1.50, "F16C31");
        fruits.add((Fruit) product);

        product = productFactory.createProduct("Fruit", "Grapes", "/img/grapes.png", 0.99, "291D36");
        fruits.add((Fruit) product);

        product = productFactory.createProduct("Fruit", "Watermelon", "/img/watermelon.png", 4.99, "22371D");
        fruits.add((Fruit) product);

        product = productFactory.createProduct("Fruit", "Orange", "/img/orange.png", 2.99, "FB5D03");
        fruits.add((Fruit) product);

        product = productFactory.createProduct("Fruit", "StrawBerry", "/img/strawberry.png", 0.99, "80080C");
        fruits.add((Fruit) product);

        product = productFactory.createProduct("Fruit", "Mango", "/img/mango.png", 0.99, "FFB605");
        fruits.add((Fruit) product);

        product = productFactory.createProduct("Fruit", "Cherry", "/img/cherry.png", 0.99, "5F060E");
        fruits.add((Fruit) product);

        product = productFactory.createProduct("Fruit", "Banana", "/img/banana.png", 1.99, "E7C00F");
        fruits.add((Fruit) product);

        return fruits;
    }
}
