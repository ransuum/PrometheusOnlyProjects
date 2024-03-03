package ua.proj1.Service;



import ua.proj1.Entity.Account;
import ua.proj1.Entity.Categories;
import ua.proj1.Entity.Category;
import ua.proj1.Entity.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiPredicate;

public class FinancialTrackingSystem {
    private final List<Account> accountss = new ArrayList<>();
    private final List<Category> categories = new ArrayList<>();
    private final List<Transaction> transactions = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private int countKYIVS = 0;
    private int countDATA_GROUP_INTERNET = 0;
    private int countFOOD = 0;
    private int countSHOP = 0;

    public void createAccount() {
        Account account = new Account();
        System.out.print("Ur remainder: ");
        double remainder = 0;
        try{
            remainder = scanner.nextDouble();
        } catch (InputMismatchException e){
            System.out.println("incorrect input");
            return;
        }
        account.setId(UUID.randomUUID());
        account.setRemainder(remainder);
        accountss.add(account);
        System.out.println("Created!");
    }

    public void changeAccountInfo(){
        System.out.print("Input id: ");
        UUID uuid = UUID.fromString(scanner.next());
        for (Account account : accountss){
            if (account.getId().equals(uuid)){
                System.out.println("Account found");
                System.out.print("Input new remainder: ");
                double remainder = 0;
                try {
                    remainder = scanner.nextDouble();
                } catch (InputMismatchException e){
                    System.out.println("Incorrect input numb");
                }
                account.setRemainder(remainder);
                account.setCredit(false);
            }

        }
    }

    public void deleteAccount(Account account) {
        for (Account account1 : accountss) {
            if (account1.getId().equals(account.getId())) {
                accountss.remove(account1);
                for (Transaction transaction : transactions){
                    if (transaction.getAccId().equals(account.getId())){
                        transactions.remove(transaction);
                    }
                }
                System.out.println("Account with id: " + account1.getId() + " - Completely deleted");
                break;
            } else {
                System.out.println("Account with this id not found");
                break;
            }
        }
    }

    public void createCategory() {
        Category category = new Category();
        System.out.println("Введіть назву категорії:");
        for (Categories categories1 : Categories.values()){
            System.out.println(String.valueOf(categories1));
        }
        String categoryName = scanner.next();
        category.setName(categoryName.trim().toUpperCase());
        category.setCategoryID(UUID.randomUUID());
        categories.add(category);
        System.out.println("Created!");
    }

    public void deleteCategory(Category category) {
        for (Category category1 : categories) {
            if (category1.getCategoryID().equals(category.getCategoryID())) {
                categories.remove(category1);
                for (Transaction transaction : transactions){
                    if (transaction.getCategoryId().equals(category1.getCategoryID())){
                        transactions.remove(transaction);
                    }
                }
                System.out.println("Category with " + category.getCategoryID() + " id was successfully deleted");
                break;
            } else {
                System.out.println("There is no Category with this id");
                break;
            }
        }
    }

