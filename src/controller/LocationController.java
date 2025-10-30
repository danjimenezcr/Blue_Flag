package controller;

import dao.CityDAO;
import dao.CountryDAO;
import dao.DistrictDAO;
import dao.ProvinceDAO;
import model.*;
import view.MainWindow;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class LocationController {
    private final DistrictDAO districtDAO = new DistrictDAO();
    private final CityDAO cityDAO = new CityDAO();
    private final ProvinceDAO provinceDAO = new ProvinceDAO();
    private final CountryDAO countryDAO = new CountryDAO();

    public List<District> getDistricts() {
        try {
            return districtDAO.getDistricts(null, null, null);
        }
        catch (Exception e) {
            System.out.println("Error getting districts: " + e.getMessage());
        }
        return null;
    }
    public District getDistrict(int districtId) {
        try {
            return districtDAO.getDistricts(districtId, null, null).get(0);
        }
        catch (Exception e) {
            System.out.println("Error getting districts: " + e.getMessage());
        }
        return null;

    }

    public List<City> getCities() {
        try {
            return cityDAO.getCities(null, null, null);
        }
        catch (Exception e) {
            System.out.println("Error getting districts: " + e.getMessage());
        }
        return null;
    }

    public City getCities(int cityId) {
        try {
            return cityDAO.getCities(cityId, null, null).get(0);
        }
        catch (Exception e) {
            System.out.println("Error getting districts: " + e.getMessage());
        }
        return null;

    }
    public List<Province> getProvinces() {
        try {
            return provinceDAO.getProvinces(null, null, null);
        }
        catch (Exception e) {
            System.out.println("Error getting districts: " + e.getMessage());
        }
        return null;
    }
    public Province getProvinces(int provinceId) {
        try {
            return provinceDAO.getProvinces(provinceId, null, null).get(0);
        }
        catch (Exception e) {
            System.out.println("Error getting provinces: " + e.getMessage());
        }
        return null;

    }
    public List<Country> getCountries() {
        try {
            return countryDAO.getCountries(null, null);
        } catch (Exception e) {
            System.out.println("Error getting countries: " + e.getMessage());
        }
        return null;
    }
    public Country getCountries(int countryId) {
        try {
            return countryDAO.getCountries(countryId, null).get(0);
        }
        catch (Exception e) {
            System.out.println("Error getting countries: " + e.getMessage());
        }
        return null;

    }

    public DefaultTableModel getDistrictModel(MainWindow mainWindow) {
        DefaultTableModel tableModel = new DefaultTableModel();
        try {
            List<District> districts = districtDAO.getDistricts(null, null, null);
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Created By");
            tableModel.addColumn("Created At");
            for (District district : districts) {
                tableModel.addRow(new Object[]{district.getId(), district.getName(), district.getCreatedBy(), district.getCreatedDateTime()});
            }
            return tableModel;
        } catch (Exception e) {
            mainWindow.showError("Error loading districts: " + e.getMessage());
            return null;
        }
    }

    public DefaultTableModel getCitiesModel(MainWindow mainWindow) {
        DefaultTableModel tableModel = new DefaultTableModel();
        try {
            List<City> cities = cityDAO.getCities(null, null, null);
            tableModel.addColumn("ID");
            tableModel.addColumn("Name");
            tableModel.addColumn("Created By");
            tableModel.addColumn("Created At");
            for (City city : cities) {
                tableModel.addRow(new Object[]{city.getId(), city.getName(), city.getCreatedBy(), city.getCreatedDateTime()});
            }
            return tableModel;
        } catch (Exception e) {
            mainWindow.showError("Error loading districts: " + e.getMessage());
            return null;
        }
    }


}
