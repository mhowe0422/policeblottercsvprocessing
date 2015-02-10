/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 {
 "spatialReference": {
 "wkid": 102729,
 "latestWkid": 2272
 },
 "candidates": [
 {
 "address": "OAK BROOK CIR, PITTSBURGH, PA, 15220",
 "location": {
 "x": 1329362.9612931209,
 "y": 408173.76783784968
 },
 "score": 98.420000000000002,
 "attributes": {
    
 }
 },
 {
 "address": "OAK BROOK CIR, PITTSBURGH, PA, 15220",
 "location": {
 "x": 1329323.2739150757,
 "y": 408168.7766432144
 },
 "score": 98.420000000000002,
 "attributes": {
    
 }
 }
 ]
 }
 */
package org.coa.pittsburgh.utilities.geocoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

import org.coa.pittsburgh.utilities.properties.PropertyReaderWriter;
import org.coa.pittsburgh.utilities.writers.text.WriteFailedURLAddress;

/**
 *
 * @author Mark
 */
public class GeoCodejsonAddress {

    private String streetAddress;
    private String formatted_address;
    private String lat;
    private String lng;
    private String key;
    private String queryAddress;
    private String neighborhood;

    private String score;
    private String zipcode;
    private String valuestringaddress;
    private String city;
    private String urladdress;

//public boolean foundAddress;
    WriteFailedURLAddress wfua = null;

    public GeoCodejsonAddress() {
        PropertyReaderWriter prw = new PropertyReaderWriter();
        prw.getProperties();
        setKey(prw.getKey());

        wfua = new WriteFailedURLAddress();
        city = "Pittsburgh";
        //foundAddress = false;
    }

    public static void main(String[] args) {
        GeoCodejsonAddress d = new GeoCodejsonAddress();
        boolean foundAddress = false;
        d.setCity("Pittsburgh");
        //d.setNeighborhood("Outside City");
        d.setNeighborhood("Test Neighborhood");
        d.setQueryAddress("1900 block Spring Garden Ave");

        foundAddress = d.URLTest();
        System.out.println("Found Address: " + foundAddress);
        if (foundAddress) {
            d.printResults();

        } else {
            d.wfua.setData(d.getUrladdress());
            d.wfua.writeURL();
        }
    }

    public boolean URLTest() {

        /* error example
         {
         "error": {
         "code": 499,
         "message": "Token Required",
         "details": []
         }
         }
         */
        //String address = formatAddress(getQueryAddress());
        boolean foundAddress = false;
        boolean score = false;
//        setCouncildistrict("");
//        setLat("");
//        setLng("");
//        setNeighborhood("");
//        setStreetAddress("");
//        setValuestringaddress("");
//        setZipcode("");
//        setScore("");
        String address = formatAddress(getQueryAddress());
        //System.out.println("Neighborhood " + getNeighborhood());
        //if (getNeighborhood().contains("Outside")) {
        //    setCity("");
        //}
        setUrladdress("http://geodata.alleghenycounty.us/arcgis/rest/services/Geocoders/EAMS_Composite_Loc/GeocodeServer/findAddressCandidates?Street=" + address + "&City=" + getCity() + "&State=PA&ZIP=&SingleLine=&outFields=&outSR=4326&searchExtent=&f=pjson");

        //String urladdress = "http://geodata.alleghenycounty.us/arcgis/rest/services/Geocoders/EAMS_Composite_Loc/GeocodeServer/findAddressCandidates?Street=" + address + "&City=" + getCity() + "&State=PA&ZIP=&SingleLine=&outFields=&outSR=4326&searchExtent=&f=pjson";
        System.out.println(getUrladdress());
        int i = 0;
        try {
            URL url = new URL(getUrladdress());

            InputStream is = url.openStream();
            JsonParser parser = Json.createParser(is);

            Event e = parser.next();

            while (parser.hasNext() && (!score)) {

                if (e == Event.START_OBJECT) {

                }
                if (e == Event.KEY_NAME) {
                    System.out.println(e.toString() + " " + parser.getString());
                    if (parser.getString().equals("error")) {
                        System.out.println("Found KEY_NAME error");
                        e = parser.next();
                        if (e == Event.VALUE_STRING) {
                            String testParser = parser.getString();
                            System.out.println(e.toString() + " " + testParser);
                        }
                    }
                    if (parser.getString().equals("code")) {
                        System.out.println("Found KEY_NAME code");
                        e = parser.next();
                        if (e == Event.VALUE_STRING) {
                            String testParser = parser.getString();
                            System.out.println(e.toString() + " " + testParser);
                        }
                    }
                    if (parser.getString().equals("message")) {
                        System.out.println("Found KEY_NAME message");
                        e = parser.next();
                        if (e == Event.VALUE_STRING) {
                            String testParser = parser.getString();
                            System.out.println(e.toString() + " " + testParser);
                        }
                    }

                    if (parser.getString().equals("address")) {
                        System.out.println("Found KEY_NAME address");
                        //foundAddress = true;

                        e = parser.next();
                        if (e == Event.VALUE_STRING) {
                            String testParser = parser.getString();
                            System.out.println(e.toString() + " " + testParser);
                            setValuestringaddress(testParser);
                            String AddressUpper = removeBlock(getQueryAddress());
                            System.out.println(AddressUpper);

                            if (testParser.contains(AddressUpper)) {
                                System.out.println("Address matched");
                                setValuestringaddress(testParser);
                                foundAddress = true;
                            }

                        }
                    }

//                   // if (foundaddress) {
                    if (parser.getString().equals("x")) {
                        System.out.println("Found KEY_NAME x lng");
                        e = parser.next();
                        if (e == Event.VALUE_NUMBER) {
                            System.out.println(e.toString() + " " + parser.getString());

                            setLng(parser.getString());

                        }
                    }

                    if (parser.getString().equals("y")) {
                        System.out.println("Found KEY_NAME y lat");
                        e = parser.next();
                        if (e == Event.VALUE_NUMBER) {
                            System.out.println(e.toString() + " " + parser.getString());

                            setLat(parser.getString());

                        }
                    }

                    if (parser.getString().equals("score")) {
                        System.out.println("Found KEY_NAME score");
                        e = parser.next();
                        if (e == Event.VALUE_NUMBER) {
                            int iScore = parser.getInt();
                            System.out.println(e.toString() + " " + parser.getString() + " " + iScore);
                            if (iScore > 95 || iScore <= 100) //if(parser.getString().equals("100"))
                            {
                                setScore(parser.getString());
                                score = true;
                                foundAddress = true;
                            }
                        }
                    }
                } // if
                System.out.println("Row Count: " + i++);
                e = parser.next();

            } // while

        } catch (NullPointerException npe) {
            System.out.println(npe.toString());

        } catch (MalformedURLException mue) {
            System.out.println(mue.toString());
            //System.exit(-1);
        } catch (IOException ioe) {
            System.out.println(ioe.toString());
            //System.exit(-2);
        }

        return foundAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.queryAddress = streetAddress;
        this.streetAddress = formatAddress(streetAddress);
    }

