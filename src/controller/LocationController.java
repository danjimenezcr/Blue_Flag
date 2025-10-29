package controller;

import model.City;
import model.Country;
import model.District;
import model.Province;
import service.LocationServices;

import java.util.List;

public class LocationController {
    private final LocationServices locationServices = new LocationServices();

    public List<District> getDistricts() {
        try  {
            return locationServices.getDistricts();
        } catch (Exception e) {
            System.out.println("Error getting districts: " + e.getMessage());
        }
        return null;
    }

    public List<City> getCities() {
        try  {
            return locationServices.getCities();
        } catch (Exception e) {
            System.out.println("Error getting districts: " + e.getMessage());
        }
        return null;
    }
    public List<Country> getCountries() {
        try  {
            return locationServices.getCountries();
        } catch (Exception e) {
            System.out.println("Error getting districts: " + e.getMessage());
        }
        return null;
    }
    public List<Province> getProvinces() {
        try  {
            return locationServices.getProvinces();
        } catch (Exception e) {
            System.out.println("Error getting districts: " + e.getMessage());
        }
        return null;
    }
}
