package Shoutbox;


import java.io.IOException;
import java.io.InputStream;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


public class DataCommunicator {

	private static final int TIMEOUT = 120000;
	
	
	public static InputStream sendGetDataToServer(String serverAddress) throws Exception{
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(serverAddress);
		HttpParams httpParams = httpclient.getParams();
		
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT);
		
		try{
			 HttpResponse response = httpclient.execute(httpGet);
			 HttpEntity entity = response.getEntity();
			 
		        if(entity == null)
		        	return null;
		        else{
		        	//System.out.println(entity.getContent().toString());
		        	return entity.getContent();}
		} catch (ClientProtocolException e) {
	    	System.out.println("ClientProtocolException in sendPostDataToServer");
	    	e.printStackTrace();
	    	throw e;
	    } catch (IOException e) {
	    	System.out.println("IOException in sendPostDataToServer");
	    	e.printStackTrace();
	    	throw e;
	    }catch(Exception e){
	    	System.out.println( "Exception in sendPostDataToServer");
	    	e.printStackTrace();
	    	throw e;
	    }

	}


}
