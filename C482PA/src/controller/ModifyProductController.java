package controller;


/**
 * @author Richard Milliorn
 * WGU C482
 */
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

import static controller.AddProductController.productIdNumber;

public class ModifyProductController implements Initializable {

    public TextField idFieldModifyProduct;
    public TextField nameModifyProduct;
    public TextField inventoryModifyProduct;
    public TextField priceModifyProduct;
    public TextField maxInventoryModiifyProduct;
    public TextField minInventoryModifyProduct;
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
    public TextField searchModifyPart;
    int index;

    int id = productIdNumber;
    String name;
    double price;
    int stock;
    int max;
    int min;
    boolean productAdded = false;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /**
     * Cancels changes and returns to main screen.
     * @param actionEvent cancel button clicked
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
     * adds part to the associated parts table.
     * @param actionEvent add button clicked while part collected.
     */
    public void productAddBtnAction(ActionEvent actionEvent) {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
    }

    /**
     * removed part from the associated parts table.
     * @param actionEvent remove button clicked
     */
    public void AssocPartRemove(ActionEvent actionEvent) {
        Part selectedPart = associatedPartTable.getSelectionModel().getSelectedItem();
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Remove");
        confirm.setHeaderText("Are you sure you want to remove the selected part?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (selectedPart != null) {
                associatedParts.remove(selectedPart);
            } else {
                MainPage.partAlertSwitch(4);
            }
        }
    }

    /**
     * Saves the changes to the selected product and returns to the main screen.
     * @param actionEvent save button clicked.
     */
    public void saveBtnAction(ActionEvent actionEvent) {
        try {
            id = Integer.parseInt(idFieldModifyProduct.getText());
            name = nameModifyProduct.getText();
            price = Double.parseDouble(priceModifyProduct.getText());
            stock = Integer.parseInt(inventoryModifyProduct.getText());
            max = Integer.parseInt(maxInventoryModiifyProduct.getText());
            min = Integer.parseInt(minInventoryModifyProduct.getText());

            if (name.isEmpty()) {
                MainPage.partAlertSwitch(2);
            } else {
                if (min < max && min <= stock && stock <= max) {
                    Product product = new Product(id, name, price, stock, min, max);
                    product.getAllAssociatedParts().addAll(associatedParts);
                    Inventory.updateProduct(index, product);
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

    public void productSearchText(ActionEvent actionEvent) {
        MainPage.partSearchText(searchModifyPart, partsTable);
    }

    /**
     * gets information for selected product from main page and populates text fields.
     *
     * RUNTIME ERROR
     * could not get it to populate the assoc parts table. had tosend the info from main screen and then reppopulate assoc parts table when hitting add.
     * @param send
     */
    public void sendProduct(Product send) {
        idFieldModifyProduct.setText(String.valueOf(send.getId()));
        nameModifyProduct.setText(send.getName());
        inventoryModifyProduct.setText(String.valueOf(send.getStock()));
        maxInventoryModiifyProduct.setText(String.valueOf(send.getMax()));
        minInventoryModifyProduct.setText(String.valueOf(send.getMin()));
        priceModifyProduct.setText(String.valueOf(send.getPrice()));

        // FIXME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // should populate associated parts table from main screen.
        associatedParts = send.getAllAssociatedParts();
        associatedPartTable.setItems(associatedParts);
    }

    /**
     * gets index  for the selected product from the main screen.
     * @param selectedIndex
     */
    public void sendIndex(int selectedIndex) {
        index = selectedIndex;
    }

    /**
     * populates tables on modify parts screens when called.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
