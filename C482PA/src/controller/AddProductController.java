package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;



public class AddProductController implements Initializable {
    public TextField idFieldAddProduct;
    public TextField nameAddProduct;
    public TextField inventoryAddProduct;
    public TextField priceAddProduct;
    public TextField maxInventoryAddProduct;
    public TextField minInventoryAddProduct;
    public TableView<Part> partsTable;
    public TableColumn<Part, Integer> partIDColumn;
    public TableColumn<Part, Integer> partInventoryColumn;
    public TableColumn<Part, String> partNameColumn;
    public TableColumn<Part, Double> partPriceColumn;
    public TableView<Part> associatedPartTable;
    public TableColumn<Part, Integer> assocIDColumn;
    public TableColumn<Part, String> assocNameColumn;
    public TableColumn<Part, Integer> assocInventoryColumn;
    public TableColumn<Part, Double> assocPriceColumn;
    public TextField searchAddPart;

    public static int productIdNumber = 1;


    int id = productIdNumber;
    String name;
    double price;
    int stock;
    int max;
    int min;
    boolean productAdded = false;

    /**
     * local associated parts list that assigns to each product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**
     * prompts user to confirm they want to cancel then returns to main screen if they select ok.
     *
     * @param actionEvent
     * @throws IOException
     */
    public void cancelBtnAction(ActionEvent actionEvent) throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Cancel");
        confirm.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainPageFXML.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * takes selected part from top table and adds it to the associated parts table and displays.
     * <p>
     * LOGIC ERROR
     * whille writing the remove i realized i had already created these methods in the Product model. had to change the code to fit those instead.
     *
     * @param actionEvent
     */
    public void productAddBtnAction(ActionEvent actionEvent) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
    }

    /**
     * removes the selected part from the associated parts list and table view.
     *
     * @param actionEvent
     */
    public void assocPartRemove(ActionEvent actionEvent) {
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Remove");
        confirm.setHeaderText("Are you sure you want to remove the selected part?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        if (selectedPart != null) {
            associatedParts.remove(selectedPart);
        }
        }else {
           MainPage.partAlertSwitch(4);
        }
    }

    /**
     * Gets text from the search field and displays matches in the parts table
     * prompted to create a method from duplicate text by IDE
     *
     * @param actionEvent enter pressed in search part field on add product screen
     */
    public void productSearchText(ActionEvent actionEvent) {
        MainPage.partSearchText(searchAddPart, partsTable);
    }

    /**
     * creates a new product and adds it to the product array list then retuns to main screen.
     * @param actionEvent
     */
    public void saveBtnAction(ActionEvent actionEvent) {
        try {
            id = productIdNumber;
            name = nameAddProduct.getText();
            price = Double.parseDouble(priceAddProduct.getText());
            stock = Integer.parseInt(inventoryAddProduct.getText());
            max = Integer.parseInt(maxInventoryAddProduct.getText());
            min = Integer.parseInt(minInventoryAddProduct.getText());

            if (name.isEmpty()) {
               MainPage.partAlertSwitch(5);
            } else {
                if (min < max && min <= stock && stock <= max) {
                    Product product = new Product(id, name, price, stock, min, max);
                    product.getAllAssociatedParts().addAll(associatedParts);
                    Inventory.addProduct(product);
                    ++productIdNumber;
                    productAdded = true;
                }
                if (productAdded) {
                    Parent root = FXMLLoader.load(getClass().getResource("/view/mainPageFXML.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setTitle("Inventory Management System");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            }
        } catch (NumberFormatException | IOException e) {
            MainPage.partAlertSwitch(6);
        }
    }

    /**
     * /**
     * * sets product ID number and populates parts table.
     * *
     * * RUMTIME ERROR
     * * had variables named differently and would not populate table errored out saying it pointed to null.
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idFieldAddProduct.setText(String.valueOf(productIdNumber));
        partsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTable.setItems(associatedParts);
        assocIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