    public String formatAddress(String address) {
        //System.out.println("Before " + address);
        address = removeBlock(address);
        //System.out.println("After " + address);
        String[] strAddress = new String[10];
        String formattedAddress = "";
        strAddress = address.split(" ");
        int length = strAddress.length;
        for (int i = 0; i < length - 1; i++) {
            if (strAddress[i].equals("&")) {
                strAddress[i] = "%26";
            }
            formattedAddress += strAddress[i] + "+";
        }
        formattedAddress += strAddress[length - 1];
        formattedAddress = formattedAddress.trim();
        //System.out.println(formattedAddress);
        return formattedAddress;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getQueryAddress() {
        return queryAddress;
    }

    public void setQueryAddress(String queryAddress) {
        this.queryAddress = queryAddress;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        //System.out.println("GeoCodeXMLAddress setNeighborhood TOP " + neighborhood);
        //System.out.println("Length " + neighborhood.length());

        if (neighborhood.contains("Golden Triangle")) {
            // System.out.println("Downtown Changed");
            neighborhood = "Downtown";
        }
        if (neighborhood.contains(" ")) {
            //System.out.println("Has spaces");
            neighborhood = neighborhood.replaceAll(" ", "+");
        }

        this.neighborhood = neighborhood;
        // System.out.println("GeoCodeXMLAddress setNeighborhood " + this.neighborhood);
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public String removeBlock(String address) {
        if (address.contains("block")) {
            address = address.replace("block ", "");
        }
        return address.toUpperCase();
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void printResults() {
        System.out.println("Query Address: " + getQueryAddress());
        parseZipcode(getValuestringaddress());
        System.out.println("Zip Code: " + getZipcode());
        System.out.println("Score: " + getScore());
        System.out.println("Lat: " + getLat());
        System.out.println("Lng: " + getLng());

    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void parseZipcode(String address) {
        //System.out.println("Address: "+ address);
        String zipcode = "TBD";
        String[] addressparts = new String[15];
        addressparts = address.split(",");
        int size = addressparts.length;
        //System.out.println("Size: "+ size);
        if (!addressparts[size - 1].contains("PA")) {
            zipcode = addressparts[size - 1];
        }
        //zipcode = addressparts[size - 1];
        //System.out.println(zipcode.length());
        //System.out.println(zipcode.trim().length());
        setZipcode(zipcode.trim());

    }

    public String getValuestringaddress() {
        return valuestringaddress;
    }

    public void setValuestringaddress(String valuestringaddress) {
        this.valuestringaddress = valuestringaddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUrladdress() {
        return urladdress;
    }

    public void setUrladdress(String urladdress) {
        this.urladdress = urladdress;
    }

}
