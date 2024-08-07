package lab4.enums;

public enum Transport {
    CAR ("машина"),
    BUS ("автобус"),
    HELICOPTER ("вертолет"),
    PLANE ("самолет"),
    TRAIN ("поезд"),
    BOAT ("катер"),
    SUBMARINE ("подводная лодка");

    private String transport;

    private Transport(String transport) {
        this.transport = transport;
    }
    public String getTransport() {
        return transport;
    }

    public static Transport getRandomTransport() {
        Transport[] transports = Transport.values();
        int randomIndex = (int) (Math.random() * transports.length);
        return transports[randomIndex];
    }
}