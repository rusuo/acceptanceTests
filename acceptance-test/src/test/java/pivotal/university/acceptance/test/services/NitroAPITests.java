package pivotal.university.acceptance.test.services;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pivotal.university.acceptance.test.cucumber.UserList;
import pivotal.university.acceptance.test.testData.HttpClientSession;

import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class NitroAPITests {

    private HttpClientSession session;

    private String nitroBunchball = "https://main.nitro.bunchball.net/nitro/xml";
    private String apiKey = "ba45333a3ce641a99dea3552aba5fe0d" ;
    private String method = "user.login";
    private String user = "oana.rusu";
    private UserList userList;
    private String tutorialActionPath = "/Nitro/challenges/Challenge/rules/Rule";
    private String quizActionPath = "/Nitro/challenges/Challenge/rules/Rule[2]";
    private String missionPath = "/Nitro/challenges/Challenge";

    public NitroAPITests (HttpClientSession session, UserList userList) {
        this.session = session;
        this.userList = userList;
    }

    public void getSessionKeyRequest() throws UnsupportedEncodingException {
        HttpPost newSessionKeyRequest = new HttpPost(nitroBunchball);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("method", method));
        urlParameters.add(new BasicNameValuePair("apiKey", apiKey));
        urlParameters.add(new BasicNameValuePair("userId", user));

        newSessionKeyRequest.setEntity(new UrlEncodedFormEntity(urlParameters));

        session.execute(newSessionKeyRequest);
    }

    public void getMissionStatus() throws XPathExpressionException, UnsupportedEncodingException {
        HttpPost newMissionStatusRequest = new HttpPost(nitroBunchball);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("method", "user.getChallengeProgress"));
        urlParameters.add(new BasicNameValuePair("userId", userList.getUser(0).getUsername()));
        urlParameters.add(new BasicNameValuePair("showOnlyTrophies", "false"));
        urlParameters.add(new BasicNameValuePair("challengeName", "CHALLENGE_QUIZ_001"));

        urlParameters.add(new BasicNameValuePair("sessionKey", getXMlValue(session.getLastResult(), "/Nitro/Login/sessionKey")));

        newMissionStatusRequest.setEntity(new UrlEncodedFormEntity(urlParameters));

        session.execute(newMissionStatusRequest);
    }

    public String getXMlValue(String xml, String xpathString) throws XPathExpressionException {
        String xpath = xpathString;
        XPath xPath = XPathFactory.newInstance().newXPath();
        return xPath.evaluate(xpath, new InputSource(new StringReader(xml)));

    }

    public String getMissionResult(String xml, String action) throws XPathExpressionException, IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = documentBuilder.parse(new InputSource(new StringReader(xml)));

        XPath xPath = XPathFactory.newInstance().newXPath();

        XPathExpression xPathExpression;

        if (action.equals("TUTORIAL_COMPLETE_EXAMPLE")) {
            xPathExpression = xPath.compile(tutorialActionPath);
        }else {
            xPathExpression = xPath.compile(quizActionPath);
        }
        NodeList nodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);

        return nodeList.item(0).getAttributes().getNamedItem("completed").getNodeValue();
    }

    public boolean isMissionCompleted(String xml) throws ParserConfigurationException, XPathExpressionException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = factory.newDocumentBuilder();
        org.w3c.dom.Document doc = documentBuilder.parse(new InputSource(new StringReader(xml)));

        XPath xPath = XPathFactory.newInstance().newXPath();

        XPathExpression xPathExpression = xPath.compile(missionPath);

        NodeList nodeList = (NodeList) xPathExpression.evaluate(doc, XPathConstants.NODESET);

        return (nodeList.item(0).getAttributes().getNamedItem("completionCount").getNodeValue().equals("1"));
    }


}
