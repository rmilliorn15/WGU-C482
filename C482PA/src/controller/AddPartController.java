package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


public class AddPartController implements Initializable {



    public TextField inhouseInvText;
    public TextField inhouseInvMaxText;
    public TextField inhouseInvMinText;
    public TextField machineIdText;
    public TextField inhouseNameText;
    public TextField inhousePriceText;
    public ToggleGroup tGroup;
    public RadioButton outsourcedRadio;
    public RadioButton inHouseRadio;
    public TextField inhousePartId;
    public Label machineIdCompanyName;

    /**
     * static number for new part id being added. should update and show throughout project
     */
    static int partIdNumber = 1;

    /**
     * closes add part screen and returns to main page
     * confirmation pop up before returning
     *
     * FUTURE ENHANCEMENT
     * figure out how to get the confirm prompt to work with the alert switch and move that there instead of in the method individually.
     * @param actionEvent cancel button clicked
     *
     * @throws IOException
     */
    public void cancelBtnAction(ActionEvent actionEvent) throws IOException {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Cancel");
        confirm.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK)
       {
            Parent root = FXMLLoader.load(getClass().getResource("/view/mainPageFXML.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setTitle("Inventory Management System");
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /**
     * Sets the Machine Id label for the last text field.
     * @param actionEvent inhouse radio button selected.
     * @throws IOException
     */
    public void InhouseRadioBtn(ActionEvent actionEvent) throws IOException {
        machineIdCompanyName.setText("Machine ID");
        machineIdText.setPromptText("Ex. 111");
    }

    /**
     * Sets Machine Id label to Company name and changes prompt text
     * @param actionEvent outsourced radio button selected
     * @throws IOException
     */
    public void OutsourcedRadioBtn(ActionEvent actionEvent) throws IOException {
        machineIdCompanyName.setText("Company Name");
        machineIdText.setPromptText("Ex. Tesla ");
    }

    /**
     *
     * @param actionEvent save button clicked
     * @throws IOException for returning to main screen
     *
     *          * RUNTIME ERROR
     *          *  error trying to add part. says that inhousename.getText is null when it should be populated.
     *          *
     *          *  RUNTIME ERROR!!!!
     *          *  can get it to add the objects with test data but not with data from the text fields.
     *          *  imported awt.* instead of controls so it wouldnt read the data.
     *          *  took me way too long to notice that.
     *          *  put in try catch so that if fields are blank it will send alert and not crash program
     *          Checks to see which radio button is selected and changes last id field to match
     *
     *          takes id, name, price, stock,max, min, machineid to create new parts.
     *          also uses company name if outsourced.
     *          checks to make sure that inv is between max and min and that Min is less than Max
     *
     *         FUTURE ENHANCEMENT
     *          have if/else statements to figure out which number isnt correct instead of catch alls.
     */
    public void saveBtnAction(ActionEvent actionEvent) throws IOException {
        int id = partIdNumber;
        String name;
        double price;
        int stock;
        int max;
        int min;
        int machineId;
        String companyName;
        boolean partAdded = false;

        if (inHouseRadio.isSelected()) {
            try {
                name = inhouseNameText.getText();
                price = Double.parseDouble(inhousePriceText.getText());
                stock = Integer.parseInt(inhouseInvText.getText());
                max = Integer.parseInt(inhouseInvMaxText.getText());
                min = Integer.parseInt(inhouseInvMinText.getText());
                machineId = Integer.parseInt(machineIdText.getText());

                if (name.isEmpty()) {
                    MainPage.partAlertSwitch(2);
                } else {
                    if (min < max && min <= stock && stock <= max) {
                        ++partIdNumber;
                        Inhouse part = new Inhouse(id, name, price, stock, min, max, machineId);
                        Inventory.addPart(part);
                        partAdded = true;
                    } else {
                        MainPage.partAlertSwitch(3);
                    }
                }
                partAddedCheck(actionEvent, partAdded);
            } catch (NumberFormatException e) {
                MainPage.partAlertSwitch(1);
            }
        }
        if (outsourcedRadio.isSelected()) {
            try {
                id = partIdNumber;
                name = inhouseNameText.getText();
                price = Double.parseDouble(inhousePriceText.getText());
                stock = Integer.parseInt(inhouseInvText.getText());
                max = Integer.parseInt(inhouseInvMaxText.getText());
                min = Integer.parseInt(inhouseInvMinText.getText());
                companyName = machineIdText.getText();

                if (name.isEmpty()) {
                    MainPage.partAlertSwitch(2);
                } else {
                    if (min < max && min <= stock && stock <= max) {
                        ++partIdNumber;
                        Outsourced part = new Outsourced(id, name, price, stock, min, max, companyName);
                        Inventory.addPart(part);
                        partAdded = true;
                    } else {
                        MainPage.partAlertSwitch(3);
                    }
                }
                partAddedCheck(actionEvent, partAdded);
            } catch (NumberFormatException e) {
                MainPage.partAlertSwitch(1);
            }
        }
    }

    /**
     * Method created to check if part was added and if so returns to main screen.
     * @param actionEvent save button clicked meaning part is trying to be saved.
     * @param partAdded boolean in save part button. indicates that there were no errors and part was added.
     * @throws IOException
     */
    private void partAddedCheck(ActionEvent actionEvent, boolean partAdded) throws IOException {
        if (partAdded) {
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
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /**
         * could not get number to update and show globally when clicking add part unless i put it in the initalize
         */
        inhousePartId.setText(String.valueOf(partIdNumber));
    }
}




