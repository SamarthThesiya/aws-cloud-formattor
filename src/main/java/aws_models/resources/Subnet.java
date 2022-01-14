package aws_models.resources;

import aws_models.helper_resources.IPv4Range;
import services.aws.AwsServiceInterface;
import services.aws.BaseAwsService;
import services.aws.SubnetService;

public class Subnet extends BaseAwsResource {

    private final IPv4Range iPv4Range;
    private RouteTable routeTable;
    private final VPC vpc;
    public String availabilityZone;

    public Subnet(String name, IPv4Range iPv4Range, VPC vpc) {
        super(name);
        this.iPv4Range = iPv4Range;

        vpc.addSubnet(this);

        this.vpc = vpc;

    }

    void setRouteTable(RouteTable routeTable) {
        this.routeTable = routeTable;
    }

    public IPv4Range getIPv4Range() {
        return iPv4Range;
    }

    public VPC getVpc() {
        return vpc;
    }

    public RouteTable getRouteTable() {
        return routeTable;
    }

    @Override
    public AwsServiceInterface getService() {
        return new SubnetService(this);
    }
}
