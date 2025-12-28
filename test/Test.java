import src.Phone;
import src.SimCard;
import src.Network;

public class Test {
    public static void main(String[] args) {
        // Setup Network
        Network network = new Network();
        Phone.setNetwork(network);

        network.addAntenna(0, 0, 10, 8);
        network.addAntenna(15, 0, 10, 10);
        System.out.println();

        // Create SIM Cards and Phones
        SimCard sim1 = new SimCard(10, "0555000011");
        SimCard sim2 = new SimCard(5, "0555000022");
        SimCard sim3 = new SimCard(40, "0555000033");  

        Phone phone1 = new Phone(1, 1, sim1, 63);
        Phone phone2 = new Phone(2, 2, sim2, 98);
        Phone phone3 = new Phone(16, 0, sim3, 76);

        // Test 1: Successful Call
        phone1.call(phone2);
        System.out.println();

        // Test 2: Call When Already in Call
        phone3.call(phone2);
        System.out.println();

        // Test 3: Movement Out of Coverage During Call
        phone1.move(43, 7);
        System.out.println();

        // Test 4: Insufficient Credit
        SimCard sim4 = new SimCard(3, "0555000044");
        Phone phone4 = new Phone(16, 0, sim4, 40);
        //Phone4 (credit: 3) attempting to call Phone1...
        phone4.call(phone1);
        System.out.println();

        // Test 5: Low Battery
        SimCard sim5 = new SimCard(10, "0555000055");
        Phone phone5 = new Phone(1, 1, sim5, 3);
        //Phone5 (battery: 3) attempting to call Phone2...
        phone5.call(phone2);
        System.out.println();

        // Test 6: Call Between Phones Out of Coverage
        //Phone1 (out of coverage) attempting to call Phone3...
        phone1.call(phone3);
        System.out.println();

        // Test 7: Movement Back Into Coverage
        //Phone1 moving to (20, 0) - back in coverage...
        phone1.move(20, 0);
        System.out.println();

        // Test 8: Successful Call After Movement
        //Phone1 calling Phone3...
        phone1.call(phone3);
        System.out.println();

        // Test 9: Ending Call
        //Phone1 ending call...
        phone1.endCall();
        System.out.println();

        // Test 10: Inactive SIM Card
        sim2.setActive(false);
        //Phone2 SIM deactivated. Phone3 attempting to call Phone2...
        phone3.call(phone2);
        sim2.setActive(true);  // Reactivate for potential future tests
        System.out.println();

        // Test 11: Add Credit and Retry
        //Adding 10 credit to Phone4's SIM...
        sim4.addCredit(10);
        System.out.println("Phone4 (credit: " + sim4.getCredit() + ") calling Phone2...");
        phone4.call(phone2);
        phone4.endCall();
        System.out.println();

        // Test 12: Capacity Test
        //Creating multiple phones to test antenna capacity...
        for (int i = 6; i <= 15; i++) {
            SimCard sim = new SimCard(50, "05550000" + i);
            Phone phone = new Phone(1 + (i * 0.1), 1, sim, 100);
            if (i <= 13) {
                System.out.println("Phone " + i + " attempting call...");
                phone.call(phone2);  // Phone2 won't answer (busy), but will test capacity
            }
        }
    }
}