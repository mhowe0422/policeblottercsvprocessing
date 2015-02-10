/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.coa.pittsburgh.utilities.writers.text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Mark
 */
public class WriteFailedURLAddress {
    public String data;
    
 public static void main( String[] args )
    {	
        WriteFailedURLAddress wfua = new WriteFailedURLAddress();
        wfua.setData("http://geodata.alleghenycounty.us/arcgis/rest/services/Geocoders/EAMS_Composite_Loc/GeocodeServer/findAddressCandidates?Street=1102+SHEFFIELD+ST&City=Pittsburgh&State=PA&ZIP=&SingleLine=&outFields=&outSR=4326&searchExtent=&f=pjson");
        wfua.writeURL();
    }
 public void writeURL()
 {
    	try{
    		
 
    		File file = new File("C:\\pgh-blotter-parser-master\\urladdress\\failedurl.txt");
 
    		//if file doesnt exists, then create it
    		if(!file.exists()){
    			file.createNewFile();
    		}
 
    		//true = append file
                
    		FileWriter fileWritter = new FileWriter(file,true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
    	        bufferWritter.write(getData());
                bufferWritter.write("\n");
                bufferWritter.flush();
    	        bufferWritter.close();
 
	        System.out.println("Done");
 
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    } 

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
 
}
