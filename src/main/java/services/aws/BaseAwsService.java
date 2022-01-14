package services.aws;


import aws_models.resources.BaseAwsResource;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.ResourceType;
import software.amazon.awssdk.services.ec2.model.Tag;
import software.amazon.awssdk.services.ec2.model.TagSpecification;

public abstract class BaseAwsService implements AwsServiceInterface{

    protected final Region region = Region.US_WEST_2;
    protected BaseAwsResource resource;

    public BaseAwsService(BaseAwsResource resource) {
        this.resource = resource;
    }

    protected abstract String create();

    protected Ec2Client getEc2Client() {
        return Ec2Client.builder().region(region).build();
    }

    @Override
    public void execute() {
        System.out.println("Going to create: " + resource.getClass().getSimpleName());
        String resourceId = create();

        if (resourceId == null) {
            throw new RuntimeException("Resource Id is not returned");
        }

        System.out.println(resource.getClass().getSimpleName() + " created: id = " + resourceId);

        resource.setId(resourceId);
    }

    protected TagSpecification getNameTag(String name, ResourceType resourceType) {

        Tag nameTag = Tag.builder().key("Name").value(name).build();

        return TagSpecification.builder()
                .tags(nameTag)
                .resourceType(resourceType)
                .build();
    }
}
