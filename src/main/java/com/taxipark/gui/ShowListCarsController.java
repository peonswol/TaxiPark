package com.taxipark.gui;

import com.taxipark.gui.component.Car;
import com.taxipark.gui.component.ConnectToDataBase;
import com.taxipark.gui.component.FilteringByObject;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowListCarsController implements Initializable {

    @FXML
    private Button addFilter;

    @FXML
    private Button clearFilter;

    @FXML
    private Label labelListLimit;

    @FXML
    private Label labelStartLimit;

    @FXML
    private Label labelEndLimit;

    @FXML
    private Label selectedCheck;


    @FXML
    private TextField startLimit;

    @FXML
    private TextField endLimit;

    @FXML
    private TextField listLimit;

    @FXML
    private CheckBox vinCheckBox;

    @FXML
    private CheckBox markAndModelCheckBox;

    @FXML
    private CheckBox yearManufactureCheckBox;

    @FXML
    private CheckBox costCheckBox;

    @FXML
    private CheckBox colorCheckBox;

    @FXML
    private CheckBox maxSpeedCheckBox;

    @FXML
    private CheckBox idCheckBox;

    @FXML
    private CheckBox fuelTypeCheckBox;

    @FXML
    private CheckBox fuelConsumptionFor100kmCheckBox;


    @FXML
    private TableView<Car> table;

    @FXML
    private TableColumn<Car, Integer> id;

    @FXML
    private TableColumn<Car, String> vin;

    @FXML
    private TableColumn<Car, String> markAndModel;

    @FXML
    private TableColumn<Car, Short> yearManufacture;

    @FXML
    private TableColumn<Car, Double> cost;

    @FXML
    private TableColumn<Car, String> color;

    @FXML
    private TableColumn<Car, Double> maxSpeed;

    @FXML
    private TableColumn<Car, String> fuelType;

    @FXML
    private TableColumn<Car, Double> fuelConsumptionFor100km;

    @FXML
    private TextField keyWordField;

    @FXML
    private Label infoToStop;

    @FXML
    private ImageView arrowToStop;

    private boolean isInterval;

    private CheckBox[] checkBoxes;

    private boolean isSearching = false;

    private List<Car> getCarsFromDB(){

        Connection connection = ConnectToDataBase.getConnection();

        String getCars =
                """
                        select car."CarID", car."CarVIN", "general"."MarkAndModel",
                        "general"."YearManufacture", "general"."Cost", "general"."Color", 
                        "general"."MaxSpeed", fuel."FuelType", fuel."FuelConsumptionFor100km"
                        from "CarTable" as car
                        inner join "GeneralInfoTable" as "general"
                        on car."GeneralInfoID" = "general"."GeneralInfoID"
                        inner join "FuelInfoTable" as fuel
                        on car."FuelInfoID" = fuel."FuelInfoID\"""";

        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(getCars);
            Car tempCar;
            List<Car> cars = new ArrayList<>();

            while (result.next()) {
                tempCar = new Car();
                tempCar.setCarID(result.getInt("CarID"));
                tempCar.setCarVIN(result.getString("CarVIN"));
                tempCar.setMarkAndModel(result.getString("MarkAndModel"));
                tempCar.setYearManufacture(result.getInt("YearManufacture"));
                tempCar.setCost(result.getDouble("Cost"));
                tempCar.setColor(result.getString("Color"));
                tempCar.setMaxSpeed(result.getDouble("MaxSpeed"));
                tempCar.setFuelType(result.getString("FuelType"));
                tempCar.setFuelConsumptionFor100km(result.getDouble("FuelConsumptionFor100km"));

                cars.add(tempCar);
            }

            return cars;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        initCheckBoxesArray();

        List<Car> cars = getCarsFromDB();

        id.setCellValueFactory(new PropertyValueFactory<>("carID"));
        vin.setCellValueFactory(new PropertyValueFactory<>("carVIN"));
        markAndModel.setCellValueFactory(new PropertyValueFactory<>("markAndModel"));
        yearManufacture.setCellValueFactory(new PropertyValueFactory<>("yearManufacture"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        color.setCellValueFactory(new PropertyValueFactory<>("color"));
        maxSpeed.setCellValueFactory(new PropertyValueFactory<>("maxSpeed"));
        fuelType.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
        fuelConsumptionFor100km.setCellValueFactory(new PropertyValueFactory<>("fuelConsumptionFor100km"));

        table.setItems(FXCollections.observableArrayList(cars));
    }

    private void initCheckBoxesArray(){
        checkBoxes = new CheckBox[]{idCheckBox, vinCheckBox, markAndModelCheckBox,
                yearManufactureCheckBox, costCheckBox, colorCheckBox, maxSpeedCheckBox,
                fuelTypeCheckBox, fuelConsumptionFor100kmCheckBox};

        turnOnDisable(true);
    }

    public void onWorkWithSearchingClick(){

        isSearching = true;

        turnOnDisable(true);
        keyWordField.setDisable(false);

        FilteredList<Car> filteredData = new FilteredList<>(table.getItems(), b -> true);

        keyWordField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(car -> {

            if (newValue.isEmpty() || newValue.isBlank() || newValue.equals(" ")){
                return true;
            }

            String searchKeyWord = newValue.toLowerCase();

            if (car.getMarkAndModel().toLowerCase().contains(searchKeyWord)){
                return true;
            }else if(car.getCarVIN().toLowerCase().contains(searchKeyWord)){
                return true;
            }else if(car.getColor().toLowerCase().contains(searchKeyWord)){
                return true;
            }else if(car.getFuelType().toLowerCase().contains(searchKeyWord)){
                return true;
            }else if(Integer.toString(car.getCarID()).contains(searchKeyWord)){
                return true;
            }else return Integer.toString(car.getYearManufacture()).toLowerCase().contains(searchKeyWord);
        }));

        SortedList<Car> sortedList = new SortedList<>(filteredData);

        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    public void onStartFilterClick(){
        if (!isSearching) {
            turnOnDisable(false);
        }else {
            infoToStop.setVisible(true);
            arrowToStop.setVisible(true);
        }
    }

    public void onStopSearchClick(ActionEvent event){
        isSearching = false;
        onClearFilterClick(event);
    }

    public void turnOnDisable(boolean bool){
        for (CheckBox checkBox:checkBoxes){
            checkBox.setDisable(bool);
        }
    }

    public void onCheckBox(ActionEvent event){
        isInterval = checkElementsToFilter();
        swapMenuToEnter(true);
    }

    private boolean checkElementsToFilter(){

        if (idCheckBox.isSelected()){

            selectedCheck.setText("Обраний фільтр: ID");
            return true;

        }else if (yearManufactureCheckBox.isSelected()){

            selectedCheck.setText("Обраний фільтр: Рік збірки");
            return true;

        }else if (costCheckBox.isSelected()){

            selectedCheck.setText("Обраний фільтр: Вартість");
            return true;

        }else if (maxSpeedCheckBox.isSelected()){

            selectedCheck.setText("Обраний фільтр: Максимальна швидкість");
            return true;

        }else if (fuelConsumptionFor100kmCheckBox.isSelected()){

            selectedCheck.setText("Обраний фільтр: Витрата пального за 100 км");
            return true;

        }else if (vinCheckBox.isSelected()){

            listLimit.setPromptText("напр: ВВ 4177 СН , AH 4035 HX , AA 6126 ME");
            selectedCheck.setText("Обраний фільтр: VIN");
            return false;

        }else if (markAndModelCheckBox.isSelected()){

            listLimit.setPromptText("напр: Audi , Audi A7 , BMW");
            selectedCheck.setText("Обраний фільтр: Марка і модель");
            return false;

        }else if (colorCheckBox.isSelected()){

            listLimit.setPromptText("напр: чорний , білий , синій");
            selectedCheck.setText("Обраний фільтр: Колір");
            return false;

        }else if (fuelTypeCheckBox.isSelected()) {

            listLimit.setPromptText("напр: А-95 , дизель , А-100");
            selectedCheck.setText("Обраний фільтр: Тип пального");
            return false;

        }

        return false;
    }

    private void turnOnLimitStartAndEnd(boolean bool){
        labelStartLimit.setVisible(bool);
        labelEndLimit.setVisible(bool);
        startLimit.setVisible(bool);
        endLimit.setVisible(bool);
    }

    private void turnOnLimitList(boolean bool){
        labelListLimit.setVisible(bool);
        listLimit.setVisible(bool);
    }

    private void turnOnCheckMenu(boolean bool){
        idCheckBox.setVisible(bool);
        vinCheckBox.setVisible(bool);
        markAndModelCheckBox.setVisible(bool);
        yearManufactureCheckBox.setVisible(bool);
        costCheckBox.setVisible(bool);
        colorCheckBox.setVisible(bool);
        maxSpeedCheckBox.setVisible(bool);
        fuelTypeCheckBox.setVisible(bool);
        fuelConsumptionFor100kmCheckBox.setVisible(bool);
    }

    private void swapVisibleButtons(boolean bool){
        addFilter.setVisible(bool);
        clearFilter.setVisible(!bool);
    }

    public void onClearFilterClick(ActionEvent event){
        MainMenuController mainMenuController = new MainMenuController();
        mainMenuController.openNewScene(event, "showListCars.fxml");
    }

    public void onCallMenuClick(ActionEvent event){
        LoginController loginController = new LoginController();
        loginController.openMenu(event);
    }

    public void onAddFilterClick(ActionEvent event){

        String[] limit = null;

        if (!isInterval){
            limit = listLimit.getText().split(" , ");
        }
        doBySelectedCheckBox(limit);

        transformCheckBox();
        swapMenuToEnter(false);
        clearEnter();
    }

    private void clearEnter(){
        listLimit.clear();
        endLimit.clear();
        startLimit.clear();
    }

    private void transformCheckBox(){

        for (CheckBox checkBox : checkBoxes){
            if (checkBox.isSelected()){
                checkBox.setDisable(true);
                checkBox.setSelected(false);
            }
        }

    }

    private void doBySelectedCheckBox(String[] limit){

        FilteringByObject filteringByObject = new FilteringByObject();

        if (idCheckBox.isSelected()) {

            filteringByObject.filterByID(startLimit.getText(), endLimit.getText(), table.getItems());

        } else if (yearManufactureCheckBox.isSelected()) {

            filteringByObject.filterByYearManufacture(startLimit.getText(), endLimit.getText(), table.getItems());

        } else if (costCheckBox.isSelected()) {

            filteringByObject.filterByCost(startLimit.getText(), endLimit.getText(), table.getItems());

        } else if (maxSpeedCheckBox.isSelected()) {

            filteringByObject.filterByMaxSpeed(startLimit.getText(), endLimit.getText(), table.getItems());

        } else if (fuelConsumptionFor100kmCheckBox.isSelected()) {

            filteringByObject.filterByFuelConsumptionFor100km(startLimit.getText(), endLimit.getText(), table.getItems());

        } else if (vinCheckBox.isSelected()) {

            filteringByObject.filterByVIN(limit, table.getItems());

        } else if (markAndModelCheckBox.isSelected()) {

            filteringByObject.filterByMarkAndModel(limit, table.getItems());

        } else if (colorCheckBox.isSelected()) {

            filteringByObject.filterByColor(limit, table.getItems());

        } else if (fuelTypeCheckBox.isSelected()) {

            filteringByObject.filterByFuelType(limit, table.getItems());

        }
    }

    private void swapMenuToEnter(boolean bool){
        turnOnCheckMenu(!bool);
        selectedCheck.setVisible(bool);

        if (isInterval){
            turnOnLimitStartAndEnd(bool);

        } else {
            turnOnLimitList(bool);
        }
        swapVisibleButtons(bool);
    }

}
