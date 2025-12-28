package src;

public class SimCard {

    private final String number;
    private boolean active;
    private double credit;
    private Phone owner;

    public SimCard(double credit, String number) {
        this.number = number;
        this.credit = Math.max(0, credit);
        this.active = true;
        this.owner = null;
    }

    public void assignToPhone(Phone phone) {
        if (owner == null) {
            owner = phone;
        } else {
            System.out.println("SIM already assigned to another phone!");
        }
    }

    public Phone getOwner() {
        return owner;
    }

    // Getters
    public double getCredit() { return credit; }
    public boolean isActive() { return active; }
    public String getNumber() { return number; }

    // Setters
    public void setActive(boolean active) { this.active = active; }
    public void addCredit(double amount) {
        if (amount > 0) credit += amount;
    }

    // Methods
    public boolean hasEnoughCredit(double amount) {
        return credit >= amount;
    }

    public void deductCredit(double amount) {
        if (hasEnoughCredit(amount)) {
            credit -= amount;
        }
    }
}
