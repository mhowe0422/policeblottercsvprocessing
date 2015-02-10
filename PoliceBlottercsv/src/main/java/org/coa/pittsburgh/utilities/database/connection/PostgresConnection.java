/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 incidentid serial NOT NULL,            //0
 incidenttype character(20),          //1
 incidentnumber integer,              //2
 incidentdate date,                   //3
 incidenttime time without time zone, //4
 address character(120),              //5
 zipcode character(15),               //6
 neighborhood character(30),          //7
 lat numeric,                         //8
 lng numeric,                         //9
 zone character(10),                  //10
 age character(3),                    //11
 gender character(1),                 //12
 */
package org.coa.pittsburgh.utilities.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coa.pittsburgh.utilities.properties.PropertyReaderWriter;

/**
 *
 * @author Mark
 */
public class PostgresConnection {

    private Connection con;
    private Connection[] conboth;
    private String connectionUrl;
    private String[] connectionUrlboth;
    PostgresConnection pc;
    private String connectiontype;

    public PostgresConnection() {

    }

    public static void main(String[] args) {
        PostgresConnection pc = new PostgresConnection();
        pc.setConnectiontype("local");
        pc.getPostgresConnection();
        pc.setConnectiontype("RDS");
        pc.getPostgresConnection();
        pc.getPostgresConnection(true);

    }

    public void getPostgresConnection() {
        PropertyReaderWriter prw = new PropertyReaderWriter();
        prw.getProperties();
        String user;
        String password;
        String host;
        String databasename;
        if (getConnectiontype().equals("RDS")) {
            user = prw.getRDBuser();
            password = prw.getRDBpassword();
            host = prw.getRDBhost();
            databasename = prw.getRDBname();
        } else {
            user = prw.getDbuser();
            password = prw.getDbpassword();
            host = prw.getHost();
            databasename = prw.getDatabase();
        }

        setConnectionUrl("jdbc:postgresql://" + host + ":5432/" + databasename + "?user=" + user + "&password=" + password);
        System.out.println(getConnectionUrl());

        try {
            // Establish the connection.
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostgresConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }

        try {
            con = DriverManager.getConnection(connectionUrl);
            setCon(con);
        } catch (SQLException ex) {
            Logger.getLogger(PostgresConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(2);
        }
    }

    public void getPostgresConnection(boolean both) {
        PropertyReaderWriter prw = new PropertyReaderWriter();
        prw.getProperties();
        String user;
        String password;
        String host;
        String databasename;
        
        conboth = new Connection[2];
        connectionUrlboth = new String[2];

        System.out.println("Both connections");
        user = prw.getDbuser();
        password = prw.getDbpassword();
        host = prw.getHost();
        databasename = prw.getDatabase();
        connectionUrlboth[0] = ("jdbc:postgresql://" + host + ":5432/" + databasename + "?user=" + user + "&password=" + password);
        System.out.println(connectionUrlboth[0]);

        user = prw.getRDBuser();
        password = prw.getRDBpassword();
        host = prw.getRDBhost();
        databasename = prw.getRDBname();
        connectionUrlboth[1] = ("jdbc:postgresql://" + host + ":5432/" + databasename + "?user=" + user + "&password=" + password);
        System.out.println(connectionUrlboth[1]);

        try {
            // Establish the connection.
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostgresConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }

        try {
            conboth[0] = DriverManager.getConnection(connectionUrlboth[0]);
            conboth[1] = DriverManager.getConnection(connectionUrlboth[1]);
            //setCon(con);
        } catch (SQLException ex) {
            Logger.getLogger(PostgresConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(2);
        }
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Connection[] getConboth() {
        return conboth;
    }

    public void setConboth(Connection[] conboth) {
        this.conboth = conboth;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String[] getConnectionUrlboth() {
        return connectionUrlboth;
    }

    public void setConnectionUrlboth(String[] connectionUrlboth) {
        this.connectionUrlboth = connectionUrlboth;
    }

    public String getConnectiontype() {
        return connectiontype;
    }

    public void setConnectiontype(String connectiontype) {
        this.connectiontype = connectiontype;
    }

}
