package com.taxipark.gui;

import com.taxipark.gui.component.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.taxipark.gui.MainMenuController.setAlert;

public class SearchCarByIDController implements Initializable {

    @FXML
    private TableView<StringTable> table;

    @FXML
    private TableColumn<StringTable, String> nameParameterColumn;

    @FXML
    private TableColumn<StringTable, String> characteristicColumn;

    @FXML
    private ChoiceBox<Integer> selectListCar;

    @FXML
    private ChoiceBox<String> selectListCharacteristic;

    @FXML
    private Button searchCarButton;

    @FXML
    private Button editCarDataButton;

    @FXML
    private Button deleteCarButton;

    @FXML
    private Button saveChangeButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Button goBackwitoutSavingButton;

    @FXML
    private Button resetEditThisCharacteristicButton;

    @FXML
    private Button chooseCharacteristicButton;

    @FXML
    private Label labelInfo;

    @FXML
    private Label messageLabelMain;

    @FXML
    private TextField enteringData;

    @FXML
    Spinner<Integer> enteringYearManufactureCar;

    @FXML
    Spinner<Double> enteringEngineCapacityCar;

    @FXML
    Spinner<Double> enteringFuelConsumptionFor100kmCar;

    @FXML
    ChoiceBox<String> enteringFuelTypeCar;

    @FXML
    Label messageLabel;

    private List<Car> cars;

    private List<String> listCharacteristic;

    private Car carSelectedByID;

    public class StringTable{
        String nameParameterColumn;
        String characteristicColumn;

        public StringTable(String nameParameterColumn, String characteristicColumn) {
            this.nameParameterColumn = nameParameterColumn;
            this.characteristicColumn = characteristicColumn;
        }

        public String getNameParameterColumn() {
            return nameParameterColumn;
        }

