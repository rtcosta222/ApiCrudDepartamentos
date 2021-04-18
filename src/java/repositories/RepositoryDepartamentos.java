/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Departamento;

/**
 *
 * @author lscar
 */
public class RepositoryDepartamentos {
    private Connection getConnection() throws SQLException{
        DriverManager.registerDriver(new SQLServerDriver());
        String cadena = "jdbc:sqlserver://sqlserverjavapgs.database.windows.net:1433;databaseName=SQLAZURE";
        Connection cn = DriverManager.getConnection(cadena, "adminsql", "Admin123");
        return cn;
    }
    
    public List<Departamento> getDepartamentos() throws SQLException{
        Connection cn = this.getConnection();
        String sql = "select * from dept";
        Statement st = cn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        ArrayList<Departamento> depts = new ArrayList<>();
        while(rs.next()){
            Departamento dept = new Departamento(rs.getInt("DEPT_NO"),
                                    rs.getString("DNOMBRE"),
                                    rs.getString("LOC"));
            depts.add(dept);
        }
        rs.close();
        cn.close();
        return depts;
    }
    
    public Departamento getDepartamento(int iddept) throws SQLException{
        Connection cn = this.getConnection();
        String sql = "select * from dept where dept_no=?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, iddept);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            Departamento dept = new Departamento(rs.getInt("DEPT_NO"),
                                                rs.getString("DNOMBRE"),
                                                rs.getString("LOC"));
            rs.close();
            cn.close();
            return dept;
        } else{
            rs.close();
            cn.close();
            return null;
        }
    }
    
    public void insertarDepartamento(int id, String nom, String loc) throws SQLException{
        Connection cn = this.getConnection();
        String sql = "insert into dept values(?,?,?)";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, id);
        pst.setString(2, nom);
        pst.setString(3, loc);
        pst.executeUpdate();
        cn.close();
    }
    
    public void modificarDepartamento(int id, String nom, String loc) throws SQLException{
        Connection cn = this.getConnection();
        String sql = "update dept set dnombre=?, loc=? where dept_no=?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setString(1, nom);
        pst.setString(2, loc);
        pst.setInt(3, id);
        pst.executeUpdate();
        cn.close();
    }
    
    public void eliminarDepartamento(int id) throws SQLException{
        Connection cn = this.getConnection();
        String sql = "delete from dept where dept_no=?";
        PreparedStatement pst = cn.prepareStatement(sql);
        pst.setInt(1, id);
        pst.executeUpdate();
        cn.close();
    }
}
