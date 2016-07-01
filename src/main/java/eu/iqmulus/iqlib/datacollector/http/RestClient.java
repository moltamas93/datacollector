package eu.iqmulus.iqlib.datacollector.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;

import eu.iqmulus.iqlib.datacollector.jsonprocessing.JsonProcessor;


public class RestClient {

	private final static Logger LOG = Logger.getLogger(RestClient.class);
	private HttpClient client;
	private String url;
	
	public RestClient() {
		client = HttpClientBuilder.create().build();
	}
	
	public String postAndGetResponseAsString(String requestPath, String bodyAsString) {
		String url = this.url + requestPath ;
		HttpPost httpPost = new HttpPost(url);
		
		StringEntity params = new StringEntity(bodyAsString, "UTF-8");
	    httpPost.setEntity(params);
	    httpPost.setHeader("Accept", "application/json");
	    httpPost.setHeader("Content-type", "application/json");
	    HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpPost);
			System.out.println("Response Code : " + httpResponse.getStatusLine().getStatusCode());
		} catch(ClientProtocolException ex) {
			LOG.error(ex);
		} catch(IOException ex) {
			LOG.error(ex);
		}
		return getResponseBodyAsString(httpResponse);
	}
	
	public String get(String requestPath) {
		String url = this.url + requestPath;
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = null;
		try {
			 httpResponse = client.execute(httpGet);
		} catch(ClientProtocolException ex) {
			LOG.error(ex);
		} catch(IOException ex) {
			LOG.error(ex);
		}
		return getResponseBodyAsString(httpResponse);
	}
	
	private String getResponseBodyAsString(HttpResponse fromHttpResponse) {
		StringBuffer response = null;
		try {
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(fromHttpResponse.getEntity().getContent()));
			String inputLine;
			 response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		} catch(IOException ex) {
			LOG.error(ex);
		}
		return response.toString();
	}

	public void initHttpClientWithConfigFile(File configFile) {
		JsonProcessor jsonProcessor = new JsonProcessor();
		JsonNode configJsonNode = jsonProcessor.jsonFileToJsonNode(configFile);
		this.url = "http://" + configJsonNode.get("host").asText() + ":" + configJsonNode.get("port").asInt();
		System.out.println(url);
		HttpGet request = new HttpGet(url);
		try {
			HttpResponse response = client.execute(request);
		} catch (UnknownHostException ex) {
			LOG.error("The host is unkonwn: " + configJsonNode.get("host").asText() ,ex);
			System.exit(0);
		} catch (ClientProtocolException ex) {
			LOG.error(ex);
			System.exit(0);
		} catch (IOException ex) {
			LOG.error(ex);
			System.exit(0);
		}
	}
	
}
