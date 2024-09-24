package org.example.baitap.dao.product1;
import org.example.baitap.models.Product1;
import org.example.baitap.utils.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductDaoImpl1 implements IProductDao1 {
    @Override
    public List<Product1> getAll() {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        ResultSet rs = null;
        List<Product1> products = new ArrayList<>();
        try{
            sta = connection.prepareCall("SELECT * FROM product1");
            rs = sta.executeQuery();
            while (rs.next()){
                Product1 product = new Product1(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("categoryId"),
                        rs.getBoolean("status")
                );
                products.add(product);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
        return products;
    }

    @Override
    public Product1 findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        ResultSet rs = null;
        try{
            sta = connection.prepareCall("SELECT * FROM product1 WHERE id =?");
            sta.setInt(1, id);
            rs = sta.executeQuery();
            if (rs.next()){
                return new Product1(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getInt("categoryId"),
                        rs.getBoolean("status")
                );
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
        return null;
    }

    @Override
    public void update(Product1 product) {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        try{
            sta = connection.prepareCall("UPDATE product1 SET name =?, price =?, stock =?, categoryId = ?, status =? WHERE id =?");
            sta.setString(1, product.getName());
            sta.setDouble(2, product.getPrice());
            sta.setInt(3, product.getStock());
            sta.setInt(4, product.getCategoryId());
            sta.setBoolean(5, product.isStatus());
            sta.setInt(6, product.getId());
            sta.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        try{
            sta = connection.prepareCall("DELETE FROM product1 WHERE id =?");
            sta.setInt(1, id);
            sta.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
    }

    @Override
    public void save(Product1 product) {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        try{
            sta = connection.prepareCall("INSERT INTO product1 (name, price, stock, categoryId, status) VALUES (?,?,?,?,?)");
            sta.setString(1, product.getName());
            sta.setDouble(2, product.getPrice());
            sta.setInt(3, product.getStock());
            sta.setInt(4, product.getCategoryId());
            sta.setBoolean(5, product.isStatus());
            sta.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
    }
}
