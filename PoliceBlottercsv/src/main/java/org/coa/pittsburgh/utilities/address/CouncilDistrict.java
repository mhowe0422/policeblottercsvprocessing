/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coa.pittsburgh.utilities.address;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.coa.pittsburgh.utilities.database.connection.PostgresConnection;
import org.coa.pittsburgh.utilities.properties.PropertyReaderWriter;

/**
 *
 * @author Mark
 */
public class CouncilDistrict {

    private String lat;
    private String lng;
    private String council;
    private Connection con;
    public Connection[] conboth;
    public Statement[] statement;

    PropertyReaderWriter prw;

    public CouncilDistrict() {
        PostgresConnection pc = new PostgresConnection();
        pc.getPostgresConnection(true);
        conboth = pc.getConboth();
        prw = new PropertyReaderWriter();
    }

    public CouncilDistrict(Connection con) {

        prw = new PropertyReaderWriter();
        setCon(con);

    }

    public CouncilDistrict(Connection[] conboth) {
        System.out.println("Council District Connection[]");
        System.out.println(conboth[0]);
        System.out.println(conboth[1]);

        prw = new PropertyReaderWriter();
        //setCon(con);

    }

    public static void main(String[] args) throws SQLException {
        CouncilDistrict cdt = new CouncilDistrict();
        cdt.setLat("40.455693639252189");
        cdt.setLng("-79.999753144307306");
        cdt.CouncilDistrictLookup(true, true, cdt.conboth);
    }

    public void CouncilDistrictLookup(boolean local) throws SQLException {

        prw.getProperties();

        String schema;
        if (local) {
            schema = prw.getSchema();
        } else {
            schema = prw.getRDBSchema();
        }
        setCouncil("0");
        //PostgresConnection pc = new PostgresConnection();
        //pc.getPostgresConnection("local");

        String SQL = "SELECT distinct gid,council from \"" + schema + "\".testdistrict3 where st_contains (geom,ST_GeomFromText('POINT(" + getLng() + " " + getLat() + ")',0)) = 't'";
        System.out.println(SQL);

        Statement stmt = getCon().createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            String gid = rs.getString("gid");
            setCouncil(rs.getString("council"));

            //System.out.println(rs.getString(2));
        }
        System.out.println("Council District " + getCouncil());
        stmt.close();
        rs.close();
        //pc.getCon().close();

    }

    public void CouncilDistrictLookup(boolean local, boolean both, Connection[] conboth) throws SQLException {

        prw.getProperties();

        String[] schema = new String[2];
        String[] SQLboth = new String[2];
        statement = new Statement[2];

        if (local) {
            schema[0] = prw.getSchema();
            schema[1] = prw.getRDBSchema();
        } else {
            schema[1] = prw.getRDBSchema();
        }
        setCouncil("0");
        //PostgresConnection pc = new PostgresConnection();
        //pc.getPostgresConnection("local");

        SQLboth[0] = "SELECT distinct gid,council from \"" + schema[0] + "\".testdistrict3 where st_contains (geom,ST_GeomFromText('POINT(" + getLng() + " " + getLat() + ")',0)) = 't'";
        System.out.println(SQLboth[0]);
        SQLboth[1] = "SELECT distinct gid,council from \"" + schema[1] + "\".testdistrict3 where st_contains (geom,ST_GeomFromText('POINT(" + getLng() + " " + getLat() + ")',0)) = 't'";
        System.out.println(SQLboth[1]);

        System.out.println("conboth[0] " + conboth[0]);
        statement[0] = conboth[0].createStatement();
        ResultSet rs = statement[0].executeQuery(SQLboth[0]);
        while (rs.next()) {
            //String gid = rs.getString("gid");
            setCouncil(rs.getString("council"));

            //System.out.println(rs.getString(2));
        }
        System.out.println("Council District [0] " + getCouncil());
        statement[0].close();
        rs.close();
        System.out.println("conboth[1] " + conboth[1]);
        statement[1] = conboth[1].createStatement();
        rs = statement[1].executeQuery(SQLboth[1]);
        while (rs.next()) {
            //String gid = rs.getString("gid");
            setCouncil(rs.getString("council"));

            //System.out.println(rs.getString(2));
        }
        System.out.println("Council District [1] " + getCouncil());
        statement[1].close();
        rs.close();
        //pc.getCon().close();

    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getCouncil() {
        return council;
    }

    public void setCouncil(String council) {
        this.council = council;
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

    public Statement[] getStatement() {
        return statement;
    }

    public void setStatement(Statement[] statement) {
        this.statement = statement;
    }

}
