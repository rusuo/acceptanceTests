package pivotal.university.acceptance.test.testData;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class HttpClientSession{
    protected HttpClient client;
    protected int lastStatusCode;
    protected String lastResult;
    protected String lastRequest;

    public int getLastStatusCode() {
        return lastStatusCode;
    }

    public String getLastRequest() {
        return lastRequest;
    }

    public String getLastResult() {
        return lastResult;
    }

    public HttpClientSession() {
        this.client = new DefaultHttpClient();
    }

    public void execute(HttpUriRequest request) {
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            lastRequest = request.toString();
            HttpResponse response = client.execute(request);
            lastStatusCode = getStatusCode(response);
            lastResult = getResult(response);

            client.getConnectionManager().shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getStatusCode(HttpResponse response) {
        return response.getStatusLine().getStatusCode();
    }

    private String getResult(HttpResponse response) {
        String responseString = "";

        try {
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseString;
    }
}
