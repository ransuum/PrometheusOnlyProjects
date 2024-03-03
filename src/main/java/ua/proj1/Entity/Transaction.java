package ua.proj1.Entity;




import ua.proj1.Service.FinancialTrackingSystem;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    private UUID transactionID;
    private double sumOfTrans;
    private String descr;
    private String category;
    private Date timestamp;
    private UUID accId;
    private UUID categoryId;

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }



    public UUID getAccId() {
        return accId;
    }

    public void setAccId(UUID accId) {
        this.accId = accId;
    }



    public double getSumOfTrans(){
        return sumOfTrans;
    }

    public String displayTransactionInfo(FinancialTrackingSystem financialTrackingSystem) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Transaction transaction : financialTrackingSystem.getTransactions()){
            stringBuilder.append("transactionID:").append(transaction.getTransactionID()).append(" Sum:").append(transaction.getSumOfTrans()).append(" Description: ")
                    .append(transaction.getDescr()).append(" {Category name = ").append(transaction.getCategory())
                    .append("}").append(" {Time of creation: ").append(transaction.getTimestamp()).append("}").append("\n");
        }
        return stringBuilder.toString();
    }

    public UUID getTransactionID() {
        return transactionID;
    }
    public String getDescr() {
        return descr;
    }

    public String getCategory() {
        return category;
    }

    public Date getTimestamp() {
        return timestamp;
    }
    public void setTimeStamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setTransactionID(UUID transactionID) {
        this.transactionID = transactionID;
    }

    public void setSumOfTrans(double sumOfTrans) {
        this.sumOfTrans = sumOfTrans;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
