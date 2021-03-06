package controller;


/**
 * /**
 *  * @author Richard Milliorn
 *  * WGU C482
 */

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;




public class MainPage implements Initializable {

    /**
     * autogenerated from scene builder
     * Deleted and had to re-add @FXML because i didn't have the JDK and javaFx setup correctly.
     * it would error before i deleted it. Make sure programs are setup correct
     */

    public Button addPartBtn;
    public Button addProduct;
    public Button deletePart;
    public Button deleteProduct;
    public Button exit;
    public Button modifyPart;
    public Button modifyProduct;
    public TableView<Part> partsTable;
    public TableColumn<Part, Integer> partIDColumn;
    public TableColumn<Part, Integer> partInventoryColumn;
    public TableColumn<Part, String> partNameColumn;
    public TableColumn<Part, Double> partPriceColumn;
    public Pane partPane;
    public TextField partSearch;
    public TableView<Product> productTable;
    public TableColumn<Product, Double> productCostColumn;
    public TableColumn<Product, Integer> productIdColumn;
    public TableColumn<Product, Integer> productInventoryColumn;
    public TableColumn<Product, String> productNameColumn;
    public Pane productPane;
    public TextField productSearch;




    /**
     * exits application with code 0 when clicked
     *      * <p>
     *      * had to have the code outside the Initalize bracket otherwise said needed local variable.
     *      * can't get to run getting exception in start method.
     *      * <p>
     *      * said should be public or annotated with @FXML in error.
     *      * <p>
     *      * first action added and it closes the program successfully.
     * @param event exit button clicked
     */
    public void exitButtonAction(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Exit");
        confirm.setHeaderText("Are you sure you want to exit?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     *  Add button action
     *  opens add part screne when button is clicked.
     * @param actionEvent add part button clicked
     * @throws IOException
     */
    public void addPartBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Part Inhouse");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Deletes item from table
     * used for delete commands on buttons
     * make sure its selecting table and not the list
     * Confirmation text before delete and shows error if selection is null.
     * @param actionEvent delete part button clicked
     */
    public void partDeleteBtnAction(ActionEvent actionEvent) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Are you sure you want to delete the selected part?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Part SP = partsTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to delete");

            if (SP == null) {
                alert.showAndWait();
            }
            else {
                Inventory.deletePart(SP);
            }
        }
    }

