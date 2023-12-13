

import command.Command;
import model.Order;
import model.Product;
import model.User;
import model.enums.OrderStatus;
import model.enums.PaymentMethod;
import model.enums.ProductType;
import model.enums.UserType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import storage.OrderStorage;
import storage.ProductStorage;
import storage.UserStorage;
import util.StorageSerializeUtil;
import util.UUIDUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class OnlineMarketMain implements Command {

    private static Scanner scanner = new Scanner(System.in);
    private static UserStorage userStorage = StorageSerializeUtil.deserializeUserStorage();
    private static ProductStorage productStorage = StorageSerializeUtil.deserializeProductStorage();
    private static OrderStorage orderStorage = StorageSerializeUtil.deserializeOrderStorage();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Command.printCommand();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    login();
                    break;
                case REGISTER :
                    register();
                    break;
            }
        }
    }

    private static void register() {

        String userId = UUIDUtil.randomId();

        System.out.println("Please input NAME");
        String userName = scanner.nextLine();

        System.out.println("Please input E MAIL");
        String userEmail = scanner.nextLine();
        User userFromStorage = userStorage.getByEmail(userEmail);

        if (userFromStorage != null) {
            System.out.println("User with this EMAIL have already added");
            return;
        }

        System.out.println("Please input password");
        String userPassword = scanner.nextLine();

        for (UserType value : UserType.values()) {
            System.out.print(value + " ");
        }

        System.out.println();

        System.out.println("Please choose USER TYPE");
        String userTypeStr = scanner.nextLine();
        try {

            UserType userType = UserType.valueOf(userTypeStr.toUpperCase());
            User user = new User(userId, userName, userEmail, userPassword, userType);
            userStorage.add(user);

        } catch (IllegalArgumentException e) {
            System.out.println("Please write right USER TYPE !!!");
        }
    }

    private static void login() {

        System.out.println("Please input E MAIL");
        String userEmail = scanner.nextLine();
        System.out.println("Please input PASSWORD");
        String password = scanner.nextLine();

        User user = userStorage.getByEmailAndPassword(userEmail, password);

        if (user == null) {
            System.out.println("Invalid E-mail or Password !!!");
            return;
        }

        if (user.getUserType().equals(UserType.USER)) {
            userCommand(user);
        }

        if (user.getUserType().equals(UserType.ADMIN)) {
            adminCommand();
        }
    }

    private static void adminCommand() {
        boolean isRun = true;
        while (isRun) {
            Command.printAdminCommand();
            String command = scanner.nextLine();
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case REMOVE_PRODUCT_BY_ID:
                    deleteProduct();
                    break;
                case PRINT_PRODUCTS:
                    productStorage.print();
                    break;
                case PRINT_USERS:
                    userStorage.print();
                    break;
                case PRINT_ORDERS:
                    orderStorage.print();
                    break;
                case CHANGE_ORDER_STATUS:
                    changeOrderStatus();
                    break;
                case EXPORT_ORDERS_TO_EXCEL:
                    exportOrdersToExel();
                    break;
                default:
                    System.out.println("Invalid command. PLEASE TRY AGAIN !!!");
            }
        }
    }

    private static void exportOrdersToExel() {
        System.out.println("Please input PATH");
        String path = scanner.nextLine();
        File directory = new File(path);

        if (directory.exists() && directory.isDirectory()) {

            List<Order> orders = orderStorage.getOrders();
            try (Workbook workbook = new XSSFWorkbook()) {

                Sheet ordersSheet = workbook.createSheet("orders");
                Row headRow = createHeadRow(ordersSheet);

                int rowIndex = 1;
                for (Order order : orders) {
                    rowIndex = createRow(order, ordersSheet, rowIndex, headRow);
                }

                workbook.write(new FileOutputStream(new File(directory, "report_" + System.currentTimeMillis() + ".xlsx")));

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("WRONG PATH !!!");
        }
    }

    private static int createRow(Order order, Sheet ordersSheet, int rowIndex, Row headRow) {
        Row row = ordersSheet.createRow(rowIndex++);

        Cell idCell = row.createCell(0);
        idCell.setCellValue(order.getId());

        Cell nameCell = row.createCell(1);
        nameCell.setCellValue(order.getProduct().getName());

        Cell countCell = row.createCell(2);
        countCell.setCellValue(order.getCount());

        Cell priceCell = row.createCell(3);
        priceCell.setCellValue(order.getPrice());

        Cell userCell = row.createCell(4);
        userCell.setCellValue(order.getUser().getEmail());

        Cell statusCell = row.createCell(5);
        statusCell.setCellValue(order.getOrderStatus().toString());

        Cell dataCell = row.createCell(6);
        dataCell.setCellValue(order.getDate());
        return rowIndex;
    }

    private static Row createHeadRow(Sheet ordersSheet) {
        Row headRow = ordersSheet.createRow(0);

        Cell headIdCell = headRow.createCell(0);
        headIdCell.setCellValue("ORDER ID");

        Cell headNameCell = headRow.createCell(1);
        headNameCell.setCellValue("ORDER NAME");

        Cell headCountCell = headRow.createCell(2);
        headCountCell.setCellValue("ORDER COUNT");

        Cell headPriceCell = headRow.createCell(3);
        headPriceCell.setCellValue("ORDER PRICE");

        Cell headUserCell = headRow.createCell(4);
        headUserCell.setCellValue("USER");

        Cell headStatusCell = headRow.createCell(5);
        headStatusCell.setCellValue("ORDER STATUS");

        Cell headDataCell = headRow.createCell(6);
        headDataCell.setCellValue("DATA");
        return headRow;
    }

    private static void changeOrderStatus() {
        orderStorage.print();

        System.out.println("Please choose ORDER ID");
        String orderId = scanner.nextLine();

        Order orderFromStorage = orderStorage.getById(orderId);

        if (orderFromStorage == null) {
            System.out.println("Wrong ORDER ID !!!");
            return;
        }

        for (OrderStatus value : OrderStatus.values()) {
            System.out.print(value + " ");
        }

        System.out.println();

        System.out.println("Please choose NEW STATUS");
        String orderStatusStr = scanner.nextLine();

        try {
            OrderStatus orderStatus = OrderStatus.valueOf(orderStatusStr.toUpperCase());
            if (orderFromStorage.getOrderStatus() == OrderStatus.NEW && orderStatus == OrderStatus.DELIVERED) {
                int count = orderFromStorage.getProduct().getStockQty() - orderFromStorage.getCount();
                orderFromStorage.setOrderStatus(orderStatus);
                orderFromStorage.getProduct().setStockQty(count);
                StorageSerializeUtil.serializeOrderStorage(orderStorage);
                System.out.println("ORDER STATUS is updated !!!");
                return;
            }

            if (orderFromStorage.getOrderStatus() == OrderStatus.DELIVERED && orderStatus == OrderStatus.CANCELED || orderStatus == OrderStatus.NEW) {
                int count = orderFromStorage.getCount() + orderFromStorage.getProduct().getStockQty();
                orderFromStorage.getProduct().setStockQty(count);
                orderFromStorage.setOrderStatus(orderStatus);
                StorageSerializeUtil.serializeOrderStorage(orderStorage);
                System.out.println("ORDER STATUS is updated !!!");
                return;
            }

            if (orderFromStorage.getOrderStatus() == OrderStatus.NEW && orderStatus == OrderStatus.CANCELED) {
                orderFromStorage.setOrderStatus(orderStatus);
                StorageSerializeUtil.serializeOrderStorage(orderStorage);
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Please write right STATUS !!!");
        }
    }

    private static void deleteProduct() {
        productStorage.print();
        System.out.println("Please choose PRODUCT ID");
        String productId = scanner.nextLine();
        Product productFromStorage = productStorage.getProductByID(productId);

        if (productFromStorage == null) {
            System.out.println("Wrong PRODUCT ID !!!");
            return;
        }

        productStorage.deleteProduct(productFromStorage);
        StorageSerializeUtil.serializeProductStorage(productStorage);
    }

    private static void addProduct() {
        System.out.println("Please input PRODUCT ID");
        String productId = scanner.nextLine();
        Product productFromStorage = productStorage.getProductByID(productId);

        if (productFromStorage != null) {
            System.out.println("Product by " + productId + " have already added!!!");
            return;
        }
        System.out.println("Please input PRODUCT NAME");
        String productName = scanner.nextLine();

        System.out.println("Please input PRODUCT DESCRIPTION");
        String productDescription = scanner.nextLine();

        System.out.println("Please input PRODUCT PRICE");
        double productPrice = Double.parseDouble(scanner.nextLine());
        try {
            System.out.println("Please input PRODUCT COUNT");
            int productCount = Integer.parseInt(scanner.nextLine());


            for (ProductType value : ProductType.values()) {
                System.out.print(value + " ");
            }

            System.out.println();
            System.out.println("Please choose PRODUCT TYPE");

            try {

                String productTypeStr = scanner.nextLine();
                ProductType productType = ProductType.valueOf(productTypeStr.toUpperCase());

                Product product = new Product(productId, productName, productDescription, productPrice, productCount, productType);
                productStorage.add(product);

                System.out.println("Product is added !!!");

            } catch (IllegalArgumentException e) {
                System.out.println("Wrong PRODUCT TYPE !!!");
            }
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void userCommand(User user) {

        boolean isRun = true;
        while (isRun) {
            Command.printUserCommand();
            String command = scanner.nextLine();
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    break;
                case PRINT_ALL_PRODUCTS:
                    productStorage.print();
                    break;
                case BUY_PRODUCT:
                    buyProduct(user);
                    break;
                case PRINT_MY_ORDERS:
                    printOrders(user);
                    break;
                case CANCEL_ORDER:
                    deleteOrder(user);
                    break;
            }
        }
    }

    private static void deleteOrder(User user) {
        orderStorage.getOrderByUser(user);
        System.out.println("Please choose ORDER ID");
        String orderId = scanner.nextLine();

        Order orderFromStorage = orderStorage.getById(orderId);

        if (orderFromStorage == null) {
            System.out.println("Please write right ORDER ID");
            return;
        }

        orderFromStorage.setOrderStatus(OrderStatus.CANCELED);
        StorageSerializeUtil.serializeOrderStorage(orderStorage);

    }

    private static void printOrders(User user) {
        orderStorage.getOrderByUser(user);
    }

    private static void buyProduct(User user) {
        productStorage.print();
        System.out.println("Please choose PRODUCT BY ID");

        String productId = scanner.nextLine();
        Product productFromStorage = productStorage.getProductByID(productId);

        if (productFromStorage == null) {
            System.out.println("You input PRODUCT ID wrong !!!");
            return;
        }


        String orderID = UUIDUtil.randomId();
        try {
            System.out.println("Please input Order count. We have " + productFromStorage.getStockQty() + " from that product");
            int orderCount = Integer.parseInt(scanner.nextLine());

            if (orderCount > productFromStorage.getStockQty()) {
                System.out.println("Please input little count!!!");
                return;
            }

            double orderPrice = orderCount * productFromStorage.getPrice();

            System.out.println("Please choose PAYMENT METHOD ");

            for (PaymentMethod value : PaymentMethod.values()) {
                System.out.print(value + " ");
            }

            String paymentMethodStr = scanner.nextLine();
            PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodStr.toUpperCase());

            System.out.println("You want buy " + productFromStorage.getName() + " with " + orderCount + " count with " + orderPrice + " price");
            System.out.println("Please input 1 for YES");
            System.out.println("Please input 2 for NO");
            String command = scanner.nextLine();
            switch (command) {
                case "1":
                    Order order = new Order(orderID, user, productFromStorage, new Date(), orderCount, orderPrice, OrderStatus.NEW, paymentMethod);
                    orderStorage.add(order);
                    break;
                case "2":
                    break;
            }


        } catch (IllegalArgumentException e) {
            System.out.println("Wrong count !!!");
        }


    }
}
