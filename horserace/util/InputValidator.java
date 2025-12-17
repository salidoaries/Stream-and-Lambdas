package horserace.util;

import horserace.exception.InvalidInputException;
import java.util.Scanner;

public class InputValidator {
    private static final Scanner sc = new Scanner(System.in);

    public static int getInt(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        String input = sc.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Invalid number. Please enter digits only.");
        }
    }

    public static String getString(String prompt) throws InvalidInputException {
        System.out.print(prompt);
        String input = sc.nextLine().trim();
        if (input.isEmpty()) {
            throw new InvalidInputException("Input cannot be empty.");
        }
        return input;
    }

    public static int getValidInt(String prompt) {
        while (true) {
            try {
                return getInt(prompt);
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public static String getValidString(String prompt) {
        while (true) {
            try {
                return getString(prompt);
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
