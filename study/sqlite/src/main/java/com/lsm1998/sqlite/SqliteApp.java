/**
 * 作者：刘时明
 * 时间：2021/3/11
 */
package com.lsm1998.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqliteApp
{
    public static void main(String[] args) throws Exception
    {
        String url = "jdbc:sqlite:/Users/liushiming/opt/sqlite/test.db";
        Connection conn = getConnection(url);
        Statement stmt = conn.createStatement();
        // createTable(stmt);
        showTable(stmt);
    }

    private static Connection getConnection(String url) throws Exception
    {
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(url);
    }

    private static int createTable(Statement stmt) throws Exception
    {
        String sql = "CREATE TABLE COMPANY "
                + "(ID INT PRIMARY KEY NOT NULL,"
                + " NAME TEXT NOT NULL,"
                + " AGE INT NOT NULL,"
                + " ADDRESS CHAR(50),"
                + " SALARY REAL)";
        return stmt.executeUpdate(sql);
    }

    private static void showTable(Statement stmt) throws Exception
    {
        String sql = "SELECT name FROM sqlite_master WHERE type='table' ORDER BY name;  ";
        ResultSet resultSet = stmt.executeQuery(sql);
        while (resultSet.next())
        {
            System.out.println(resultSet.getObject(1));
        }
    }
}
