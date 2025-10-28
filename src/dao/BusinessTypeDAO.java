import model.BusinessType;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class BusinessTypeDAO {

    public List<BusinessType> getBusinessType(String id, String description) {
        String sql = "{ ? = call adminBusinessType.getBusinessType(?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            //Parameters Input
            cs.setString(2, id);
            cs.setString(3, description);
            System.out.println(cs.toString());

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<BusinessType> list = new ArrayList<>();
                while (rs.next()) {
                    list.add( new BusinessType(rs.getInt("id"),
                                    rs.getString(" CREATEDBY"),
                                    rs.getDate("CREATEDDATE"),
                                    rs.getString("UPDATEDBY"),
                                    rs.getDate("UPDATEDDATE"),
                                    rs.getString("description")
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