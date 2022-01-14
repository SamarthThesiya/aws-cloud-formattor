package services.aws;

import aws_models.resources.InternetGateway;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AttachInternetGatewayRequest;
import software.amazon.awssdk.services.ec2.model.CreateInternetGatewayRequest;
import software.amazon.awssdk.services.ec2.model.CreateInternetGatewayResponse;
import software.amazon.awssdk.services.ec2.model.ResourceType;

public class InternetGatewayService extends BaseAwsService {

    InternetGateway internetGateway;

    public InternetGatewayService(InternetGateway internetGateway) {
        super(internetGateway);

        this.internetGateway = internetGateway;
    }

    @Override
    protected String create() {

        Ec2Client ec2Client = getEc2Client();

        CreateInternetGatewayRequest createInternetGatewayRequest = CreateInternetGatewayRequest.builder()
                .tagSpecifications(getNameTag(internetGateway.name, ResourceType.INTERNET_GATEWAY))
                .build();

        CreateInternetGatewayResponse createInternetGatewayResponse = ec2Client.createInternetGateway(createInternetGatewayRequest);

        AttachInternetGatewayRequest attachInternetGatewayRequest = AttachInternetGatewayRequest.builder()
                .internetGatewayId(createInternetGatewayResponse.internetGateway().internetGatewayId())
                .vpcId(internetGateway.getVpc().getId())
                .build();

        ec2Client.attachInternetGateway(attachInternetGatewayRequest);

        return createInternetGatewayResponse.internetGateway().internetGatewayId();
    }
}
