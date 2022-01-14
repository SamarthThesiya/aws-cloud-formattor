package services.aws;

import aws_models.resources.RouteTable;
import aws_models.resources.Subnet;
import aws_models.resources.VPC;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.CreateVpcRequest;
import software.amazon.awssdk.services.ec2.model.CreateVpcResponse;
import software.amazon.awssdk.services.ec2.model.ResourceType;

public class VpcService extends BaseAwsService {

    private final VPC vpc;

    public VpcService(VPC vpc) {
        super(vpc);
        this.vpc = vpc;
    }

    @Override
    protected String create() {
        Ec2Client ec2Client = getEc2Client();

        CreateVpcRequest createVpcRequest = CreateVpcRequest.builder()
                .cidrBlock(vpc.getIpRange().toString())
                .tagSpecifications(getNameTag(vpc.name, ResourceType.VPC))
                .build();

        CreateVpcResponse createVpcResponse = ec2Client.createVpc(createVpcRequest);

        return createVpcResponse.vpc().vpcId();
    }

    @Override
    public void execute() {
        super.execute();

        for (Subnet subnet : vpc.getSubnets()) {
            subnet.getService().execute();
        }

        for (RouteTable routeTable : vpc.getRouteTables()) {
            routeTable.getService().execute();
        }

    }
}
