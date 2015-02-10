/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coa.pittsburgh.policeblotter.model;

/**
 *
 * @author Mark
 */
public class Incidents {

    private String age;
    private Description description;
    private String gender;
    private String incident;
    private String reportName;
    private String incidentdate;
    private String incidenttime;
    private String location;
    private String neighborhood;
    private String zipcode;
    private String zone;

    private String address;
    private String lat;
    private String lng;
    
    private String councildistrict;

    public Incidents() {
    }
    public String InsertIncident() {

        StringBuilder insertdata = new StringBuilder();
        insertdata.append("insert into \"PoliceBlotter2\".Incident(IncidentType,IncidentNumber,IncidentDate,IncidentTime,Address,ZipCode,Neighborhood,lat,lng,Zone,Age,Gender,councildistrict) ");
        insertdata.append("values('");

        insertdata.append(getReportName().trim());
        insertdata.append("','");

        insertdata.append(getIncident().trim());
        insertdata.append("','");

        insertdata.append(getIncidentdate().trim());
        insertdata.append("','");

        insertdata.append(getIncidenttime().trim());

        insertdata.append("','");
        insertdata.append(getAddress().trim());
        insertdata.append("','");
        insertdata.append(getZipcode());
        insertdata.append("','");
        insertdata.append(getNeighborhood().trim());
        insertdata.append("',");
        insertdata.append(getLat());
        insertdata.append(",");
        insertdata.append(getLng());
        insertdata.append(",'");
        insertdata.append(getZone().trim());
        insertdata.append("','");
        insertdata.append(getAge().trim());
        insertdata.append("','");
        insertdata.append(getGender().trim());
        insertdata.append("',");
        insertdata.append(getCouncildistrict());

        insertdata.append(");");

        return insertdata.toString();
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getIncidentdate() {
        return incidentdate;
    }

    public void setIncidentdate(String incidentdate) {
        this.incidentdate = incidentdate;
    }

    public String getIncidenttime() {
        return incidenttime;
    }

    public void setIncidenttime(String incidenttime) {
        this.incidenttime = incidenttime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    

    

    private String removesinglequote(String s) {
        s = s.replaceAll("'", "");

        return s;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCouncildistrict() {
        return councildistrict;
    }

    public void setCouncildistrict(String councildistrict) {
        this.councildistrict = councildistrict;
    }

}
