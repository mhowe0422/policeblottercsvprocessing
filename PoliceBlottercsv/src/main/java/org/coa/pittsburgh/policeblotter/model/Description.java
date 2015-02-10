/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coa.pittsburgh.policeblotter.model;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mark
 */
public class Description {

    private String description;
    private String section;

    public Description() {

    }

    public String setInsert(String section, String description) {
        //insert into "PoliceBlotter".geocode(address,city,state,zipcode,lat,lon,query) values('400 Cedar Avenue','Pittsburgh','PA','15212',40.4518259,-80.00079699999999,'400 Cedar Avenue, Pittsburgh, PA 15212, USA');
        //insert into "PoliceBlotter".geocode(address,city,state,zipcode,lat,lon,query) (select '1200 Western Avenue' as address, 'Pittsburgh' as city,'PA' as state,'15233' as zipcode,40.4509827 as lat, -80.02170389999999 as lon,'1200 Western Avenue, Pittsburgh, PA 15233, USA' as query where
        //not exists (select address from "PoliceBlotter".geocode where address='1200 Western Avenue'));

        StringBuffer insertdata = new StringBuffer();

        //String rc = "FAILURE";
        int descriptionlength = description.length();
        int space = 0;
        for (int i = 0; i < descriptionlength; i++) {
            if (description.charAt(i) == ' ') {
                space = i;
                break;
            }
        }

        //String section = description.substring(0, space).trim();
        setSection(section);
        String descriptiontitle = description.substring(space, descriptionlength).trim();

        insertdata.append("insert into \"PoliceBlotter2\".description(section,description) (select '");
        insertdata.append(section);
        insertdata.append("' as section,'");
        insertdata.append(removesinglequote(descriptiontitle));
        insertdata.append("' as description where not exists (select section from \"PoliceBlotter2\".description where section='");
        insertdata.append(section);
        insertdata.append("'));");
        return insertdata.toString();
    }
public String setInsert( String description) {
        //insert into "PoliceBlotter".geocode(address,city,state,zipcode,lat,lon,query) values('400 Cedar Avenue','Pittsburgh','PA','15212',40.4518259,-80.00079699999999,'400 Cedar Avenue, Pittsburgh, PA 15212, USA');
        //insert into "PoliceBlotter".geocode(address,city,state,zipcode,lat,lon,query) (select '1200 Western Avenue' as address, 'Pittsburgh' as city,'PA' as state,'15233' as zipcode,40.4509827 as lat, -80.02170389999999 as lon,'1200 Western Avenue, Pittsburgh, PA 15233, USA' as query where
        //not exists (select address from "PoliceBlotter".geocode where address='1200 Western Avenue'));

        StringBuffer insertdata = new StringBuffer();

        //String rc = "FAILURE";
        int descriptionlength = description.length();
        int space = 0;
        for (int i = 0; i < descriptionlength; i++) {
            if (description.charAt(i) == ' ') {
                space = i;
                break;
            }
        }

        //String section = description.substring(0, space).trim();
        setSection(section);
        String descriptiontitle = description.substring(space, descriptionlength).trim();

        insertdata.append("insert into \"PoliceBlotter2\".description(section,description) (select '");
        insertdata.append(section);
        insertdata.append("' as section,'");
        insertdata.append(removesinglequote(descriptiontitle));
        insertdata.append("' as description where not exists (select section from \"PoliceBlotter2\".description where section='");
        insertdata.append(section);
        insertdata.append("'));");
        return insertdata.toString();
    }
    public void writeDescription(ArrayList description, Statement[] statementboth) {

        int length;
        length = description.size();
        System.out.println("++++++++++++++++++++++++++write description+++++++++++++++++++++++++++++++++++++++");
        for (int i = 0; i < length; i++) {
            System.out.println(description.get(i).toString());
            String sql = setInsert(description.get(i).toString());
            System.out.println(sql);
            try {
                statementboth[0].execute(sql);
                statementboth[1].execute(sql);
// need to write sql
            } catch (SQLException ex) {
                Logger.getLogger(Description.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("+++++++++++++++++++++++++++end description++++++++++++++++++++++++++++++++++++++++");
    }

    public void writeDescription(String section,String description, Statement[] statementboth) {

        System.out.println("++++++++++++++++++++++++++write description+++++++++++++++++++++++++++++++++++++++");

        System.out.println(description);
        String sql = setInsert(section,description);
        System.out.println(sql);
        try {
            statementboth[0].execute(sql);
            statementboth[1].execute(sql);
// need to write sql
        } catch (SQLException ex) {
            Logger.getLogger(Description.class.getName()).log(Level.SEVERE, null, ex);

        }
        System.out.println("+++++++++++++++++++++++++++end description++++++++++++++++++++++++++++++++++++++++");
    }

    private String removesinglequote(String s) {
        s = s.replaceAll("'", "");

        return s;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

}
