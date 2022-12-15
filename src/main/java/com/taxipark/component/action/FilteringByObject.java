package com.taxipark.component.action;

import com.taxipark.component.car.Car;

import java.util.Iterator;
import java.util.List;

public class FilteringByObject {

    private double start, end;

    public void filterByMarkAndModel(String[] objectsFilter, List<Car> cars){

        boolean ifMark;
        String objectCar;

        Iterator<Car> iter = cars.iterator();

        while (iter.hasNext()) {
            Car car = iter.next();
            ifMark = false;

            for (String objectFilter: objectsFilter){
                objectCar = car.getMarkAndModel().toLowerCase();
                if (objectCar.contains(objectFilter.toLowerCase())){
                    ifMark = true;
                }
            }
            if(!ifMark){
                iter.remove();
            }
        }
    }

    public void filterByColor(String[] objectsFilter, List<Car> cars){

        boolean ifMark;
        String objectCar;

        Iterator<Car> iter = cars.iterator();

        while (iter.hasNext()) {
            Car car = iter.next();
            ifMark = false;

            for (String objectFilter: objectsFilter){
                objectCar = car.getColor().toLowerCase();
                if (objectCar.contains(objectFilter.toLowerCase())){
                    ifMark = true;
                }
            }
            if(!ifMark){
                iter.remove();
            }
        }
    }

    public void filterByFuelType(String[] objectsFilter, List<Car> cars){

        boolean ifMark;
        String objectCar;

        Iterator<Car> iter = cars.iterator();

        while (iter.hasNext()) {
            Car car = iter.next();
            ifMark = false;

            for (String objectFilter: objectsFilter){
                objectCar = car.getFuelType().toLowerCase();
                if (objectCar.contains(objectFilter.toLowerCase())){
                    ifMark = true;
                }
            }
            if(!ifMark){
                iter.remove();
            }
        }
    }

    public void filterByVIN(String[] objectsFilter, List<Car> cars){

        boolean ifMark;
        String objectCar;

        Iterator<Car> iter = cars.iterator();

        while (iter.hasNext()) {
            Car car = iter.next();
            ifMark = false;

            for (String objectFilter: objectsFilter){
                objectCar = car.getCarVIN().toLowerCase();
                if (objectCar.contains(objectFilter.toLowerCase())){
                    ifMark = true;
                }
            }
            if(!ifMark){
                iter.remove();
            }
        }
    }

    public void filterByID(String startStr, String endStr, List<Car> cars){

        convert(startStr, endStr);

        cars.removeIf(car -> car.getCarID() > (int)end
                || car.getCarID() < (int)start);

    }

    public void filterByYearManufacture(String startStr, String endStr, List<Car> cars){

        convert(startStr, endStr);

        cars.removeIf(car -> car.getYearManufacture() > (int)end
                || car.getYearManufacture() < (int)start);

    }

    public void filterByCost(String startStr, String endStr, List<Car> cars){

        convert(startStr, endStr);

        cars.removeIf(car -> car.getCost() > end
                || car.getCost() < start);

    }

    public void filterByMaxSpeed(String startStr, String endStr, List<Car> cars){

        convert(startStr, endStr);

        cars.removeIf(car -> car.getMaxSpeed() > end
                || car.getMaxSpeed() < start);

    }

    public void filterByFuelConsumptionFor100km(String startStr, String endStr, List<Car> cars){

        convert(startStr, endStr);


        cars.removeIf(car -> car.getFuelConsumptionFor100km() > end
                || car.getFuelConsumptionFor100km() < start);

    }

    public void convert(String startStr, String endStr){
        start = Double.parseDouble(startStr);
        end = Double.parseDouble(endStr);
    }
}
