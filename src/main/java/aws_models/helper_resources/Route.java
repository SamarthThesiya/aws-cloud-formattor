package aws_models.helper_resources;

import aws_models.resources.RouteTarget;

public class Route {

    private IPv4 routeDestination;
    private RouteTarget routeTarget;

    public Route(IPv4 routeDestination, RouteTarget routeTarget) {
        this.routeDestination = routeDestination;
        this.routeTarget = routeTarget;
    }

    public IPv4 getRouteDestination() {
        return routeDestination;
    }

    public RouteTarget getRouteTarget() {
        return routeTarget;
    }
}
