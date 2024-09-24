package org.example.baitap.dao.orderdetails;
import org.example.baitap.dao.product1.IProductDao1;
import org.example.baitap.models.OrderDetails;
import org.example.baitap.models.Product1;
import org.example.baitap.utils.DBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDetailsDaoImpl implements OrderDetailsDao{

    @Autowired
    private IProductDao1 iProductDao1;

    @Override
    public List<OrderDetails> getAll() {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        ResultSet rs = null;
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        try {
            sta = connection.prepareCall("SELECT * FROM OrderDetails");
            rs = sta.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("productId");
                Product1 product = iProductDao1.findById(productId); // Lấy thông tin sản phẩm

                OrderDetails orderDetails = new OrderDetails(
                        rs.getInt("id"),
                        rs.getInt("orderId"),
                        productId,
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        product
                );
                orderDetailsList.add(orderDetails);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(connection);
        }
        return orderDetailsList;
    }


    @Override
    public OrderDetails findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        ResultSet rs = null;
        try {
            sta = connection.prepareCall("SELECT * FROM OrderDetails WHERE id = ?");
            sta.setInt(1, id);
            rs = sta.executeQuery();
            if (rs.next()) {
                int orderId = rs.getInt("orderId");
                int productId = rs.getInt("productId");
                int quantity = rs.getInt("quantity");
                double price = rs.getDouble("price");
                Product1 product = iProductDao1.findById(productId);
                return new OrderDetails(id, orderId, productId, quantity, price, product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DBConnection.closeConnection(connection);
        }
        return null;
    }


    @Override
    public void update(OrderDetails orderDetails) {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        try{
            sta = connection.prepareCall("update OrderDetails set quantity=?");
            sta.setInt(1, orderDetails.getQuantity());
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
            sta = connection.prepareCall("DELETE FROM OrderDetails WHERE id =?");
            sta.setInt(1, id);
            sta.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
    }

    @Override
    public void save(OrderDetails orderDetails) {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        try{
            sta = connection.prepareCall("INSERT INTO OrderDetails (orderId, productId, quantity, price) VALUES (?,?,?,?)");
            sta.setInt(1, orderDetails.getOrderId());
            sta.setInt(2, orderDetails.getProductId());
            sta.setInt(3, orderDetails.getQuantity());
            sta.setDouble(4, orderDetails.getPrice());
            sta.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
    }
}
