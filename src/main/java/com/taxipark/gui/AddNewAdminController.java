package com.taxipark.gui;

import com.taxipark.gui.component.CheckingAdmin;
import com.taxipark.gui.component.ConnectToDataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class AddNewAdminController implements Initializable {
    @FXML
    private Label messageInfo;

    @FXML
    private Button saveDataButton;

    @FXML
    private Button addAnotherAdminButton;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField middleName;

    @FXML
    private TextField position;

    @FXML
    private ChoiceBox<String> gender;

    @FXML
    private DatePicker dateBirth;

    @FXML
    private DatePicker dateEmployment;

    CheckingAdmin checkingAdmin = new CheckingAdmin();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            gender.getItems().addAll("жінка", "чоловік");
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }


    private boolean allDataIsCorrectSize() {
        try {
            return checkingAdmin.checkSizeAdminLoginAndPassword(login.getText())
                    && checkingAdmin.checkSizeAdminLoginAndPassword(password.getText())
                    && checkingAdmin.checkSizeAdminNameAndPosition(firstName.getText())
                    && checkingAdmin.checkSizeAdminNameAndPosition(lastName.getText())
                    && checkingAdmin.checkSizeAdminNameAndPosition(middleName.getText())
                    && checkingAdmin.checkSizeAdminNameAndPosition(position.getText())
                    && dateBirth.getValue() != null && dateEmployment.getValue() != null
                    && gender.getValue() != null;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private boolean allDataIsCorrect() {
        try {
            if (!allDataIsCorrectSize()){
                setMessage("Введіть ВСІ дані і коректного розміру!\n\t(логін і пароль - 8-30\n\tрешта даних - <= 30)");
                return false;
            }
            if (!checkingAdmin.isNotLoginInDB(login.getText())){
                setMessage("Такий логін вже існує!");
                return false;
            }
            if (!checkingAdmin.checkCorrectDate(dateBirth.getValue(), dateEmployment.getValue())){
                setMessage("Введіть коректні дати!");
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    public void onCallMenuButtonClick(ActionEvent event) {

        try {
            LoginController loginController = new LoginController();
            loginController.openMenu(event);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    public void onSaveDataButtonClick(ActionEvent event) {
        try {
            messageInfo.setVisible(false);

            if (allDataIsCorrect()){
                if (addDataInDataBase()) {
                    setMessage("Користувача успішно додано)");
                    clearAndDisableAllEntering();
                    saveDataButton.setVisible(false);
                    addAnotherAdminButton.setVisible(true);
                }else {
                    setMessage("Виникла помилка при записі!");
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void setMessage(String message){

        try {
            messageInfo.setText(message);
            messageInfo.setVisible(true);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private boolean addDataInDataBase() {
        String inserting = "insert into \"Admins\"(" +
                "                \"AdminLogin\", " +
                "                \"AdminFirstName\", " +
                "                \"AdminLastName\", " +
                "                \"AdminMiddleName\", " +
                "                \"AdminPosition\", " +
                "                \"AdminGender\", " +
                "                \"AdminDateBirth\", " +
                "                \"AdminDateEmployment\", " +
                "                \"AdminPassword\" " +
                "        ) values(?,?,?,?,?,?,?,?,?)";

        try {
            Connection connection = ConnectToDataBase.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(inserting);
            preparedStatement.setString(1, login.getText());
            preparedStatement.setString(2, firstName.getText());
            preparedStatement.setString(3, lastName.getText());
            preparedStatement.setString(4, middleName.getText());
            preparedStatement.setString(5, position.getText());
            preparedStatement.setString(6, String.valueOf(gender.getValue().charAt(0)));

            Date date = Date.valueOf(dateBirth.getValue());

            preparedStatement.setDate(7, date);

            date = Date.valueOf(dateEmployment.getValue());
            preparedStatement.setDate(8, date);

            preparedStatement.setString(9, password.getText());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void onAddAnotherAdminButtonClick(ActionEvent event){
        try {
            MainMenuController mainMenuController = new MainMenuController();
            mainMenuController.openNewScene(event, "addNewAdmin.fxml");
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void clearAndDisableAllEntering(){

        login.setDisable(true);
        login.clear();

        password.setDisable(true);
        password.clear();

        firstName.setDisable(true);
        firstName.clear();

        lastName.setDisable(true);
        lastName.clear();

        middleName.setDisable(true);
        middleName.clear();

        position.setDisable(true);
        position.clear();

        gender.setDisable(true);

        dateBirth.setDisable(true);

        dateEmployment.setDisable(true);
    }
}

