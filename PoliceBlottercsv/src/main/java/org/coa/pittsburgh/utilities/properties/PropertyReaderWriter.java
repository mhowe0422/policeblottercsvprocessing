/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coa.pittsburgh.utilities.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author Mark
 */
public class PropertyReaderWriter {

    private String host;
    private String database;
    private String dbuser;
    private String dbpassword;
    private String schema;
    private String filepath;
    private String key;
    private int daysback;

    private String remotedbhost;
    private String remotedbname;
    private String remotedbuser;
    private String remotedbpassword;

    private String RDBhost;
    private String RDBname;
    private String RDBuser;
    private String RDBpassword;
    private String RDBSchema;

    private String logpath;
    private String pdfpath;
    private String txtpath;
    private String csvpath;

    public static void main(String[] args) {
        PropertyReaderWriter prw = new PropertyReaderWriter();
        prw.setProperties();
        prw.getProperties();
        prw.printProperties();
    }

    public void setProperties() {

        Properties prop = new Properties();
        OutputStream output = null;

        try {

            output = new FileOutputStream("C:\\pgh-blotter-parser-master\\resources\\config.properties");
            //output = new FileOutputStream("resources/config.properties");

            // set the properties value
            //Locate database information
            prop.setProperty("host", "localhost");
            prop.setProperty("database", "postgres");
            prop.setProperty("dbuser", "postgres");
            prop.setProperty("dbpassword", "win95sux");
            prop.setProperty("schema", "public");

            //Local path to data
            prop.setProperty("filepath", "C:\\pgh-blotter-parser-master\\");
            //prop.setProperty("filepath", "/home/ec2-user/scripts");
            //Google Key
            prop.setProperty("key", "AIzaSyCb3EA0lfao273s6Jkp8tfTzJfUSkswpOw");

            //Used for setting the number of days back to look
            prop.setProperty("daysback", "1");

            // remote database
            prop.setProperty("remotedbhost", "blotter.ca5wksbwkzsv.us-east-1.rds.amazonaws.com");
            prop.setProperty("remotedbname", "blotter");
            prop.setProperty("remotedbuser", "arm5077");
            prop.setProperty("remotedbpassword", "lukebryan");

            //RDB database
            prop.setProperty("RDBhost", "cfapghpoliceblotter.cnsbqqmktili.us-east-1.rds.amazonaws.com");
            prop.setProperty("RDBname", "CfAPGHPoliceBlotter");
            prop.setProperty("RDBuser", "CfAPGHPoliceBltr");
            prop.setProperty("RDBpassword", "CfAPGH2015");
            prop.setProperty("RDBSchema", "PoliceBlotter2");

            //PATH
            prop.setProperty("LogPath", prop.getProperty("filepath") + "logs");
            prop.setProperty("PDFPath", prop.getProperty("filepath") + "pdf");
            prop.setProperty("TextPath", prop.getProperty("filepath") + "txt2");
            prop.setProperty("CsvPath", prop.getProperty("filepath") + "csv");

            //prop.setProperty("LogPath", prop.getProperty("filepath")+"/log");
            //prop.setProperty("PDFPath", prop.getProperty("filepath")+"/pdf");
            //prop.setProperty("TextPath", prop.getProperty("filepath")+"/txt");
            // save properties to project root folder
            prop.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public void getProperties() {

        Properties prop = new Properties();
        InputStream input = null;

        try {

            input = new FileInputStream("C:\\pgh-blotter-parser-master\\resources\\config.properties");
            //input = new FileInputStream("resources/config.properties");
            // load a properties file
            prop.load(input);

            // get the property value and store it
            setHost(prop.getProperty("host"));
            setDatabase(prop.getProperty("database"));
            setDbuser(prop.getProperty("dbuser"));
            setDbpassword(prop.getProperty("dbpassword"));
            setSchema(prop.getProperty("schema"));
            
            setFilepath(prop.getProperty("filepath"));
            setKey(prop.getProperty("key"));
            setDaysback(prop.getProperty("daysback"));

            // remote db
            setRemotedbhost(prop.getProperty("remotedbhost"));
            setRemotedbname(prop.getProperty("remotedbname"));
            setRemotedbuser(prop.getProperty("remotedbuser"));
            setRemotedbpassword(prop.getProperty("remotedbpassword"));

            // RDB
            setRDBhost(prop.getProperty("RDBhost"));
            setRDBname(prop.getProperty("RDBname"));
            setRDBuser(prop.getProperty("RDBuser"));
            setRDBpassword(prop.getProperty("RDBpassword"));
            setRDBSchema(prop.getProperty("RDBSchema"));

            // PATH 
            setLogpath(prop.getProperty("LogPath"));
            setPdfpath(prop.getProperty("PDFPath"));
            setTxtpath(prop.getProperty("TextPath"));
            setCsvpath(prop.getProperty("CsvPath"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getDbuser() {
        return dbuser;
    }

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getDaysback() {
        return daysback;
    }

    public void setDaysback(String daysback) {
        this.daysback = Integer.parseInt(daysback);
    }

    public String getRemotedbhost() {
        return remotedbhost;
    }

    public void setRemotedbhost(String remotedbhost) {
        this.remotedbhost = remotedbhost;
    }

    public String getRemotedbname() {
        return remotedbname;
    }

    public void setRemotedbname(String remotedbname) {
        this.remotedbname = remotedbname;
    }

    public String getRemotedbuser() {
        return remotedbuser;
    }

    public void setRemotedbuser(String remotedbuser) {
        this.remotedbuser = remotedbuser;
    }

    public String getRemotedbpassword() {
        return remotedbpassword;
    }

    public void setRemotedbpassword(String remotedbpassword) {
        this.remotedbpassword = remotedbpassword;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getRDBhost() {
        return RDBhost;
    }

    public void setRDBhost(String RDBhost) {
        this.RDBhost = RDBhost;
    }

    public String getRDBname() {
        return RDBname;
    }

    public void setRDBname(String RDBname) {
        this.RDBname = RDBname;
    }

    public String getRDBuser() {
        return RDBuser;
    }

    public void setRDBuser(String RDBuser) {
        this.RDBuser = RDBuser;
    }

    public String getRDBpassword() {
        return RDBpassword;
    }

    public void setRDBpassword(String RDBpassword) {
        this.RDBpassword = RDBpassword;
    }

    public String getLogpath() {
        return logpath;
    }

    public void setLogpath(String logpath) {
        this.logpath = logpath;
    }

    public String getPdfpath() {
        return pdfpath;
    }

    public void setPdfpath(String pdfpath) {
        this.pdfpath = pdfpath;
    }

    public String getTxtpath() {
        return txtpath;
    }

    public void setTxtpath(String txtpath) {
        this.txtpath = txtpath;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getRDBSchema() {
        return RDBSchema;
    }

    public void setRDBSchema(String RDBSchema) {
        this.RDBSchema = RDBSchema;
    }

    public String getCsvpath() {
        return csvpath;
    }

    public void setCsvpath(String csvpath) {
        this.csvpath = csvpath;
    }

    private void printProperties() {
        /*
         private String database;
         private String dbuser;
         private String dbpassword;
         private String filepath;
         private String key;
         private int daysback;
         private String remotedbhost;
         private String remotedbname;
         private String remotedbuser;
         private String remotedbpassword;
         */
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("filepath " + getFilepath());
        System.out.println("key " + getKey());
        System.out.println("days back " + getDaysback());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Local");
        System.out.println("local host " + getHost());
        System.out.println("local database " + getDatabase());
        System.out.println("local dbuser " + getDbuser());
        System.out.println("local dbpassword " + getDbpassword());
        System.out.println("local schema " + getSchema());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Remote");
        System.out.println("remote host " + getRemotedbhost());
        System.out.println("remotedbname " + getRemotedbname());
        System.out.println("remotedbuser " + getRemotedbuser());
        System.out.println("remotedbpassword " + getRemotedbpassword());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("RDB");
        System.out.println("RDB host " + getRDBhost());
        System.out.println("RDB name " + getRDBname());
        System.out.println("RDB user " + getRDBuser());
        System.out.println("RDB password " + getRDBpassword());
        System.out.println("RDB schema " + getRDBSchema());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Path Information");
        System.out.println("Log Path " + getLogpath());
        System.out.println("PDF Path " + getPdfpath());
        System.out.println("Text Path " + getTxtpath());
        System.out.println("csv Path " + getCsvpath());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++");
    }
}
