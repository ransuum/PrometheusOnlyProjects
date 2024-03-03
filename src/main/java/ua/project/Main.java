package ua.project;

import ua.project.component.ApplicationRunner;

import java.util.Scanner;

public class Main {
    public static Scanner SCANNER = new Scanner(System.in);
    public static void main(String[] args) {
        ApplicationRunner runner = new ApplicationRunner();
        runner.run();
    }
}
