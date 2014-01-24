package pivotal.university.acceptance.test.services;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import pivotal.university.acceptance.test.config.Settings;
import pivotal.university.acceptance.test.testData.HttpClientSession;
import pivotal.university.acceptance.test.testData.PivotalHDUser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PivotalHDCommunicationService {
    private HttpClientSession session;
    private String createUserEndPoint = "/hawq/webservice/createUser/";
    private String submitSqlCommandEndPoint = "/hawq/webservice/executeSql/";

    private String retrieveTutorial = "/hawq/webservice/tutorial/hawq";

    public PivotalHDCommunicationService(HttpClientSession session) {
        this.session = session;
    }

    public void requestNewAccount() {
        HttpGet newAccountRequest = new HttpGet(Settings.applicationUrl  + createUserEndPoint);

        session.execute(newAccountRequest);
    }

    public void submitSqlCommand(PivotalHDUser user, String command) throws Throwable {
        HttpPost submitCommand = new HttpPost(Settings.applicationUrl + submitSqlCommandEndPoint) ;

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("username", user.getUsername()));
        urlParameters.add(new BasicNameValuePair("password", user.getPassword()));
        urlParameters.add(new BasicNameValuePair("value", command));

        submitCommand.setEntity(new UrlEncodedFormEntity(urlParameters));

        session.execute(submitCommand);
    }

    public void requestNewTutorial(){
        HttpGet tutorialRequest = new HttpGet(Settings.applicationUrl + retrieveTutorial);

        session.execute(tutorialRequest);
    }
}
