package dao;

import model.Country;
import model.District;
import model.Province;
import oracle.jdbc.OracleTypes;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProvinceDAO {

    public List<Province> getProvinces(Integer provinceId, String name, Integer countryId) {
        String sql = "{  ? = call PROVINCEMANAGER.GETPROVINCES(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // IN parameters
            if (provinceId != null) cs.setInt(2, provinceId);
            else cs.setNull(2, OracleTypes.INTEGER);

            if(name != null) cs.setString(3, name);
            else cs.setNull(3, OracleTypes.VARCHAR);

            if(countryId != null) cs.setInt(4, countryId);
            else cs.setNull(4, OracleTypes.INTEGER);

            cs.execute();

            try (ResultSet rs =  (ResultSet) cs.getObject(1)) {

                List<Province> list = new ArrayList<>();
                while (rs.next()) {
                    Country country = new CountryDAO().getCountries(rs.getInt("COUNTRYID"), null).get(0);
                    list.add(new Province(
                            rs.getInt("id"),
                            rs.getString("provinceName"),
                            rs.getDate("createdDateTime"),
                            rs.getString("createdBy"),
                            rs.getDate("updatedDateTime"),
                            rs.getString("updatedBy"),
                            country
                    ));


                }
                return list;
            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException e){
            System.out.println("Failed to connect to database! " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public void addProvince(Province province) {
        String sql = "{call PROVINCEMANAGER.INSERTPROVINCE(?, ?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setString(1, province.getName());
            cs.setInt(2, province.getCountry().getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteProvince(Province province) {
        String sql = "{call DISTRICTMANAGER.DELETEPROVINCE(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, province.getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateProvince(Province province) {
        String sql = "{call CITYMANAGER.UPDATEPROVINCE(?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, province.getId());
            cs.setString(2, province.getName());
            cs.setInt(3, province.getCountry().getId());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

        System.out.println(new ProvinceDAO().getProvinces(0, null, null));
    }
}
