package aws_models.helper_resources;

public class IPv4 {
    private final int firstBit, secondBit, thirdBit, fourthBit;

    public IPv4(int firstBit, int secondBit, int thirdBit, int fourthBit) {
        this.firstBit = firstBit;
        this.secondBit = secondBit;
        this.thirdBit = thirdBit;
        this.fourthBit = fourthBit;
    }

    @Override
    public String toString() {
        return firstBit + "." + secondBit + "." + thirdBit + "." + fourthBit;
    }
}
