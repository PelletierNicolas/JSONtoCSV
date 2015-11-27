package internshipTest;

import java.io.*;
import java.net.*;

import org.json.*;

public class InternshipTest {
	
	public static void main (String[] args){
		
		 String targetURL="http://api.goeuro.com/api/v2/position/suggest/en/"+args[0];
		 HttpURLConnection connection = null; 
		 File res=new File(args[0]+".csv");
		 try {
			FileWriter resW=new FileWriter(res);

			URL url = new URL(targetURL);
			connection = (HttpURLConnection)url.openConnection();
			
			InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder();
		    String line;
		    while((line = rd.readLine()) != null) {
		        response.append(line);
		        response.append('\r');
		    }
		    rd.close();
			
		    JSONArray json=new JSONArray(response.toString());
		    String temp;
		    resW.write("_id,name,type,latitude,longitude\r");
		    for(int i=0;i<json.length();i++){
		    		temp=json.getJSONObject(i).getLong("_id")+","
		    				+json.getJSONObject(i).getString("name")+","
		    				+json.getJSONObject(i).getString("type")+","
		    				+json.getJSONObject(i).getJSONObject("geo_position").getDouble("latitude")+","
		    				+json.getJSONObject(i).getJSONObject("geo_position").getDouble("longitude")+"\r";
		    		
		    		resW.write(temp);
		    }
		    
		    resW.close();
			} catch (Exception e) {
			    e.printStackTrace();
			} finally {
			    if(connection != null) {
			    connection.disconnect(); 
			}
			    			    
		 }
		 			
	}
}
