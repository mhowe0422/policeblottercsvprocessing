package org.coa.pittsburgh.utilities.readers.text;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.coa.pittsburgh.policeblotter.model.Incidents;
import org.coa.pittsburgh.policeblotter.model.Description;
import org.coa.pittsburgh.utilities.address.ProcessAddress;
import org.coa.pittsburgh.utilities.database.connection.PostgresConnection;
import org.coa.pittsburgh.utilities.database.connection.WriteDatabaseRecords;
import org.coa.pittsburgh.utilities.dateinformation.DateProcessing;
import org.coa.pittsburgh.utilities.properties.PropertyReaderWriter;

/**
 *
 * @author Mark
 */
public class ProcessCSVFile {

    private int MCount;
    private int FCount;
    private int UCount;
    private int TotalCount;
    private int GenderMissing;
    private int AgeMissing;
    private int NoAgeorGender;
    private int Arrest;
    private int Offense;
    private int Both;
    private int councildistrict;

    File input;
    File output;
    File alldescriptions;
    BufferedWriter wr;
    BufferedWriter awr;
    BufferedReader br;
    Connection con;
    public Connection[] conboth = new Connection[2];

    private String Inputfilename;
    private String Outputfilename;
    private String FileName;
    private String IncidentDate;

    private boolean local;
    boolean both;

    //Incidents incidents;
    WriteDatabaseRecords wdbr;
    ProcessAddress pa;
    DateProcessing dp;
    PropertyReaderWriter prw;
    public static PostgresConnection pc;

    public ProcessCSVFile() {
    }

