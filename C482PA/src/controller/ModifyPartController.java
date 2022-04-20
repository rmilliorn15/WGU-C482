package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inhouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.AddPartController.partIdNumber;

public class ModifyPartController implements Initializable {

    public RadioButton modifyPartOutsourcedRadio;
    public RadioButton modifyPartInhouseRadio;
    public Label machineIdCompanyName;
    public TextField modifyIdText;
    public TextField modNameText;
    public TextField modInvText;
    public TextField modInvMaxText;
    public TextField modInvMinText;
    public TextField machineIDText;
    public Button modifyPartInhouseSave;
    public Button modifyPartInhouseCancel;
    public TextField modPriceText;
    public ToggleGroup tgroup2;


    int id = partIdNumber;
    String name;
    double price;
    int stock;
    int max;
    int min;
    int machineId;
    String companyName;
    boolean partUpdated = false;
    int index;

    /**
     * Cancel changes and returns to main screen.
     * @param actionEvent cancel button clicked
     * @throws IOException for returning to main screen.
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
     * populates the modify part text fields with selected information
     *  if the part is an inhouse part  shows machine ID
     *          * if outsourced shows company name.
     *
     * @param send gets sent part information from the selected part.
     */
    public void sendParts(Part send) {
        modifyIdText.setText(String.valueOf(send.getId()));
        modNameText.setText(send.getName());
        modInvText.setText(String.valueOf(send.getStock()));
        modInvMaxText.setText(String.valueOf(send.getMax()));
        modInvMinText.setText(String.valueOf(send.getMin()));
        modPriceText.setText(String.valueOf(send.getPrice()));

        if(send instanceof Inhouse) {
            modifyPartInhouseRadio.setSelected(true);
            machineIDText.setText(String.valueOf(((Inhouse) send).getMachineId()));
            machineIdCompanyName.setText("Machine ID");
        }
        if (send instanceof Outsourced) {
            modifyPartOutsourcedRadio.setSelected(true);
            machineIDText.setText(((Outsourced) send).getCompanyName());
            machineIdCompanyName.setText("Company Name");
        }
    }

    /**
     * Saves new part information to inventory.
     *
     *
     * RUNTIME ERROR
     * max and min values swap when clicking save. Cant seem to find cause.
     * @param actionEvent
     * @throws IOException
     */
    public void saveBtnAction(ActionEvent actionEvent) throws IOException {
        try {
            if (modifyPartInhouseRadio.isSelected()) {

                id = Integer.parseInt(modifyIdText.getText());
                name = modNameText.getText();
                price = Double.parseDouble(modPriceText.getText());
                stock = Integer.parseInt(modInvText.getText());
                max = Integer.parseInt(modInvMaxText.getText());
                min = Integer.parseInt(modInvMinText.getText());
                machineId = Integer.parseInt(machineIDText.getText());

                if (min < max && min <= stock && stock <= max) {
                    Inhouse newPart = new Inhouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(index, newPart);
                    partUpdated = true;
                }
                else {
                    MainPage.partAlertSwitch(3);
                }
            }
        }  catch (NumberFormatException e){
            MainPage.partAlertSwitch(4);
        }
        try {
            if (modifyPartOutsourcedRadio.isSelected()) {
                id = Integer.parseInt(modifyIdText.getText());
                name = modNameText.getText();
                price = Double.parseDouble(modPriceText.getText());
                stock = Integer.parseInt(modInvText.getText());
                max = Integer.parseInt(modInvMaxText.getText());
                min = Integer.parseInt(modInvMinText.getText());
                companyName = machineIDText.getText();

                if (min < max && min <= stock && stock <= max) {
                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(index, newPart);
                    partUpdated = true;
                } else {
                    MainPage.partAlertSwitch(3);
                }
            }
        }
        catch (NumberFormatException e){
            MainPage.partAlertSwitch(4);
        }
        if (partUpdated){

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/mainPageFXML.fxml"));
            loader.load();

            MainPage mainPageController = loader.getController();
            Parent root =  loader.getRoot();
            Stage stage = (Stage)((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * gets selected part index from main parts table.
     * @param selectedIndex
     */
    public void sendIndex(int selectedIndex) {
        index = selectedIndex;
    }

    /** Sets label to Machine ID when inhouse is checked
     *
     * @param actionEvent
     */
    public void inhouse(ActionEvent actionEvent) {
        machineIdCompanyName.setText("Machine ID");
    }

    /** Sets label to company name when outsourced is selected
     *
     * @param actionEvent outsourced radio checked
     */
    public void outsourced(ActionEvent actionEvent) {
        machineIdCompanyName.setText("Company Name");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


}
