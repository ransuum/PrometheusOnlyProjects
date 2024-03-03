package ua.proj1.Entity;



import ua.proj1.Service.FinancialTrackingSystem;

import java.util.UUID;

public class Account{
    private UUID id;
    private double remainder;

    public boolean isCredit() {
        return credit;
    }

    public void setCredit(boolean credit) {
        this.credit = credit;
    }

    private boolean credit;

    public void displayAccountInfo(FinancialTrackingSystem financialTrackingSystem) {
        for (Account account : financialTrackingSystem.getAccounts()) {
            System.out.println("id: " + account.getId() + " Remainder: " + account.getRemainder() + " credit: " + credit + "\n");
        }
    }
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setRemainder(double remainder) {
        this.remainder = remainder;
    }

    public double getRemainder() {
        return remainder;
    }
}
