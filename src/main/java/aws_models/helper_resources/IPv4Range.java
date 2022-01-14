package aws_models.helper_resources;

public class IPv4Range extends IPv4{

    private final int cidr;

    public IPv4Range(int firstBit, int secondBit, int thirdBit, int fourthBit, int cidr) {
        super(firstBit, secondBit, thirdBit, fourthBit);

        this.cidr = cidr;
    }

    @Override
    public String toString() {
        return super.toString() + "/" + cidr;
    }
}
