package org.example.baitap.dao.category;

import org.example.baitap.models.Category;
import org.example.baitap.utils.DBConnection;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class CategoryDaoImpl implements ICategory{
    @Override
    public List<Category> getAll() {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        ResultSet rs = null;
        List<Category> categories = new ArrayList<>();
        try{
            sta = connection.prepareCall("SELECT * FROM category");
            rs = sta.executeQuery();
            while (rs.next()){
                Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getBoolean("status")
                );
                categories.add(category);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
        return categories;
    }

    @Override
    public Category findById(Integer id) {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        ResultSet rs = null;
        try{
            sta = connection.prepareCall("SELECT * FROM category WHERE id =?");
            sta.setInt(1, id);
            rs = sta.executeQuery();
            if (rs.next()){
                return new Category(
                        rs.getInt("id"),
                        rs.getString("name"),
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
    public void update(Category category) {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        try{
            sta = connection.prepareCall("UPDATE category SET name =?, status =? WHERE id =?");
            sta.setString(1, category.getName());
            sta.setBoolean(2, category.isStatus());
            sta.setInt(3, category.getId());
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
            sta = connection.prepareCall("DELETE FROM category WHERE id =?");
            sta.setInt(1, id);
            sta.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
    }

    @Override
    public void save(Category category) {
        Connection connection = DBConnection.getConnection();
        CallableStatement sta = null;
        try{
            sta = connection.prepareCall("INSERT INTO category (name, status) VALUES (?,?)");
            sta.setString(1, category.getName());
            sta.setBoolean(2, category.isStatus());
            sta.executeUpdate();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            DBConnection.closeConnection(connection);
        }
    }
}
