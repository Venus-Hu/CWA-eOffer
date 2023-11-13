package gov.gsa.sst.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class HttpURLConnectionSoap {   
	 
	public String createHttpURLConnectionAndMakeRequest(String passSoapMessage, String host, String endPointAddress, boolean useHttps) throws MalformedURLException, FileNotFoundException, IOException{

		String USER_AGENT = "Mozilla/5.0";
		String result = "";
			
        String URLString = host + endPointAddress;
        URL obj = new URL(URLString);
           
        if(useHttps)
	    {
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestProperty("SOAPAction", "");
			con.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(passSoapMessage);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				result = response.toString();
			}

	    }else {
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        con.setRequestProperty("SOAPAction", "");
            con.setRequestProperty("Content-Type","text/xml;charset=UTF-8");
            con.setRequestProperty("Connection","Keep-Alive");
	        con.setRequestMethod("POST");
	        con.setUseCaches(false);
	        con.setRequestProperty("User-Agent", USER_AGENT); 
	        con.setDoOutput(true);
	        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(passSoapMessage);
			wr.flush();
			wr.close();
	        int responseCode = con.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) { //success
	            BufferedReader in = new BufferedReader(new InputStreamReader(
	            con.getInputStream()));
	            String inputLine;
	            StringBuffer response = new StringBuffer();
	 
	            while ((inputLine = in.readLine()) != null) {
	                response.append(inputLine);
	            }
	            in.close();
	            result = response.toString();
	        }
	    }
		return result;
    }
}