    /**
     * FUTURE ENHANCEMENT
     * set up alert switch for different issues on page.
     *
     * RUNTIME ERROR product does not delete when button is clicked.
     * started after i added the associated parts on add product screen.
     * somehow the addProduct in the product model was on add parts still. so it would show my test data but not allow me to add or remove
     *
     * RUNTIME ERROR
     * could not get correct alert to show for the associated part attached to product.
     * Forgot to add break in the alert switch.
     *
     * @param actionEvent product delete button clicked
     */
    public void productDeleteBtnAction(ActionEvent actionEvent) {


        Product SP = productTable.getSelectionModel().getSelectedItem();


            if (SP == null) {
                partAlertSwitch(4);
            }
            else  {

                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirm Delete");
                confirm.setHeaderText("Are you sure you want to delete the selected product?");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (SP.getAllAssociatedParts().isEmpty()){
                    Inventory.deleteProduct(SP);
                }
                else {
                    partAlertSwitch(8);
                    }
                }
            }
        }


    /**
     *  adding test data to tables
     */
    public static void addTestData() {

        Inhouse test2 = new Inhouse(2, "Green", 7.90, 7, 2, 30, 24);
        Inhouse test1 = new Inhouse(1,"Red",3.99, 24,10,100,4);
        Outsourced test4 = new Outsourced(4,"Blue",3.99, 4,1,10, "purple");
        Outsourced test3 = new Outsourced(3,"Purple",9.99, 7, 1,10, "Tesla");
        Inhouse test6 = new Inhouse(6,"black",3.99, 4,1,10, 42);
        Inventory.addPart(test1);
        Inventory.addPart(test2);
        Inventory.addPart(test3);
        Inventory.addPart(test4);
        Inventory.addPart(test6);

        Product test11 = new Product(1,"Orange",3.99, 4,0,10);
        Product test12 = new Product(2,"Brown",3.99, 4,0,10);
        Product test13 = new Product(3,"Pink",3.99, 4,0,10);
        Product test14 = new Product(4,"Violet",3.99, 4,0,10);
        Product test15 = new Product(5,"Indigo",3.99, 4,0,10);
        Inventory.addProduct(new Product(6, "Fred", 3.99,5, 1,10));
        Inventory.addProduct(test11);
        Inventory.addProduct(test12);
        Inventory.addProduct(test13);
        Inventory.addProduct(test14);
        Inventory.addProduct(test15);
    }

    /**
     * method for controlling what happens when add product button is clicked
     *
     * @param actionEvent add product button clicked
     * @throws IOException
     */
    public void addProductBtnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Add product.fxml"));
        Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Add Product");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * method for controlling what happens when modify product button is clicked
     * takes information from selected product and sends it to the modify product controller.
     * @param actionEvent modify product button clicked
     * @throws IOException
     */
    public void modifyProductBtnAction(ActionEvent actionEvent) throws IOException {
       try {
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(getClass().getResource("/view/Modify Product.fxml"));
           loader.load();

           ModifyProductController modProductController = loader.getController();
           modProductController.sendProduct(productTable.getSelectionModel().getSelectedItem());
           modProductController.sendIndex(productTable.getSelectionModel().getSelectedIndex());

           Parent root = loader.getRoot();
           Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
           stage.setTitle("Modify Product");
           stage.setScene(new Scene(root));
           stage.show();
       } catch (NullPointerException e){
           partAlertSwitch(4);
       }
    }

    /**
     * Opens modify part screen and sends the information for the selected part from main.
     *
     * @param actionEvent modify part button clicked.
     * @throws IOException
     */
    public void modifyPartBtnAction(ActionEvent actionEvent) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
            loader.load();

            ModifyPartController modPartController = loader.getController();
            modPartController.sendParts(partsTable.getSelectionModel().getSelectedItem());
            modPartController.sendIndex(partsTable.getSelectionModel().getSelectedIndex());

            Parent root = loader.getRoot();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Modify Part Inhouse");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (NullPointerException e){
            partAlertSwitch(4);
        }
    }

    /**
     * gets user text and searches for matching parts and displays in table
     * @param actionEvent enter button pressed in search
     *
     * RUNTIME ERROR // LOGIC ERROR?
     * tried following webinar for search by Id and it no longer searched by name or id.
     *
     * fixed. I used Test1,  test2 etc for names and it causes system to not work.
     *
     * next issue. searches by ID but not by name
     * fixed. had the partsTable.add in the wrong braces.
     *
     */
    public void partSearchText(ActionEvent actionEvent) {
        partSearchText(partSearch, partsTable);
    }

    /**
     * method autocreated from duplicate text
     * FUTURE ENHANCEMENT.
     *
     * Find out what causes the overflow in search and correct the issue. if you put too many characters it defaults to select part error alert.
     *
     * @param partSearch selecting search field
     * @param partsTable table containing list of available parts.
     */
    static void partSearchText(TextField partSearch, TableView<Part> partsTable) {
        try {
            String search = partSearch.getText();
            ObservableList<Part> part = Inventory.lookupPart(search);
            if (part.size() == 0) {
                int id = Integer.parseInt(search);
                Part searchPart = Inventory.lookupPart(id);
                if (searchPart != null) {
                    part.add(searchPart);
                }
            }
            partsTable.setItems(part);

        } catch (NumberFormatException e) {
           partAlertSwitch(4);
        }

    }

    /**
     * gets user text and searches for matching products and displays in table
     * @param actionEvent enter pressed while in the product search bar
     */
    public void productSearchText(ActionEvent actionEvent) {
        try {
            String search = productSearch.getText();
            ObservableList<Product> product = Inventory.lookupProduct(search);
            if (product.size() == 0) {
                int id = Integer.parseInt(search);
                Product searchProduct = Inventory.lookupProduct(id);
                if (searchProduct != null) {
                    product.add(searchProduct);
                }
            }
            productTable.setItems(product);
        } catch (NumberFormatException e) {
            partAlertSwitch(4);
        }
    }

    /**
     * Populates tables on the main screen with Part and Product array data whenever it is called.
     * RUNTIME ERROR
     * was not writing to the table. did not add the inforamtion to the correct area. i added directly to allParts/products
     * not to the Inventory methods. Fixed with help from CI MEL
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // product table population
        productTable.setItems(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // parts table population
        partsTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * switch method created to hold all the alerts needed for the program
     * FURUTE ENHANCEMENT
     * figure out how to get the confirm with button to continue to work on here.
     * cant seem to get it to work right. it always proceeds instead of return to the screen when canceling.
     * @param alertNumber
     */
    public static void partAlertSwitch(int alertNumber) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        switch (alertNumber) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Part Not Added");
                alert.setContentText("Blank fields or incorrect values entered. Please Retry");
                alert.showAndWait();
                break;
            case 2:
                alert.setTitle("Error");
                alert.setHeaderText("Part Not Added");
                alert.setContentText("Please enter a Name");
                alert.show();
                break;
            case 3:
                alert.setTitle("Error");
                alert.setHeaderText("Part Not Added");
                alert.setContentText("Please enter valid Inventory numbers");
                alert.show();
                break;
            case 4:
                alert.setTitle("Please Select a Part");
                alert.setHeaderText("Please select a part to modify or remove and try again.");
                alert.show();
                break;
            case 5:
                alert.setTitle("Product name Empty");
                alert.setHeaderText("Please enter a name");
                alert.show();
                break;
            case 6:
                alert.setTitle("Error");
                alert.setHeaderText("Product Not Added");
                alert.setContentText("Some values might have been left blank or not entered correctly. Please try again.");
                alert.show();
                break;
            case  7:
                alert.setTitle("Error");
                alert.setHeaderText("No Matching parts found");
                alert.show();
                break;
            case 8:
                alert.setTitle("Error");
                alert.setHeaderText("Cannot delete Product with associated parts. Please remove Associated parts and try again.");
                alert.show();
                break;


                default:
                alert.setTitle("Error");
                alert.setHeaderText("Default Error Reached I am not sure what happened either.");
                alert.showAndWait();


        }
    }
}



