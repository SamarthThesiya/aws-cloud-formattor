package aws_models.resources;

import aws_models.helper_resources.IPv4;
import aws_models.helper_resources.Route;
import services.aws.AwsServiceInterface;
import services.aws.BaseAwsService;
import services.aws.RouteTableService;

import java.util.ArrayList;
import java.util.List;

public class RouteTable extends BaseAwsResource {

    private List<Route> routes;
    private List<Subnet> subnets;
    private VPC vpc;

    public RouteTable(String name, VPC vpc, Subnet... subnets) {
        super(name);

        this.subnets = new ArrayList<>();
        this.routes = new ArrayList<>();
        this.vpc = vpc;

        this.vpc.addRouteTable(this);

        for (Subnet subnet: subnets) {
            subnet.setRouteTable(this);
            this.subnets.add(subnet);
        }
    }

    public void addRoute(IPv4 iPv4Range, RouteTarget routeTarget) {
        Route route = new Route(iPv4Range, routeTarget);
        this.routes.add(route);
    }

    public List<Route> getRoutes() {
        return this.routes;
    }

    public List<Subnet> getSubnets() {
        return this.subnets;
    }

    public VPC getVpc() {
        return vpc;
    }

    @Override
    public AwsServiceInterface getService() {
        return new RouteTableService(this);
    }
}
