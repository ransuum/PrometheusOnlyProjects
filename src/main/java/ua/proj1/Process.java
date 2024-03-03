package ua.proj1;

import ua.proj1.Entity.Account;
import ua.proj1.Entity.Category;
import ua.proj1.Entity.Transaction;
import ua.proj1.Service.FinancialTrackingSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Process {
    private final Scanner scanner = new Scanner(System.in);
    private final List<String> strings = new ArrayList<>();
    private final FinancialTrackingSystem financialTrackingSystem = new FinancialTrackingSystem();

        public void app() throws IOException, ParseException {
            Account account = new Account();
            Category category = new Category();
            Transaction transaction = new Transaction();
            String line;
            System.out.println("Виберіть що хочете зробити: ");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src/main/java/prometheus/org/ua/proj1/instruction")));
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                strings.add(line);
            }
            String answer;
            do {
                System.out.print("Ваш вибір: ");
                answer = scanner.next();
                switch (answer) {
                    case "0":
                        for (String l : strings){
                            System.out.println(l);
                        }
                        break;
                    case "1":
                        financialTrackingSystem.createAccount();
                        break;
                    case "2":
                        Account account1 = new Account();
                        System.out.print("Pls input id which u want to delete: ");
                        UUID id = UUID.fromString(scanner.next());
                        System.out.print("Reminder: ");
                        double rem = scanner.nextDouble();
                        account1.setId(id);
                        account1.setRemainder(rem);
                        financialTrackingSystem.deleteAccount(account1);
                        break;
                    case "3":
                        financialTrackingSystem.createCategory();
                        break;
                    case "4":
                        Category category1 = new Category();
                        System.out.print("Pls input id which u want to delete: ");
                        UUID categoryID = UUID.fromString(scanner.next());
                        System.out.print("Category name: ");
                        String s = scanner.next();
                        category1.setName(s);
                        category1.setCategoryID(categoryID);
                        financialTrackingSystem.deleteCategory(category1);
                        break;
                    case "5":
                        financialTrackingSystem.recordTransaction(account, category, transaction);
                        break;
                    case "6":
                        financialTrackingSystem.analyzeTransactionsByCategory();
                        break;
                    case "7":
                        System.out.println(financialTrackingSystem.findMostPopularCategory());
                        break;
                    case "8":
                        financialTrackingSystem.displaySystemInfo(financialTrackingSystem, account, category, transaction);
                        break;
                    case "9":
                       account.displayAccountInfo(financialTrackingSystem);
                        break;
                    case "10":
                        category.displayCategoryInfo(financialTrackingSystem);
                        break;
                    case "11":
                        System.out.println(transaction.displayTransactionInfo(financialTrackingSystem));
                        break;
                }
            } while (!answer.equalsIgnoreCase("no"));

        }
    }
