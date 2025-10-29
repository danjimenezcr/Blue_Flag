package dao;

import dao.CollectionCenterDAO;
import dao.PointsConvertionDAO;
import dao.UsersDAO;
import model.*;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;


public class UserXCollectionCenterDAO {

    public int addUserXCollectionCenter(UserXCollectionCenter userXCollectionCenter) {
        String sql = "{ call adminUserXCollectionCenter.insertUserXCollectionCenter(?,?,?,?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, userXCollectionCenter.getUserId().getId());
            cs.setInt(2, userXCollectionCenter.getCollectionCenter().getAutorizedEntityId());
            cs.setInt(3, userXCollectionCenter.getPointsConvertion().getId());
            cs.setInt(4, userXCollectionCenter.getKilograms());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
        return 1;
    }

    public List<UserXCollectionCenter> getTMXCenter(Integer id, Integer Userid, Integer CollectionCenter, Integer PointsConvertionKey) {
        String sql = "{ ? = call adminUserXCollectionCenter.getUserXCollectionCenter(?, ?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Out Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(id != null)  cs.setInt(2, id);
            else cs.setNull(2, OracleTypes.INTEGER);

            if (Userid != null) cs.setInt(3, Userid);
            else cs.setNull(3, OracleTypes.INTEGER);

            if (CollectionCenter != null) cs.setInt(4, CollectionCenter);
            else cs.setNull(4, OracleTypes.INTEGER);

            if (PointsConvertionKey != null) cs.setInt(5, PointsConvertionKey);
            else cs.setNull(5, OracleTypes.INTEGER);

            //Parameters Input
            System.out.println(cs);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<UserXCollectionCenter> list = new ArrayList<>();
                while (rs.next()) {
                    User user = new UsersDAO().getUsers(rs.getInt("userId"), null, null, null,null, null, null).get(0);
                    CollectionCenter collectionCenter = new CollectionCenterDAO().getCollectionCenter(rs.getInt("collectionCenterId"), null, null, null).get(0);
                    PointsConvertion pointsConvertion = new PointsConvertionDAO().getPointsConvertion(rs.getInt("pointsConvertionId"), null, null, null).get(0);

                    list.add( new UserXCollectionCenter(rs.getInt("id"),
                                    user,
                                    rs.getString("CREATEDBY"),
                                    rs.getDate("CREATEDDATETIME"),
                                    rs.getString("UPDATEDBY"),
                                    rs.getDate("UPDATEDDATETIME"),
                                    collectionCenter,
                                    pointsConvertion,
                                    rs.getInt("kilograms")
                            ));

                }
                return list;

            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e){
            System.out.println("Failed to connect to database! " + e.getMessage());
        }
        return null;
    }

    public void deleteUserXCollectionCenter(UserXCollectionCenter userXCollectionCenter){
        String sql = "{call adminUserXCollectionCenter.removeUserXCollectionCenter(?)}}";
        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, userXCollectionCenter.getId());

            cs.execute();

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public void updateuserXCollectionCenter(UserXCollectionCenter userXCollectionCenter){
        String sql = "{call adminUserXCollectionCenter.UpdateUserXCollectionCenter(?, ?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            //Input parameters
            cs.setInt(1, userXCollectionCenter.getId());
            cs.setInt(2, userXCollectionCenter.getUserId().getId());
            cs.setInt(3, userXCollectionCenter.getCollectionCenter().getAutorizedEntityId());
            cs.setInt(4, userXCollectionCenter.getPointsConvertion().getId());
            cs.setInt(5, userXCollectionCenter.getKilograms());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

}