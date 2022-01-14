import aws_models.helper_resources.IPv4;
import aws_models.helper_resources.IPv4Range;
import aws_models.resources.*;
import services.aws.VpcService;

public class Main {
    public static void main(String[] args) {

        IPv4Range vpcRange = new IPv4Range(12, 0, 0,0, 16);

        IPv4Range publicSubnetRange = new IPv4Range(12, 0, 1, 0, 24);
        IPv4Range privateSubnetRange = new IPv4Range(12,0,2,0,24);

        VPC vpc = new VPC("my-vpc", vpcRange);

        Subnet publicSubnet = new Subnet("my-public-subnet", publicSubnetRange, vpc);
        Subnet privateSubnet = new Subnet("my-private-subnet", privateSubnetRange, vpc);

        InternetGateway internetGateway = new InternetGateway("my-ig", vpc);
        NatGateway natGateway = new NatGateway("my-ng", publicSubnet);

        RouteTable publicRouteTable = new RouteTable("public-rt", vpc, publicSubnet);
        publicRouteTable.addRoute(new IPv4Range(0,0,0,0,0), internetGateway);

        RouteTable privateRouteTable = new RouteTable("private-rt", vpc, privateSubnet);
        privateRouteTable.addRoute(new IPv4Range(0,0,0,0,0), natGateway);

        vpc.getService().execute();
    }
}