        public String getCharacteristicColumn() {
            return characteristicColumn;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            cars = CarInDataBase.getCarsFromDB();

            if (cars.size() == 0) {
                caseNoCar();
            } else {

                setListCharacteristic();

                for (Car car : cars) {
                    selectListCar.getItems().add(car.getCarID());
                }

                for (int i = 1; i < listCharacteristic.size(); i++) {
                    selectListCharacteristic.getItems().add(listCharacteristic.get(i));
                }

                enteringFuelTypeCar.getItems().addAll("А-100", "А-98", "А-95 Energy", "А-95", "А-92 Energy", "ДТ Energy", "ГАЗ");
                enteringYearManufactureCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 2022));
                enteringEngineCapacityCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 3, 1, 0.1));
                enteringFuelConsumptionFor100kmCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 20, 1, 0.1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void caseNoCar() {
        try {
            deleteCarButton.setDisable(true);
            editCarDataButton.setDisable(true);
            saveChangeButton.setDisable(true);
            selectListCar.setDisable(true);
            setMainMessage("Авто немає!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListCharacteristic() {
        try {
            listCharacteristic = new ArrayList<>();

            listCharacteristic.add("ID");
            listCharacteristic.add("VIN");
            listCharacteristic.add("Марка і модель");
            listCharacteristic.add("Рік збірки");
            listCharacteristic.add("Вартість");
            listCharacteristic.add("Колір");
            listCharacteristic.add("Максимальна швидкість");
            listCharacteristic.add("Коробка передач");
            listCharacteristic.add("Тип приводу");
            listCharacteristic.add("Тип пального");
            listCharacteristic.add("Тип двигуна");
            listCharacteristic.add("Об'єм двигуна");
            listCharacteristic.add("Витрати пального за 100 км");
            listCharacteristic.add("Стан");
            listCharacteristic.add("Типи безпеки");
            listCharacteristic.add("Типи комфорту");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private List<StringTable> createListToWrite(){

        List<StringTable> stringTableList = new ArrayList<>();

        try {

            stringTableList.add(new StringTable(listCharacteristic.get(0), String.valueOf(carSelectedByID.getCarID())));
            stringTableList.add(new StringTable(listCharacteristic.get(1), carSelectedByID.getCarVIN()));
            stringTableList.add(new StringTable(listCharacteristic.get(2), carSelectedByID.getMarkAndModel()));
            stringTableList.add(new StringTable(listCharacteristic.get(3), String.valueOf(carSelectedByID.getYearManufacture())));
            stringTableList.add(new StringTable(listCharacteristic.get(4), String.valueOf(carSelectedByID.getCost())));
            stringTableList.add(new StringTable(listCharacteristic.get(5), carSelectedByID.getColor()));
            stringTableList.add(new StringTable(listCharacteristic.get(6), String.valueOf(carSelectedByID.getMaxSpeed())));
            stringTableList.add(new StringTable(listCharacteristic.get(7), carSelectedByID.getTransmission()));
            stringTableList.add(new StringTable(listCharacteristic.get(8), carSelectedByID.getDriveType()));
            stringTableList.add(new StringTable(listCharacteristic.get(9), carSelectedByID.getFuelType()));
            stringTableList.add(new StringTable(listCharacteristic.get(10), carSelectedByID.getEngineType()));
            stringTableList.add(new StringTable(listCharacteristic.get(11), String.valueOf(carSelectedByID.getEngineCapacity())));
            stringTableList.add(new StringTable(listCharacteristic.get(12), String.valueOf(carSelectedByID.getFuelConsumptionFor100km())));
            stringTableList.add(new StringTable(listCharacteristic.get(13), carSelectedByID.getState()));
            stringTableList.add(new StringTable(listCharacteristic.get(14), carSelectedByID.getSecurityTypes()));
            stringTableList.add(new StringTable(listCharacteristic.get(15), carSelectedByID.getComfortTypes()));

        }catch (Exception e){
            e.printStackTrace();
        }
        return stringTableList;
    }

    public void onSearchCarButtonClick(ActionEvent event){

        try {
            if (selectListCar.getValue() != null){
                int id = selectListCar.getValue();
                carSelectedByID = Car.searchCarByID(id, cars);
                if (carSelectedByID != null){
                    setDataTable();
                    setMainMessage("Дані про вибране авто виведено)");
                }else {
                    setMainMessage("Авто не знайдено!");
                }
            }else {
                setMainMessage("Виберіть авто!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setDataTable(){
        try {
            nameParameterColumn.setCellValueFactory(new PropertyValueFactory<>("nameParameterColumn"));
            characteristicColumn.setCellValueFactory(new PropertyValueFactory<>("characteristicColumn"));

            List<StringTable> stringTableList = createListToWrite();
            table.setItems(FXCollections.observableArrayList(stringTableList));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onEditCarDataButtonClick(ActionEvent event){

        try {
            if (selectListCar.getValue() != null) {
                int id = selectListCar.getValue();
                carSelectedByID = Car.searchCarByID(id, cars);

                if (carSelectedByID != null) {
                    labelInfo.setText("Виберіть параметр для оновлення інформації :");

                    turnOnVisibleButtonAndChoiceListOnMainScene(false);
                    onResetEditThisCharacteristicButtonClick(event);

                }else {
                    setMainMessage("Авто не знайдено!");
                }
            }else {
                setMainMessage("Виберіть авто!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onChooseCharacteristicButtonClick(ActionEvent event) {
        try {
            boolean isSelected = true;

            if (selectListCharacteristic.getValue() == null) {
                messageLabel.setText("Виберіть характеристику для редагування!!");
                messageLabel.setVisible(true);
                isSelected = false;
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(3))) {
                enteringYearManufactureCar.setVisible(true);
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(9))) {
                enteringFuelTypeCar.setVisible(true);
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(11))) {
                enteringEngineCapacityCar.setVisible(true);
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(12))) {
                enteringFuelConsumptionFor100kmCar.setVisible(true);
            } else {
                enteringData.setVisible(true);
            }

            if (isSelected) {
                messageLabel.setText("Введіть нові дані:");
                chooseCharacteristicButton.setDisable(true);
                turnOnVisibleButtonAndChoiceListOnChangingDataScene(true);
                selectListCharacteristic.setDisable(true);
                saveChangeButton.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResetEditThisCharacteristicButtonClick(ActionEvent event){
        try {
            turnOnVisibleButtonAndChoiceListOnChangingDataScene(false);
            selectListCharacteristic.setVisible(true);
            selectListCharacteristic.setDisable(false);
            chooseCharacteristicButton.setDisable(false);
            chooseCharacteristicButton.setVisible(true);
            resetEditThisCharacteristicButton.setVisible(true);
            goBackwitoutSavingButton.setDisable(false);
            turnOffAndClearAllEnteringField();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onDeleteCarButtonClick(ActionEvent event){
        try {
            if (selectListCar.getValue() != null) {
                int id = selectListCar.getValue();
                carSelectedByID = Car.searchCarByID(id, cars);

                if (carSelectedByID != null) {
                    DeleteCar deleteCar = new DeleteCar();
                    deleteCar.execute(carSelectedByID);

                    MainMenuController mainMenuController = new MainMenuController();
                    mainMenuController.openNewScene(event, "searchCarByID.fxml");

                    setMainMessage("Авто успішно видалено)");
                }else {
                    setMainMessage("Виникла помилка із видаленням!");
                }
            }else {
                setMainMessage("Виберіть авто!");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMainMessage(String message){
        try {
            messageLabelMain.setText(message);
            messageLabelMain.setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onSaveChangeButtonClick(ActionEvent event){
        try {
            int id = carSelectedByID.getCarID();
            if (doActionWithSelectedCharacteristic()) {
                goBackButton.setDisable(false);
                cars = CarInDataBase.getCarsFromDB();
                carSelectedByID = Car.searchCarByID(id, cars);
                if (carSelectedByID != null){
                    setDataTable();
                    saveChangeButton.setDisable(true);
                    goBackButton.setDisable(false);
                    goBackwitoutSavingButton.setDisable(true);
                    turnOffAndClearAllEnteringField();

                    setAlert(Alert.AlertType.INFORMATION, "Зміни успішно збережено!", "");
                }
            }else {
                setAlert(Alert.AlertType.ERROR, "Виникла помилка при записі!", "Спробуйте ще раз, перевіривши коректність запитів.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean doActionWithSelectedCharacteristic() {
        try {

            CheckingCar checkingCar = new CheckingCar();
            EditCarData editCarData = new EditCarData();
            boolean isSaved = false;

            if (selectListCharacteristic.getValue() == null) {
                messageLabel.setText("Введіть дані для збереження!!");
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(6)) || selectListCharacteristic.getValue().equals(listCharacteristic.get(4))) {
                if (checkingCar.stringIsDouble(enteringData.getText())) {
                    isSaved = editCarData.updateData(carSelectedByID, selectListCharacteristic.getValue(), Double.parseDouble(enteringData.getText()));
                }else {
                    setAlert(Alert.AlertType.ERROR, "Некоректний тип даних!", "Введено некоректний тип даних, спробуйте ще раз");
                }
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(3))) {
                isSaved = editCarData.updateData(carSelectedByID, selectListCharacteristic.getValue(), enteringYearManufactureCar.getValue());
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(9))) {
                if (enteringFuelTypeCar.getValue() != null) {
                    isSaved = editCarData.updateData(carSelectedByID, selectListCharacteristic.getValue(), enteringFuelTypeCar.getValue());
                }else{
                    setAlert(Alert.AlertType.ERROR, "Пусте поле вводу!", "Виберіть тип пального, спробуйте ще раз");
                }
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(11))) {
                isSaved = editCarData.updateData(carSelectedByID, selectListCharacteristic.getValue(), enteringEngineCapacityCar.getValue());
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(12))) {
                isSaved = editCarData.updateData(carSelectedByID, selectListCharacteristic.getValue(), enteringFuelConsumptionFor100kmCar.getValue());
            } else {
                isSaved = editCarData.updateData(carSelectedByID, selectListCharacteristic.getValue(), enteringData.getText());
            }

            return isSaved;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void onGoBackButtonClick(ActionEvent event){
        try {
            turnOffAndClearAllEnteringField();
            turnOnVisibleButtonAndChoiceListOnChangingDataScene(false);
            turnOnVisibleButtonAndChoiceListOnMainScene(true);
            selectListCharacteristic.setVisible(false);
            chooseCharacteristicButton.setVisible(false);
            resetEditThisCharacteristicButton.setVisible(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onCallMenuButtonClick(ActionEvent event){

        try {
            LoginController loginController = new LoginController();
            loginController.openMenu(event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void turnOnVisibleButtonAndChoiceListOnMainScene(boolean bool){
        try {
            selectListCar.setVisible(bool);
            searchCarButton.setVisible(bool);
            editCarDataButton.setVisible(bool);
            deleteCarButton.setVisible(bool);
            goBackwitoutSavingButton.setVisible(!bool);
            messageLabelMain.setVisible(bool);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void turnOnVisibleButtonAndChoiceListOnChangingDataScene(boolean bool) {
        try {
            goBackButton.setVisible(bool);
            saveChangeButton.setVisible(bool);
            messageLabel.setVisible(bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void turnOffAndClearAllEnteringField() {
        try {
            enteringData.setVisible(false);
            enteringData.clear();
            enteringYearManufactureCar.setVisible(false);
            enteringEngineCapacityCar.setVisible(false);
            enteringFuelTypeCar.setVisible(false);
            enteringFuelConsumptionFor100kmCar.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
