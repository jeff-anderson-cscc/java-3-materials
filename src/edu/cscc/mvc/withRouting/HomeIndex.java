package edu.cscc.mvc.withRouting;

import edu.cscc.mvc.withRouting.framework.ApplicationView;
import edu.cscc.mvc.withRouting.framework.MVCContext;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HomeIndex extends ApplicationView {
    /**
     * Sets the context and configures it to exit by default
     * unless a route is specified.
     *
     * @param context The {@link MVCContext}.
     */
    public HomeIndex(MVCContext context) {
        super(context);
    }

    @Override
    public void show() {
        System.out.println("Welcome to the order management system.");
        System.out.println("1. View orders");
        System.out.println("2. Exit");
        System.out.print("What say ye? ");
        Scanner scanner= new Scanner(System.in);
        try {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    route("Orders", "index");
                    break;
                case 2:
                    route("Home", "goodbye");
                    break;
            }
        } catch (InputMismatchException ex) {
            route("Home", "index");
        }
    }
}
