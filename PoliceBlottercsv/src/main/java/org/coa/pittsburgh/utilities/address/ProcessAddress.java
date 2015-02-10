/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coa.pittsburgh.utilities.address;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coa.pittsburgh.utilities.database.connection.PostgresConnection;
import org.coa.pittsburgh.utilities.geocoder.GeoCodejsonAddress;

/**
 *
 * @author Mark
 */
public class ProcessAddress {

    CouncilDistrict cdt;
    GeoCodejsonAddress gcja;

    //Timer timer;
    private String TestAddress;
    private String Neighborhood;
    private String lat;
    private String lng;
    private String councildistrict;
    private String zipcode;

    private boolean local;

    private Connection con;
    public Connection[] conboth = new Connection[2];

    public long StartTime;
    public long EndTime;

    public ProcessAddress() {

        gcja = new GeoCodejsonAddress();
    }

    public ProcessAddress(Connection con, boolean local) {
        cdt = new CouncilDistrict(con);
        gcja = new GeoCodejsonAddress();
        setCon(con);
        setLocal(local);

        //pc = new PostgresConnection();
    }

    public ProcessAddress(Connection[] conboth, boolean local) {
        cdt = new CouncilDistrict(conboth);
        gcja = new GeoCodejsonAddress();
        //setCon(con);
        setLocal(local);

        //pc = new PostgresConnection();
    }

    public static void main(String[] args) throws SQLException {
        ProcessAddress at = new ProcessAddress();
        PostgresConnection pc = new PostgresConnection();
        pc.getPostgresConnection(true);
        at.conboth = pc.getConboth();

        at.gcja = new GeoCodejsonAddress();

        at.cdt = new CouncilDistrict(at.conboth);
        at.setTestAddress("202  OAK BROOK CIR");
        at.setNeighborhood("Westwood");
        at.CountyAddress(true, true,at.conboth);

        //at.AddressWithNeighborhood();
        //at.AddressWithoutNeighborhood();
    }

    public void DistrictTest() {

        cdt.setLat(getLat());
        cdt.setLng(getLng());

        try {
            cdt.CouncilDistrictLookup(isLocal());
        } catch (SQLException ex) {
            Logger.getLogger(ProcessAddress.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCouncildistrict(cdt.getCouncil());

        System.out.println("Council District for " + getTestAddress() + " is " + getCouncildistrict());
    }
    
        public void DistrictTest(boolean local,boolean both,Connection[] conboth) {

        cdt.setLat(getLat());
        cdt.setLng(getLng());

        try {
            cdt.CouncilDistrictLookup(local,both,conboth);
        } catch (SQLException ex) {
            Logger.getLogger(ProcessAddress.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCouncildistrict(cdt.getCouncil());

        System.out.println("Council District for " + getTestAddress() + " is " + getCouncildistrict());
    }

    public void CountyAddress() {
        StartTimer("CountyAddress");
        //GeoCodejsonAddress gcja = new GeoCodejsonAddress();
        gcja.setQueryAddress(getTestAddress());
        boolean foundAddress = gcja.URLTest();

        System.out.println("Found Address: " + foundAddress);
        if (foundAddress) {
            gcja.printResults();
        }

        setLat(gcja.getLat());
        setLng(gcja.getLng());
        setZipcode(gcja.getZipcode());
        System.out.println("ZIP CODE " + getZipcode());

        if (getLat() == null || getLng() == null) {
            System.out.println("Location lat/lng is not available");
        } else {
            DistrictTest();
        }

        EndTimer();

    }
    
    public void CountyAddress(boolean local, boolean both, Connection[] conboth) {
        StartTimer("CountyAddress");
        //GeoCodejsonAddress gcja = new GeoCodejsonAddress();
        gcja.setQueryAddress(getTestAddress());
        boolean foundAddress = gcja.URLTest();

        System.out.println("Found Address: " + foundAddress);
        if (foundAddress) {
            gcja.printResults();
        }

        setLat(gcja.getLat());
        setLng(gcja.getLng());
        setZipcode(gcja.getZipcode());
        System.out.println("ZIP CODE " + getZipcode());

        if (getLat() == null || getLng() == null) {
            System.out.println("Location lat/lng is not available");
        } else {
            DistrictTest(true,true,conboth);
        }

        EndTimer();

    }

    public void StartTimer(String TestingName) {
        System.out.println("Started " + TestingName);
        StartTime = System.currentTimeMillis();

    }

    public void EndTimer() {
        EndTime = System.currentTimeMillis();
        System.out.println("Run Time: " + (EndTime - StartTime));
    }

    public String getTestAddress() {
        return TestAddress;
    }

    public void setTestAddress(String TestAddress) {
        this.TestAddress = TestAddress;
    }

    public String getNeighborhood() {
        return Neighborhood;
    }

    public void setNeighborhood(String Neighborhood) {
        this.Neighborhood = Neighborhood;
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

    public String getCouncildistrict() {
        return councildistrict;
    }

    public void setCouncildistrict(String councildistrict) {
        this.councildistrict = councildistrict;
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

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
