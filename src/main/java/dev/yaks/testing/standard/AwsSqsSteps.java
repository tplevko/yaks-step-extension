package dev.yaks.testing.standard;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

import static com.consol.citrus.actions.EchoAction.Builder.echo;

import java.util.HashMap;
import java.util.Map;

public class AwsSqsSteps {

    @CitrusResource
    private TestCaseRunner runner;

    private Map<String, Object> headers = new HashMap<>();
    private String body;

    private String selector = "";

    private SqsClient sqsClient;

    public void initSqsClient(String accessKey, String secretKey, String region) {

        sqsClient = SqsClient.builder().region(Region.of(region))
            .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey)).build();
    }

    @Given("^Set SQS client access key ([a-z0-9-]+)$")
    public void setSqsClientAccessKey() {
        runner.given(echo("My steps are loaded"));
    }

    @Given("^Set SQS client secret key ([a-z0-9-]+)$")
    public void setSqsClientSecretKey(String secretKey) {
        runner.given(echo("My secret key is: " + secretKey));
    }

    @Given("^Set SQS client region ([a-z0-9-]+)$")
    public void setSqsClientRegion() {
        runner.given(echo("My steps are loaded"));
    }

    @Given("^Initialize SQS client ([a-z0-9-]+)$")
    public void printEvnVariable() {
        runner.given(echo("My steps are loaded"));
    }

    @Given("^(?:JMS|jms) message body: (.+)$")
    @Then("^(?:expect|verify) (?:JMS|jms) message body: (.+)$")
    public void setMessageBody(String body) {
        this.body = body;
    }

    @When("^send (?:SQS|sqs) message to destination (.+)$")
    public void sendMessage(String destination) {
//        setDestination(destination);
//        sendMessage();
    }

    @When("^send (?:SQS|sqs) message$")
    public void sendMessage() {
//        runner.run(send().endpoint(jmsEndpoint)
//            .payload(body)
//            .headers(headers));
//
//        body = null;
//        headers.clear();
    }

    @Given("^(?:SQS|sqs) destination: ([^\\s]+)$")
    public void setDestination(String destination) {
//        jmsEndpoint.getEndpointConfiguration().setDestinationName(destination);
    }
}