    public void recordTransaction(Account accountToFound, Category categoryToFind, Transaction transaction ) {
        System.out.println("Write id and remainder for acc");
        System.out.print("id: ");
        UUID accID = UUID.fromString(scanner.next());
        System.out.print("Reminder: ");
        double remainder = 0;
        try {
            remainder = scanner.nextDouble();
        } catch (InputMismatchException e){
            System.out.println("incorrect input");
        }
        for (Account account : accountss) {
            if (account.getId().equals(accID) && account.getRemainder() == remainder) {
                accountToFound = account;
            }
        }
        System.out.println("Write id and name fo Category");
        System.out.print("id: ");
        UUID categoryID = UUID.fromString(scanner.next());
        System.out.print("Category name: ");
        String categoryName = scanner.next();
        for (Category category : categories) {
            if (category.getCategoryID().equals(categoryID) && category.getName().equals(categoryName)) {
                categoryToFind = category;
                break;
            }
        }
        if (categoryToFind.getCategoryID() != null && accountToFound.getId() != null) {
            System.out.println("Category and Acc was found");
            System.out.print("Input ur sum of transaction: ");
            double sum = scanner.nextDouble();
            if (remainder - sum < 0) {
                System.out.print("У Вас рахунок йде в мінус, тому транзакцію не записано. Чи сплатите Ви цей кредит?(так/ні)   ");
                String answer = scanner.next();
                if (answer.equals("так")){
                    UUID transactionID = UUID.randomUUID();
                    accountToFound.setRemainder(remainder - sum);
                    BiPredicate<Integer, Integer> biPredicate = (n1, n2) -> n1 - n2 < 0;
                    accountToFound.setCredit(biPredicate.test((int) remainder, (int) sum));
                    transaction.setCategory(categoryToFind.getName());
                    transaction.setSumOfTrans(sum);
                    transaction.setCategoryId(categoryToFind.getCategoryID());
                    transaction.setAccId(accountToFound.getId());
                    transaction.setTransactionID(transactionID);
                    transaction.setDescr("Transaction for account with {remainder = " + accountToFound.getRemainder() + "} {id = " + accountToFound.getId() + "}");
                    transaction.setTimeStamp(new Date());
                    transactions.add(transaction);
                    System.out.println("CREATED!");
                }
            } else {
                UUID transactionID = UUID.randomUUID();
                accountToFound.setRemainder(remainder - sum);
                BiPredicate<Integer, Integer> biPredicate = (n1, n2) -> n1 - n2 < 0;
                accountToFound.setCredit(biPredicate.test((int) remainder, (int) sum));
                transaction.setCategory(categoryToFind.getName());
                transaction.setSumOfTrans(sum);
                transaction.setTransactionID(transactionID);
                transaction.setDescr("Transaction for account with {remainder = " + accountToFound.getRemainder() + "} {id = " + accountToFound.getId() + "}");
                transaction.setTimeStamp(new Date());
                transactions.add(transaction);
                System.out.println("CREATED!");
            }
        }
    }

