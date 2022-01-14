package services.aws;

import aws_models.resources.Subnet;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.AssociateRouteTableRequest;
import software.amazon.awssdk.services.ec2.model.CreateSubnetRequest;
import software.amazon.awssdk.services.ec2.model.CreateSubnetResponse;
import software.amazon.awssdk.services.ec2.model.ResourceType;

public class SubnetService extends BaseAwsService {

    private final Subnet subnet;

    public SubnetService(Subnet subnet) {
        super(subnet);
        this.subnet = subnet;
    }


    @Override
    protected String create() {
        Ec2Client ec2Client = getEc2Client();

        CreateSubnetRequest.Builder builder = CreateSubnetRequest.builder()
                .tagSpecifications(getNameTag(subnet.name, ResourceType.SUBNET))
                .cidrBlock(subnet.getIPv4Range().toString())
                .vpcId(subnet.getVpc().getId());

        if (subnet.availabilityZone != null) {
            builder.availabilityZone(subnet.availabilityZone);
        }

        CreateSubnetRequest createSubnetRequest = builder.build();

        CreateSubnetResponse createSubnetResponse = ec2Client.createSubnet(createSubnetRequest);

        return createSubnetResponse.subnet().subnetId();
    }

}
