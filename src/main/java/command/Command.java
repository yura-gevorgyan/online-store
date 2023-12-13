package command;

public interface Command {

    String LOGIN = "1";
    String EXIT = "0";
    String REGISTER = "2";
    String LOGOUT = "0";
    String PRINT_ALL_PRODUCTS = "1";
    String BUY_PRODUCT = "2";
    String PRINT_MY_ORDERS = "3";
    String CANCEL_ORDER = "4";
    String ADD_PRODUCT = "1";
    String REMOVE_PRODUCT_BY_ID = "2";
    String PRINT_PRODUCTS = "3";
    String PRINT_USERS = "4";
    String PRINT_ORDERS = "5";
    String CHANGE_ORDER_STATUS = "6";
    String EXPORT_ORDERS_TO_EXCEL = "7";

    static void printCommand() {
        System.out.println("Please input " + EXIT + " for EXIT");
        System.out.println("Please input " + LOGIN + " for LOGIN");
        System.out.println("Please input " + REGISTER + " for REGISTER");
    }

    static void printUserCommand() {
        System.out.println("Please input " + LOGOUT + " for LOGOUT");
        System.out.println("Please input " + PRINT_ALL_PRODUCTS + " for PRINT ALL PRODUCTS");
        System.out.println("Please input " + BUY_PRODUCT + " for BUY PRODUCT");
        System.out.println("Please input " + PRINT_MY_ORDERS + " for PRINT YOUR ORDERS");
        System.out.println("Please input " + CANCEL_ORDER + " for CANCEL ORDER BY ID");
    }

    static void printAdminCommand() {
        System.out.println("Please input " + LOGOUT + " for LOGOUT");
        System.out.println("Please input " + ADD_PRODUCT + " for ADD PRODUCT");
        System.out.println("Please input " + REMOVE_PRODUCT_BY_ID + " for REMOVE PRODUCT BY ID");
        System.out.println("Please input " + PRINT_PRODUCTS + " for PRINT PRODUCTS");
        System.out.println("Please input " + PRINT_USERS + " for PRINT USERS");
        System.out.println("Please input " + PRINT_ORDERS + " for PRINT ORDERS");
        System.out.println("Please input " + CHANGE_ORDER_STATUS + " for CHANGE ORDER STATUS");
        System.out.println("Please input " + EXPORT_ORDERS_TO_EXCEL + " for EXPORT ORDERS TO EXCEL");
    }
}
