package services.aws;

import aws_models.helper_resources.Route;
import aws_models.resources.InternetGateway;
import aws_models.resources.NatGateway;
import aws_models.resources.RouteTable;
import aws_models.resources.RouteTarget;
import aws_models.resources.Subnet;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.*;

public class RouteTableService extends BaseAwsService {

    private final RouteTable routeTable;

    public RouteTableService(RouteTable routeTable) {
        super(routeTable);

        this.routeTable = routeTable;
    }

    @Override
    protected String create() {

        Ec2Client ec2Client = getEc2Client();

        CreateRouteTableRequest createRouteTableRequest = CreateRouteTableRequest.builder()
                .vpcId(routeTable.getVpc().getId())
                .tagSpecifications(getNameTag(routeTable.name, ResourceType.ROUTE_TABLE))
                .build();

        CreateRouteTableResponse createRouteTableResponse = ec2Client.createRouteTable(createRouteTableRequest);

        for (Route route : routeTable.getRoutes()) {

            route.getRouteTarget().getService().execute();

            CreateRouteRequest.Builder builder = CreateRouteRequest.builder()
                    .routeTableId(createRouteTableResponse.routeTable().routeTableId())
                    .destinationCidrBlock(route.getRouteDestination().toString());

            setTarget(builder, route.getRouteTarget(), route.getRouteTarget().getId());

            ec2Client.createRoute(builder.build());
        }

        for (Subnet subnet : routeTable.getSubnets()) {

            AssociateRouteTableRequest associateRouteTableRequest = AssociateRouteTableRequest.builder()
                    .subnetId(subnet.getId())
                    .routeTableId(createRouteTableResponse.routeTable().routeTableId())
                    .build();

            ec2Client.associateRouteTable(associateRouteTableRequest);
        }

        return createRouteTableResponse.routeTable().routeTableId();
    }

    private void setTarget(CreateRouteRequest.Builder builder, RouteTarget routeTarget, String targetId) {
        if (routeTarget instanceof InternetGateway) {
            builder.gatewayId(targetId);
        } else if (routeTarget instanceof NatGateway) {
            builder.natGatewayId(targetId);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Target is not implemented");
        }
    }
}
