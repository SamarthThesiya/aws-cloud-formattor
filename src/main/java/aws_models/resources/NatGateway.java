package aws_models.resources;

import services.aws.AwsServiceInterface;
import services.aws.NatGatewayService;

public class NatGateway extends RouteTarget{

    private final Subnet subnet;
    private boolean isPublic = true;

    public NatGateway(String name, Subnet subnet) {
        super(name);

        this.subnet = subnet;
    }

    public NatGateway(String name, Subnet subnet, boolean isPublic) {
        super(name);

        this.subnet = subnet;
        this.isPublic = isPublic;
    }

    @Override
    public AwsServiceInterface getService() {
        return new NatGatewayService(this);
    }

    public boolean isPublic() {
        return isPublic;
    }

    public Subnet getSubnet() {
        return subnet;
    }
}
