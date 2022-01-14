package aws_models.resources;

import services.aws.AwsServiceInterface;

public abstract class BaseAwsResource {

    public String name;

    private String id;

    public BaseAwsResource(String name) {
        this.name = name;
    }

    public abstract AwsServiceInterface getService();

    public String getId() {

        if (id == null) {
            throw new RuntimeException("id is not assigned yet");
        }

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