    public static void main(String[] args) {
        ProcessCSVFile pcf = new ProcessCSVFile();
        try {
            pcf.both = true;
            pcf.StartUP();
        } catch (IOException ex) {
            Logger.getLogger(ProcessCSVFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ProcessCSVFile.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void StartUP() throws IOException, SQLException {

        pc = new PostgresConnection();
        dp = new DateProcessing();
        prw = new PropertyReaderWriter();
        String[] local = new String[2];
        local[0] = "local";
        local[1] = "RDS";
        prw.getProperties();
        String filepath = prw.getFilepath();

        int daysback = prw.getDaysback();
        dp.setDaysBack(daysback);
        dp.Mydate();
        System.out.println("**************************************");
        //System.out.println("Incident Date " + dp.getYesterdayIncident());
        setIncidentDate(dp.getYesterdayIncident());
        System.out.println("Incident Date " + getIncidentDate());
        // needs to be arrest_blotter_DOW.csv like arrest_blotter_Wednesday.csv
        // oops change of naming
        // blotter_wednesday20150204.csv
        String DOW = dp.getArrestBlotterFileName(); // passed value equals days back
        System.out.println("Arrest Blotter File " + DOW);
        //String DOW = dp.getIncidentFileNameDate(daysback); // passed value equals days back
        setInputfilename(filepath + "csv\\" + DOW);
        //setOutputfilename(filepath + "support2\\Address" + DOW + ".txt"); // using txt2 for testing purposes

        System.out.println(getInputfilename());
        if (!both) {

            for (int i = 0; i < 2; i++) {
                pc.setConnectiontype(local[i]); //this the only one to change RDS or local

                pc.getPostgresConnection();
                setCon(pc.getCon());
                wdbr = new WriteDatabaseRecords(getCon());
                if (pc.getConnectiontype().equals("local")) {
                    setLocal(true);
                } else {
                    setLocal(false);
                }

                pa = new ProcessAddress(getCon(), isLocal());
                ProcessTextFile();
                printTotals();
                getCon().close();
            }
        } else {

            // adding data to both databases
            pc.getPostgresConnection(true);
            conboth = pc.getConboth();
            wdbr = new WriteDatabaseRecords(conboth);
            setLocal(true);
            //both = true;

            pa = new ProcessAddress(conboth, isLocal());
            ProcessTextFile();
            printTotals();

        }
    }

    public void ProcessTextFile() throws FileNotFoundException, IOException, SQLException {

        input = new File(getInputfilename());  // The txt file 
        br = new BufferedReader(new InputStreamReader(new FileInputStream(input)));
        String line;

        while ((line = br.readLine()) != null) {
            if (!line.contains("CCR")) {
                ProcessLine(line);
            }
        }
        br.close();
    }

    public void ProcessLine(String line) throws IOException, SQLException {
        Incidents incidents = new Incidents();
        String[] pl = new String[20];
        System.out.println(line);
        //parse line
        //REPORT_NAME,CCR,SECTION,DESCRIPTION,ARREST_TIME,ADDRESS,NEIGHBORHOOD,ZONE,AGE,SEX
        pl = line.split(",");
        int pl_length = pl.length;
        System.out.println("size " + pl.length);
        for (int i = 0; i < pl_length; i++) {
            System.out.println("line[" + i + "] " + pl[i]);
        }
        incidents.setAddress(pl[5]);
        pa.setTestAddress(incidents.getAddress());
        pa.setNeighborhood(pl[6]);
        if(both)
        {
        pa.CountyAddress(true,true,conboth); // includes lat/lng/councildistrict
        }
        else
        {
           pa.CountyAddress(); // includes lat/lng/councildistrict 
        }
        if (pl_length >= 9) {
            incidents.setAge(pl[8]);
        } else {
            incidents.setAge("");
        }
        incidents.setCouncildistrict(pa.getCouncildistrict());
        //calculate council district
        //process section and description
        // processSectionDescription(pl[2], pl[3],incidents);
        //incidents.setDescription(null);
        if (pl_length == 10) {
            incidents.setGender(pl[9]);
        } else {
            incidents.setGender("");
        }
        incidents.setIncident(pl[1]);
        incidents.setIncidentdate(getIncidentDate());
        incidents.setIncidenttime(pl[4]);
        //calculate lat/lng from address
        incidents.setLat(pa.getLat());
        incidents.setLng(pa.getLng());
        incidents.setNeighborhood(pl[6]);
        incidents.setReportName(pl[0]);
        incidents.setZipcode(pa.getZipcode());
        incidents.setZone("Zone " + pl[7]);

        OutputIncidents(incidents);
        processSectionDescription(pl[2], pl[3], incidents);

//update counts           
    }

    public void processSectionDescription(String section, String description, Incidents incidents) {
        Description descript = new Description();
        descript.writeDescription(section, description, wdbr.getStatementboth());
        //setSection(descript.getSection());
        String incidentDescription = insertIncidentDescription(section, incidents);
        try {
            wdbr.WriteSQL(incidentDescription,true);
        } catch (SQLException ex) {
            Logger.getLogger(ProcessCSVFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(incidentDescription);
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
    }

    private String insertIncidentDescription(String section, Incidents incidents) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into \"PoliceBlotter2\".IncidentDescription(incidentnumber,incidentdate,descriptionid) \n"
                + "values(" + incidents.getIncident()
                + ",'" + incidents.getIncidentdate()
                + "',(select descriptionid from \"PoliceBlotter2\".description where section = '"
                + section + "'))");
        return sql.toString();
    }

    public void OutputIncidents(Incidents incidents) {
        System.out.println(incidents.InsertIncident());
        // System.out.println("Closed " + wdbr.getStatement().isClosed());
        try {

            wdbr.WriteSQL(incidents.InsertIncident(),true);
                // TODO: fix duplicates
            // wdbr.WriteSQL(GeoAddressInsert);

            //}
        } catch (SQLException ex) {
            Logger.getLogger(ProcessCSVFile.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR: " + incidents.InsertIncident());
        }
        // } else {
        //     String urlerrordata = incidents.getIncidentdate() + "," + incidents.getIncident() + "," + gcja.getUrladdress();
        //     wfua.setData(urlerrordata);
        //     wfua.writeURL();

    }

    public void printTotals() {
        System.out.println("Arrest Count:" + getArrest());
        System.out.println("Offense Count:" + getOffense());
        System.out.println("Both Count:" + getBoth());
        System.out.println("M Count:" + getMCount());
        System.out.println("F Count:" + getFCount());
        System.out.println("U Count:" + getUCount());
        System.out.println("Age Missing Count:" + getAgeMissing());
        System.out.println("Gender Missing Count:" + getGenderMissing());
        System.out.println("No Age or Gender Count:" + getNoAgeorGender());
        System.out.println("Total Count:" + getTotalCount());
    }

    public int getMCount() {
        return MCount;
    }

    public void setMCount(int MCount) {
        this.MCount = MCount;
    }

    public int getFCount() {
        return FCount;
    }

    public void setFCount(int FCount) {
        this.FCount = FCount;
    }

    public int getUCount() {
        return UCount;
    }

    public void setUCount(int UCount) {
        this.UCount = UCount;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public int getGenderMissing() {
        return GenderMissing;
    }

    public void setGenderMissing(int GenderMissing) {
        this.GenderMissing = GenderMissing;
    }

    public int getAgeMissing() {
        return AgeMissing;
    }

    public void setAgeMissing(int AgeMissing) {
        this.AgeMissing = AgeMissing;
    }

    public int getNoAgeorGender() {
        return NoAgeorGender;
    }

    public void setNoAgeorGender(int NoAgeorGender) {
        this.NoAgeorGender = NoAgeorGender;
    }

    public int getArrest() {
        return Arrest;
    }

    public void setArrest(int Arrest) {
        this.Arrest = Arrest;
    }

    public int getOffense() {
        return Offense;
    }

    public void setOffense(int Offense) {
        this.Offense = Offense;
    }

    public int getBoth() {
        return Both;
    }

    public void setBoth(int Both) {
        this.Both = Both;
    }

    public int getCouncildistrict() {
        return councildistrict;
    }

    public void setCouncildistrict(int councildistrict) {
        this.councildistrict = councildistrict;
    }

    public File getInput() {
        return input;
    }

    public void setInput(File input) {
        this.input = input;
    }

    public File getOutput() {
        return output;
    }

    public void setOutput(File output) {
        this.output = output;
    }

    public File getAlldescriptions() {
        return alldescriptions;
    }

    public void setAlldescriptions(File alldescriptions) {
        this.alldescriptions = alldescriptions;
    }

    public BufferedWriter getWr() {
        return wr;
    }

    public void setWr(BufferedWriter wr) {
        this.wr = wr;
    }

    public BufferedWriter getAwr() {
        return awr;
    }

    public void setAwr(BufferedWriter awr) {
        this.awr = awr;
    }

    public BufferedReader getBr() {
        return br;
    }

    public void setBr(BufferedReader br) {
        this.br = br;
    }

    public String getInputfilename() {
        return Inputfilename;
    }

    public void setInputfilename(String Inputfilename) {
        this.Inputfilename = Inputfilename;
    }

    public String getOutputfilename() {
        return Outputfilename;
    }

    public void setOutputfilename(String Outputfilename) {
        this.Outputfilename = Outputfilename;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public String getIncidentDate() {
        return IncidentDate;
    }

    public void setIncidentDate(String IncidentDate) {
        this.IncidentDate = IncidentDate;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

}
