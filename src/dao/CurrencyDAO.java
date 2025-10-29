package dao;

import model.Currency;
import model.MaterialType;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class CurrencyDAO {

    public int addCurrency(Currency currency) {
        String sql = "{ call CurrencyManager.insertCurrency(?,?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setString(1, currency.getCode());
            cs.setString(2, currency.getSymbol());
            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
        return 1;
    }

    public List<Currency> getCurrencies(Integer id, String code, String Symbol) {
        String sql = "{ ? = call CurrencyManager.getCurrencies(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            if (code != null) cs.setString(3, code);
            else cs.setNull(3, OracleTypes.VARCHAR);

            if (Symbol != null) cs.setString(4, Symbol);
            else cs.setNull(4, OracleTypes.VARCHAR);

            System.out.println(cs);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<Currency> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new Currency(rs.getInt("id"),
                                    rs.getString("code"),
                                    rs.getString("createdBy"),
                                    rs.getDate("createdDateTime"),
                                    rs.getString("updatedBy"),
                                    rs.getDate("updatedDateTime"),
                                    rs.getString("symbol")
                            )
                    );
                    return list;
                }

            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e){
            System.out.println("Failed to connect to database! " + e.getMessage());
        }
        return null;
    }

    public void deleteCurrency(Currency currency){
        String sql = "{call CurrencyManager.deleteCurrency(?)}}";
        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, currency.getId());

            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateCurrency(Currency currency){
        String sql = "{call adminCenterType.updateCenterType(?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, currency.getId());
            cs.setString(2, currency.getCode());
            cs.setString(3, currency.getSymbol());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

}