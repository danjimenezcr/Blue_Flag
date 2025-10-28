import model.AffiliatedBusiness;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class AffiliatedBusinessDAO {

    public List<AffiliatedBusiness> getAffiliatedBusiness(String id, String name, String BusinessType, String districtId) {
        String sql = "{ ? = call adminAffiliatedBusiness.getAffiliatedBusiness(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setString(2, id);
            cs.setString(3, name);
            cs.setString(4, BusinessType);
            cs.setString(5, districtId);
            System.out.println(cs.toString());

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<AffiliatedBusiness> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new AffiliatedBusiness(rs.getInt("id"),
                                    rs.getString("NAME"),
                                    rs.getString("OPENHOUR"),
                                    rs.getString("CLOSEHOUR"),
                                    rs.getString("MANAGER"),
                                    rs.getString("CONTACT"),
                                    rs.getString("districtName"),
                                    rs.getInt("BusinessTypeId")
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

}