    public void analyzeTransactionsByCategory() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.print("Input category name to find: ");
        String name = scanner.next();
        List<Transaction> transactions1 = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getCategory().equals(name)) {
                transactions1.add(transaction);
            }
        }
        if (!transactions1.isEmpty()) {
            System.out.print("Введите начальную дату в формате yyyy-MM-dd:");
            Date startDate = dateFormat.parse(scanner.next());

            System.out.print("Введите конечную дату в формате yyyy-MM-dd:");
            Date endDate = dateFormat.parse(scanner.next());

            List<Transaction> transactionsByDates = new ArrayList<>();
            for (Transaction transaction : transactions1) {
                if (transaction.getTimestamp().after(startDate) && transaction.getTimestamp().before(endDate)) {
                    transactionsByDates.add(transaction);
                }
            }
            if (transactionsByDates.isEmpty()) {
                System.out.println("There is no transaction between these dates.....");
            } else {
                transactionsByDates.sort(Comparator.comparing(Transaction::getTimestamp));
                System.out.println("Transaction from " + startDate + " to " + endDate + ":");
                for (Transaction transaction : transactionsByDates) {
                    System.out.println("Transaction_ID:" + transaction.getTransactionID() + "; Sum of trans:" + transaction.getSumOfTrans()
                            + "; Description:" + transaction.getDescr() + "; Category_NAME:" + transaction.getCategory() + "; DATE: " + transaction.getTimestamp());
                }
            }
        } else {
            System.out.println("We can't found transaction with this name.....");
        }
    }

    public String findMostPopularCategory() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.print("Введите начальную дату в формате yyyy-MM-dd:");
        Date startDate = dateFormat.parse(scanner.next());

        System.out.print("Введите конечную дату в формате yyyy-MM-dd:");
        Date endDate = dateFormat.parse(scanner.next());

        List<Transaction> transactionsByDates = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getTimestamp().after(startDate) && transaction.getTimestamp().before(endDate)) {
                transactionsByDates.add(transaction);
            }
        }
        if (transactionsByDates.isEmpty()) {
            System.out.println("There is no transaction between these dates.....");
        } else {
            transactionsByDates.sort(Comparator.comparing(Transaction::getTimestamp));
            System.out.println("Transaction from " + startDate + " to " + endDate + ":");
            for (Transaction transaction : transactionsByDates) {
                System.out.println("Transaction_ID:" + transaction.getTransactionID() + "; Sum of trans:" + transaction.getSumOfTrans()
                        + "; Description:" + transaction.getDescr() + "; Category_NAME:" + transaction.getCategory() + "; DATE: " + transaction.getTimestamp());
            }
        }
        List<Transaction> transactionsByNameOfCategory = new ArrayList<>();
        for (Transaction transaction: transactionsByDates){
            if (transaction.getCategory().equals(Categories.KYIVSTAR_INTERNET.toString())){
                countKYIVS++;
            }
            if (transaction.getCategory().equals(Categories.DATA_GROUP_INTERNET.toString())){
                countDATA_GROUP_INTERNET++;
            }
            if (transaction.getCategory().equals(Categories.FOOD.toString())){
                countFOOD++;
            }
            if (transaction.getCategory().equals(Categories.SHOP.toString())){
                countSHOP++;
            }
        }

        if (countKYIVS > countDATA_GROUP_INTERNET && countKYIVS > countFOOD && countKYIVS > countSHOP){
            for (Transaction transaction : transactionsByDates){
                if (transaction.getCategory().equals(Categories.KYIVSTAR_INTERNET.toString())){
                    transactionsByNameOfCategory.add(transaction);
                }
            }
        }
        if (countDATA_GROUP_INTERNET > countKYIVS && countDATA_GROUP_INTERNET > countFOOD && countDATA_GROUP_INTERNET > countSHOP){
            for (Transaction transaction : transactionsByDates){
                if (transaction.getCategory().equals(Categories.DATA_GROUP_INTERNET.toString())){
                    transactionsByNameOfCategory.add(transaction);
                }
            }
        }
        if (countFOOD > countKYIVS && countFOOD > countDATA_GROUP_INTERNET && countFOOD > countSHOP){
            for (Transaction transaction : transactionsByDates){
                if (transaction.getCategory().equals(Categories.FOOD.toString())){
                    transactionsByNameOfCategory.add(transaction);
                }
            }
        }
        if (countSHOP > countKYIVS && countSHOP > countDATA_GROUP_INTERNET && countSHOP > countFOOD){
            for (Transaction transaction : transactionsByDates){
                if (transaction.getCategory().equals(Categories.SHOP.toString())){
                    transactionsByNameOfCategory.add(transaction);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Transaction transaction : transactionsByNameOfCategory){
            stringBuilder.append("transactionID:").append(transaction.getTransactionID()).append(" Sum:").append(transaction.getSumOfTrans()).append(" Description: ")
                    .append(transaction.getDescr()).append(" {Category name = ").append(transaction.getCategory())
                    .append("}").append(" {Time of creation: ").append(transaction.getTimestamp()).append("}").append("\n");
        }
        return stringBuilder.toString();
    }
    public void displaySystemInfo(FinancialTrackingSystem financialTrackingSystem, Account account, Category category, Transaction transaction){
        System.out.println("Account INFO:");
        account.displayAccountInfo(financialTrackingSystem);
        System.out.println("Category INFO:");
        category.displayCategoryInfo(financialTrackingSystem);
        System.out.println("Transaction INFO:");
        System.out.println(transaction.displayTransactionInfo(financialTrackingSystem));
    }
    public List<Account> getAccounts() {
        return accountss;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
