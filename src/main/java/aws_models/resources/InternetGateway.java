package aws_models.resources;

import services.aws.AwsServiceInterface;
import services.aws.BaseAwsService;
import services.aws.InternetGatewayService;

public class InternetGateway extends RouteTarget {

    private final VPC vpc;

    public InternetGateway(String name, VPC vpc) {
        super(name);
        this.vpc = vpc;
    }

    public VPC getVpc() {
        return vpc;
    }

    @Override
    public AwsServiceInterface getService() {
        return new InternetGatewayService(this);
    }
}
