package services.aws;

import aws_models.resources.BaseAwsResource;
import aws_models.resources.NatGateway;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

public class NatGatewayService extends BaseAwsService{

    NatGateway natGateway;

    public NatGatewayService(NatGateway natGateway) {
        super(natGateway);

        this.natGateway = natGateway;
    }

    @Override
    protected String create() {

        Ec2Client ec2Client = getEc2Client();

        CreateNatGatewayRequest.Builder builder = CreateNatGatewayRequest.builder()
                .subnetId(natGateway.getSubnet().getId())
                .tagSpecifications(getNameTag(natGateway.name, ResourceType.NATGATEWAY));

        if (natGateway.isPublic()) {
            AllocateAddressResponse allocateAddressResponse = ec2Client.allocateAddress();

            builder.allocationId(allocateAddressResponse.allocationId());
        }

        CreateNatGatewayResponse createNatGatewayResponse = ec2Client.createNatGateway(builder.build());

        return createNatGatewayResponse.natGateway().natGatewayId();
    }
}
