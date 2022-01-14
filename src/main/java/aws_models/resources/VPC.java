package aws_models.resources;

import aws_models.helper_resources.IPv4Range;
import services.aws.AwsServiceInterface;
import services.aws.BaseAwsService;
import services.aws.VpcService;

import java.util.ArrayList;
import java.util.List;

public class VPC extends BaseAwsResource {

    private final List<Subnet> subnets;
    private final IPv4Range ipRange;
    private InternetGateway internetGateway;
    private List<RouteTable> routeTables;

    public VPC(String name, IPv4Range ipRange) {
        super(name);
        this.ipRange = ipRange;

        subnets = new ArrayList<>();
        routeTables = new ArrayList<>();
    }

    void addSubnet(Subnet subnet) {
        this.subnets.add(subnet);
    }
    void addRouteTable(RouteTable routeTable) {this.routeTables.add(routeTable);}

    public List<RouteTable> getRouteTables() {
        return routeTables;
    }

    public void setInternetGateway(InternetGateway internetGateway) {
        this.internetGateway = internetGateway;
    }

    public List<Subnet> getSubnets() {
        return new ArrayList<>(subnets);
    }

    public IPv4Range getIpRange() {
        return ipRange;
    }

    @Override
    public AwsServiceInterface getService() {
        return new VpcService(this);
    }
}
