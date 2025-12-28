package src;

public class Phone {
    // Attributes
    private double x, y;
    private double battery;
    private boolean inCall;

    private SimCard sim;
    private Phone callPartner;
    private Antenna connectedAntenna;

    private static Network network;

    private static final double MIN_BATTERY = 5.0;
    private static final double CALL_COST = 4.0;

    // Constructor
    public Phone(double x , double  y, SimCard sim, double battery) {
        this.x = x;
        this.y = y;
        this.sim = sim; 
        this.sim.assignToPhone(this);
        this.battery = battery;
        this.inCall = false;
        this.callPartner = null;
        connectedAntenna = network.findNearestAntenna(x, y);
    }

    //Setter
    public static void setNetwork(Network net) {
        network = net;
    }

    // Method
    public boolean canMakeCall() {
        if (inCall) {
            System.out.println("Cannot make the call: You are already in a call.");
            return false;
        }
        if (battery < MIN_BATTERY) {
            System.out.println("Cannot make the call: Battery too low.");
            return false;
        }
        if (!sim.isActive()) {
            System.out.println("Cannot make the call: SIM card is inactive.");
            return false;
        }
        if (!sim.hasEnoughCredit(CALL_COST)) {
            System.out.println("Cannot make the call: Not enough credit.");
            return false;
        }
        return true;
    }


    public boolean canReceiveCall() {
        if (inCall) {
            System.out.println("Cannot receive the call: Already in another call.");
            return false;
        }
        if (battery < MIN_BATTERY) {
            System.out.println("Cannot receive the call: Battery too low.");
            return false;
        }
        return true;
    }

    public void call(Phone receiver) {
        if (!canMakeCall() || !receiver.canReceiveCall())
            return;

        Antenna myAntenna = network.findNearestAntenna(x, y);
        Antenna receiverAntenna = network.findNearestAntenna(receiver.x, receiver.y);

        if (myAntenna == null) {
            System.out.println("Cannot make the call: You don't have network.");
            return;
        }

        if (receiverAntenna == null) {
            System.out.println("Receiver not available.");
            return;
        }

        this.connectedAntenna = myAntenna;
        receiver.connectedAntenna = receiverAntenna;

        this.inCall = true;
        receiver.inCall = true;

        this.callPartner = receiver;
        receiver.callPartner = this;

        this.connectedAntenna.incrementActiveCalls();
        receiver.connectedAntenna.incrementActiveCalls();

        sim.deductCredit(CALL_COST);
        System.out.println("Call started between " + sim.getNumber() + " and " + receiver.sim.getNumber());
    }

    public void endCall() {
        if (!inCall) return;  
        Phone partner = this.callPartner;
        inCall = false;
        callPartner = null;
        if (connectedAntenna != null) {
            connectedAntenna.decrementActiveCalls();
        }
        if (partner != null && partner.inCall) {
            partner.inCall = false;
            partner.callPartner = null;

            if (partner.connectedAntenna != null) {
                partner.connectedAntenna.decrementActiveCalls();
            }
        }
        System.out.println("Call ended.");
    }


    public void move(double x, double y){
        this.x = x;
        this.y = y;

        connectedAntenna = network.findNearestAntenna(this.x, this.y);

        if (inCall && connectedAntenna == null){
            System.out.print("Network connection lost. ");
            endCall();
        }
    }
}