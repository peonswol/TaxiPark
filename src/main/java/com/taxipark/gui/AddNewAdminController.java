package com.taxipark.gui;

import com.taxipark.gui.component.ConnectToDataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


//todo DO TRANSFORM
public class AddNewAdminController implements Initializable {
    @FXML
    Label labelInfo;

    @FXML
    Button callMenu;

    @FXML
    Button saveData;

    @FXML
    TextField login;

    @FXML
    TextField password;

    @FXML
    TextField firstName;

    @FXML
    TextField lastName;

    @FXML
    TextField middleName;

    @FXML
    TextField position;

    @FXML
    ChoiceBox<String> gender;

    @FXML
    DatePicker dateBirth;

    @FXML
    DatePicker dateEmployment;

    Connection connection = ConnectToDataBase.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        gender.getItems().addAll("жінка", "чоловік");
    }

    private boolean allDataIsNotEmpty(){
        return !login.getText().isEmpty()
                && !password.getText().isEmpty()
                && !firstName.getText().isEmpty()
                && !middleName.getText().isEmpty()
                && !lastName.getText().isEmpty()
                && !position.getText().isEmpty()
                && gender.getValue() != null
                && !dateBirth.getValue().format(DateTimeFormatter.BASIC_ISO_DATE).equals("")
                && !dateEmployment.getValue().format(DateTimeFormatter.BASIC_ISO_DATE).equals("");
    }

    public void onCallMenuClick(ActionEvent event) {
        LoginController loginController = new LoginController();
        loginController.openMenu(event);
    }

    public void onSaveDataClick(ActionEvent event) {
        if (!allDataIsNotEmpty()){
            labelInfo.setText("Введіть ВСІ дані коректно");
        }
        else {
            addDataInDataBase();
            labelInfo.setText("Користувача успішно додано");
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
            PreparedStatement preparedStatement = connection.prepareStatement(inserting);
            preparedStatement.setString(1, firstName.getText());
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
}

