package ua.proj1.Entity;



import ua.proj1.Service.FinancialTrackingSystem;

import java.util.UUID;

public class Category {
    private UUID categoryID;
    private String name;
    public void displayCategoryInfo(FinancialTrackingSystem financialTrackingSystem) {
        for (Category category : financialTrackingSystem.getCategories()){
            System.out.println("Category ID: " + category.getCategoryID() + " Category_NAME: " + category.getName() + "\n");
        }
    }

    public UUID getCategoryID() {
        return categoryID;
    }

    public String getName() {
        return name;
    }

    public void setCategoryID(UUID categoryID) {
        this.categoryID = categoryID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
