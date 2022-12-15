package com.taxipark.gui.menu;

import com.taxipark.component.car.Car;
import com.taxipark.component.car.CarInDataBase;
import com.taxipark.component.action.FilteringByObject;
import com.taxipark.gui.LoginController;
import com.taxipark.gui.MainMenuController;
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
import java.util.List;
import java.util.ResourceBundle;

public class ShowListCarsController implements Initializable {

    @FXML
    private Button addFilterButton;

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

    private List<CheckBox> checkBoxes;

    private boolean isSearching = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            initCheckBoxesArray();

            List<Car> cars = CarInDataBase.getCarsFromDB();

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initCheckBoxesArray() {
        try {
            checkBoxes = List.of(new CheckBox[]{idCheckBox, vinCheckBox, markAndModelCheckBox,
                    yearManufactureCheckBox, costCheckBox, colorCheckBox, maxSpeedCheckBox,
                    fuelTypeCheckBox, fuelConsumptionFor100kmCheckBox});

            turnOnDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onWorkWithSearchingClick() {
        try {
            isSearching = true;

            turnOnDisable(true);
            keyWordField.setDisable(false);

            FilteredList<Car> filteredData = new FilteredList<>(table.getItems(), b -> true);

            keyWordField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(car -> {

                if (newValue.isEmpty() || newValue.isBlank() || newValue.equals(" ")) {
                    return true;
                }

                String searchKeyWord = newValue.toLowerCase();

                if (car.getMarkAndModel().toLowerCase().contains(searchKeyWord)) {
                    return true;
                } else if (car.getCarVIN().toLowerCase().contains(searchKeyWord)) {
                    return true;
                } else if (car.getColor().toLowerCase().contains(searchKeyWord)) {
                    return true;
                } else if (car.getFuelType().toLowerCase().contains(searchKeyWord)) {
                    return true;
                } else if (Integer.toString(car.getCarID()).contains(searchKeyWord)) {
                    return true;
                } else return Integer.toString(car.getYearManufacture()).toLowerCase().contains(searchKeyWord);
            }));

            SortedList<Car> sortedList = new SortedList<>(filteredData);

            sortedList.comparatorProperty().bind(table.comparatorProperty());
            table.setItems(sortedList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStartFilterClick() {
        try {
            if (!isSearching) {
                turnOnDisable(false);
            } else {
                infoToStop.setVisible(true);
                arrowToStop.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStopSearchButtonClick(ActionEvent event) {
        try {
            isSearching = false;
            onClearFilterButtonClick(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOnDisable(boolean bool) {
        try {
            for (CheckBox checkBox : checkBoxes) {
                checkBox.setDisable(bool);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCheckBox(ActionEvent event) {
        try {
            isInterval = checkElementsToFilter();
            swapMenuToEnter(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkElementsToFilter() {

        try {
            if (idCheckBox.isSelected()) {

                selectedCheck.setText("Обраний фільтр: ID");
                return true;

            } else if (yearManufactureCheckBox.isSelected()) {

                selectedCheck.setText("Обраний фільтр: Рік збірки");
                return true;

            } else if (costCheckBox.isSelected()) {

                selectedCheck.setText("Обраний фільтр: Вартість");
                return true;

            } else if (maxSpeedCheckBox.isSelected()) {

                selectedCheck.setText("Обраний фільтр: Максимальна швидкість");
                return true;

            } else if (fuelConsumptionFor100kmCheckBox.isSelected()) {

                selectedCheck.setText("Обраний фільтр: Витрата пального за 100 км");
                return true;

            } else if (vinCheckBox.isSelected()) {

                listLimit.setPromptText("напр: ВВ 4177 СН , AH 4035 HX , AA 6126 ME");
                selectedCheck.setText("Обраний фільтр: VIN");
                return false;

            } else if (markAndModelCheckBox.isSelected()) {

                listLimit.setPromptText("напр: Audi , Audi A7 , BMW");
                selectedCheck.setText("Обраний фільтр: Марка і модель");
                return false;

            } else if (colorCheckBox.isSelected()) {

                listLimit.setPromptText("напр: чорний , білий , синій");
                selectedCheck.setText("Обраний фільтр: Колір");
                return false;

            } else if (fuelTypeCheckBox.isSelected()) {

                listLimit.setPromptText("напр: А-95 , дизель , А-100");
                selectedCheck.setText("Обраний фільтр: Тип пального");
                return false;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    private void turnOnLimitStartAndEnd(boolean bool) {
        try {
            labelStartLimit.setVisible(bool);
            labelEndLimit.setVisible(bool);
            startLimit.setVisible(bool);
            endLimit.setVisible(bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void turnOnLimitList(boolean bool) {
        try {
            labelListLimit.setVisible(bool);
            listLimit.setVisible(bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void turnOnCheckMenu(boolean bool) {
        try {
            for (CheckBox checkBox : checkBoxes) {
                checkBox.setVisible( bool);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onClearFilterButtonClick(ActionEvent event) {
        try {
            MainMenuController mainMenuController = new MainMenuController();
            mainMenuController.openNewScene(event, "showListCars.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCallMenuButtonClick(ActionEvent event) {
        try {
            LoginController loginController = new LoginController();
            loginController.openMenu(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAddFilterButtonClick(ActionEvent event) {

        try {
            String[] limit = null;

            if (!isInterval) {
                limit = listLimit.getText().split(" , ");
            }
            doBySelectedCheckBox(limit);

            transformCheckBox();
            swapMenuToEnter(false);
            clearEnter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearEnter() {
        try {
            listLimit.clear();
            endLimit.clear();
            startLimit.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transformCheckBox() {

        try {
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    checkBox.setDisable(true);
                    checkBox.setSelected(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //todo other class
    private void doBySelectedCheckBox(String[] limit) {

        FilteringByObject filteringByObject = new FilteringByObject();

        try {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void swapMenuToEnter(boolean bool) {

        try {
            turnOnCheckMenu(!bool);
            selectedCheck.setVisible(bool);

            if (isInterval) {
                turnOnLimitStartAndEnd(bool);

            } else {
                turnOnLimitList(bool);
            }
            addFilterButton.setVisible(bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
