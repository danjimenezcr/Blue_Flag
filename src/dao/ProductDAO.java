package dao;

import model.*;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OracleTypes;

public class ProductDAO {

    public List<Product> getProducts(Integer productId, String description, Integer authorizedEntityId) {
        String sql = "{ ? = call PRODUCTMANAGER.GETPRODUCTS(?, ?, ?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.registerOutParameter(1, OracleTypes.CURSOR);

            if(productId != null)  cs.setInt(2, productId);
            else cs.setNull(2, OracleTypes.INTEGER);
            if  (description != null) cs.setString(3, description);
            else cs.setNull(3, OracleTypes.VARCHAR);
            if (authorizedEntityId != null) cs.setInt(4, authorizedEntityId);
            else cs.setNull(4, OracleTypes.INTEGER);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<Product> list = new ArrayList<>();
                while (rs.next()) {
                    AutorizedEntity autorizedEntity = new AutorizedEntityDAO().getAutorizedEntity(rs.getInt("authorizedEntityId"), null, null, null).get(0);

                    list.add(new Product(
                            rs.getInt("id"),
                            rs.getString("description"),
                            rs.getString("photoUrl"),
                            rs.getDouble("cost"),
                            rs.getDate("createdDateTime"),
                            rs.getString("createdBy"),
                            rs.getDate("updatedDateTime"),
                            rs.getString("updatedBy"),
                            autorizedEntity
                    ));
                }
                return list;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to database! " + e.getMessage());
        }

        return null;
    }
    public static void main(String[] args) {

        System.out.println(new ProductDAO().getProducts(0, null, null));
    }

    public void addProduct(Product product) {
        String sql = "{call PRODUCTMANAGER.INSERTPRODUCT(?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setString(1, product.getDescription());
            cs.setString(2, product.getPhotoUrl());
            cs.setFloat(3, (float) product.getCost());
            cs.setInt(4, product.getAutorizedEntity().getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteProduct(Product product) {
        String sql = "{call PRODUCTMANAGER.DELETEPRODUCT(?)}";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.setInt(1, product.getId());

            cs.execute();

        } catch (Exception e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        String sql = "{call PRODUCTMANAGER.UPDATEEMAIL(?, ?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection()){
            CallableStatement cs = conn.prepareCall(sql);

            // Input parameters
            cs.setInt(1, product.getId());
            cs.setString(2, product.getDescription());
            cs.setString(3, product.getPhotoUrl());
            cs.setFloat(4, (float) product.getCost());
            cs.setInt(5, product.getAutorizedEntity().getId());

        } catch (Exception e){
            System.out.println("Failed to connect to database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<Product> getProductsRedeemRanking() {
        String sql = "{ ? = call PRODUCTMANAGER.GETPRODUCTSREDEEMRANKING }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);


            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {

                List<Product> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("ID"),
                            rs.getString("PRODUCT_DESCRIPTION"),
                            rs.getString("PHOTOURL"),
                            rs.getDouble("TOTAL_CANJE"),
                            null, // createdDateTime
                            null, // createdBy
                            null, // updatedDateTime
                            null, // updatedBy
                            new AutorizedEntity(rs.getInt("AUTHORIZEDENTITYID"), null, null, null, null, null, null)
                    ));
                }
                return list;

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Failed to connect to database! " + e.getMessage());
        }

        return null;
    }

    public List<Product> getProductsByCommerce(int commerceId) {
        String sql = "{ ? = call PRODUCTMANAGER.GETPRODUCTSBYCOMMERCE(?) }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            // Input Parameter
            cs.setInt(2, commerceId);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                List<Product> list = new ArrayList<>();

                while (rs.next()) {
                    AutorizedEntity ae = new AutorizedEntity(
                            commerceId,
                            null, null, null, null, null, null);

                    list.add(new Product(
                            rs.getInt("ID"),
                            rs.getString("PRODUCT_DESCRIPTION"),
                            rs.getString("PHOTO"),
                            rs.getDouble("COST"),
                            rs.getDate("CREATEDDATETIME"),
                            rs.getString("CREATEDBY"),
                            rs.getDate("UPDATEDDATETIME"),
                            rs.getString("UPDATEDBY"),
                            ae
                    ));
                }
                return list;
            }

        } catch (Exception e) {
            System.out.println("Error getting products: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Product> getTop5Products() {
        String sql = "{ ? = call PRODUCTMANAGER.GETTOP5PRODUCTS }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            // OUT Parameter
            cs.registerOutParameter(1, OracleTypes.CURSOR);

            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                List<Product> list = new ArrayList<>();

                while (rs.next()) {
                    list.add(new Product(
                            rs.getInt("ID"),
                            rs.getString("PRODUCT_DESCRIPTION"),
                            null,
                            rs.getDouble("TOTAL_REDEMPTIONS"),
                            null, null, null, null,
                            null
                    ));
                }
                return list;
            }

        } catch (Exception e) {
            System.out.println("Error getting top 5 products: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getTotalProductsRedeemed() {
        String sql = "{ ? = call PRODUCTMANAGER.GETTOTALPRODUCTSREDEEMED }";

        try (Connection conn = DBConnection.getConnection()) {
            CallableStatement cs = conn.prepareCall(sql);

            cs.registerOutParameter(1, OracleTypes.CURSOR);
            cs.execute();

            try (ResultSet rs = (ResultSet) cs.getObject(1)) {
                List<String> list = new ArrayList<>();

                while (rs.next()) {
                    String result =
                            "Year: " + rs.getInt("YEAR") +
                                    " | Month: " + rs.getInt("MONTH") +
                                    " | Total Redeemed: " + rs.getInt("TOTAL_REDEEMED");

                    list.add(result);
                }
                return list;
            }

        } catch (Exception e) {
            System.out.println("Error fetching total products redeemed: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }



}


