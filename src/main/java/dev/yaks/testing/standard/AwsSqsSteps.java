package dev.yaks.testing.standard;

import static com.consol.citrus.actions.EchoAction.Builder.echo;

import com.consol.citrus.Citrus;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusFramework;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.context.TestContext;

import javax.sql.DataSource;

import java.util.HashMap;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

public class AwsSqsSteps {

    @CitrusResource
    private TestCaseRunner runner;

    @CitrusResource
    private TestContext context;

    @CitrusFramework
    private Citrus citrus;

    private Map<String, Object> headers = new HashMap<>();
    private String body;

    private String selector = "";

    private SqsClient sqsClient;

    @Before
    public void before(Scenario scenario) {
        if (sqsClient == null && citrus.getCitrusContext().getReferenceResolver().resolveAll(DataSource.class).size() == 1L) {
            sqsClient = citrus.getCitrusContext().getReferenceResolver().resolve(SqsClient.class);
        }
    }

    @Given("^(?:SQS|sqs) connection$")
    public void setConnection(DataTable properties) {
        Map<String, String> connectionProps = properties.asMap(String.class, String.class);

        String accessKey = connectionProps.get("accessKey");
        String secretKey = connectionProps.get("secretKey");
        String region = connectionProps.get("region");

        this.sqsClient = SqsClient.builder().region(Region.of(region))
            .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey)).build();
    }

    @Given("Init SQS client with accessKey: {string} secretKey: {string} and region: {string}")
    public void initSqsClient(String accessKey, String secretKey, String region) {

        sqsClient = SqsClient.builder().region(Region.of(region))
            .credentialsProvider(() -> AwsBasicCredentials.create(accessKey, secretKey)).build();
    }

    @Given("Init SQS client")
    public void initSqsClient() {
        System.out.println("**************");
        for (String s : context.getVariables().keySet()) {
            System.out.println("variable: " + s + " : " + (String) context.getVariables().get(s));
        }
        System.out.println("**************");

        sqsClient = SqsClient.builder().region(Region.of((String) context.getVariables().get("aws-sqs.region")))
            .credentialsProvider(() -> AwsBasicCredentials
                .create((String) context.getVariables().get("aws-sqs.accessKey"), (String) context.getVariables().get("aws-sqs.secretKey"))).build();
    }

    @Given("Send message: {string} to SQS queue: {string}")
    public void sendSqsMessage(String message, String queueName) {

        sqsClient.sendMessage(SendMessageRequest.builder()
            .queueUrl(queueName)
            .messageBody(message)
            .delaySeconds(10)
            .build());
    }

    @Given("^Set SQS client access key ([a-z0-9-]+)$")
    public void setSqsClientAccessKey() {
        runner.given(echo("My steps are loaded"));
    }

    //    @Given("^Set SQS client secret key ([a-z0-9-]+)$")
    //    public void setSqsClientSecretKey(String secretKey) {
    //        runner.given(echo("My secret key is: " + secretKey));
    //    }

    @Given("Set SQS client secret key {string}")
    public void test(String secretKey) {
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

    //    @Given("^(?:JMS|jms) message body: (.+)$")
    //    @Then("^(?:expect|verify) (?:JMS|jms) message body: (.+)$")
    //    public void setMessageBody(String body) {
    //        this.body = body;
    //    }

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
    //
    //    @Given("^(?:SQS|sqs) connection factory$")
    //    public void setConnection(DataTable properties) throws ClassNotFoundException {
    //        List<List<String>> cells = properties.cells();
    //        Map<String, String> connectionSettings = new LinkedHashMap<>();
    //        cells.forEach(row -> connectionSettings.put(row.get(0), row.get(1)));
    //
    //        connectionFactory = ConnectionFactoryCreator.lookup(connectionSettings.get("type"))
    //            .create(connectionSettings);
    //
    //        citrus.getCitrusContext().getReferenceResolver().bind("connectionFactory", connectionFactory);
    //        jmsEndpoint.getEndpointConfiguration().setConnectionFactory(connectionFactory);
    //    }
}
