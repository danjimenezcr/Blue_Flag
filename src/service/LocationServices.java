package service;

import dao.*;
import model.City;
import model.Country;
import model.District;
import model.Province;

import java.util.List;

public class LocationServices {
    private final DistrictDAO  districtDAO = new DistrictDAO();
    private final CityDAO cityDAO = new CityDAO();
    private final ProvinceDAO provinceDAO = new ProvinceDAO();
    private final CountryDAO countryDAO = new CountryDAO();

    public List<District> getDistricts() {
        return districtDAO.getDistricts(null, null, null);
    }
    public District getDistricts(int districtId) {
        return districtDAO.getDistricts(districtId, null, null).get(0);
    }

    public List<City> getCities() {
        return cityDAO.getCities(null, null, null);
    }
    public City getCities(int cityId) {
        return cityDAO.getCities(cityId, null, null).get(0);
    }

    public List<Province> getProvinces() {
        return provinceDAO.getProvinces(null, null, null);
    }
    public Province getProvinces(int provinceId) {
        return provinceDAO.getProvinces(provinceId, null, null).get(0);
    }

    public List<Country> getCountries() {
        return countryDAO.getCountries(null, null);
    }
    public Country getCountries(int countryId) {
        return countryDAO.getCountries(countryId, null).get(0);
    }

}
