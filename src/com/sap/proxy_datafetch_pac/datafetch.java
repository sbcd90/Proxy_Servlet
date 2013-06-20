package com.sap.proxy_datafetch_pac;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import org.apache.http.conn.ClientConnectionManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import sun.security.x509.*;
import java.security.cert.*;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.auth.AuthScope;
//add external jar files first via project name -> <right click> -> Build Path -> Configure Build Path -> add external jar files
//also for Apache tomcat add it to Properties -> Deployment Assembly -> Add -> <Add the necessary jar files>
public class datafetch {
	public String getApiData(String url){
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		URL url_pass;
		URI finalURI;
		String returning = "";
		try	{
			url_pass = new URL(url);
			try {
				finalURI = URIUtils.createURI(
						url_pass.getProtocol(),
						url_pass.getHost(),
						url_pass.getPort(),
						url_pass.getPath(),
						URLEncodedUtils.format(parameters,"UTF-8"),
						null);
				HttpGet get = new HttpGet(finalURI);
				DefaultHttpClient httpClient;
				try{
					httpClient = httpClientTrustingAllSSLCerts();
					Credentials credentials = new UsernamePasswordCredentials("<USERNAME>","<PASSWORD>");
					httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY,credentials);
					try{
						HttpResponse response = httpClient.execute(get);
//						returning = returning + "response: " + response.getStatusLine().toString() + "\nresponse content string: " + EntityUtils.toString(response.getEntity());
						returning = returning + EntityUtils.toString(response.getEntity());
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				catch(NoSuchAlgorithmException e4){
					e4.printStackTrace();
				} catch (KeyManagementException e5) {
					e5.printStackTrace();
				}
			}
			catch (URISyntaxException e2) {
				e2.printStackTrace();
			}
		}
		catch (MalformedURLException e1) {
	        e1.printStackTrace();
	    }
		
		return returning;
	}
	private DefaultHttpClient httpClientTrustingAllSSLCerts() throws NoSuchAlgorithmException, KeyManagementException{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, getTrustingManager(), new java.security.SecureRandom());
		SSLSocketFactory socketFactory = new SSLSocketFactory(sc);
		Scheme sch = new Scheme("https", 443, socketFactory);
		httpClient.getConnectionManager().getSchemeRegistry().register(sch);
		return httpClient;
	}
	private TrustManager[] getTrustingManager(){
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
                // Do nothing
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
                // Do nothing
            }
		} };
		return trustAllCerts;
	}
}
