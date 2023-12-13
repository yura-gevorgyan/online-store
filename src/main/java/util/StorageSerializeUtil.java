package util;

import storage.OrderStorage;
import storage.ProductStorage;
import storage.UserStorage;

import java.io.*;

public abstract class StorageSerializeUtil {

    private static final String ORDER_FILE_PATH = "C:\\Users\\Yura\\IdeaProjects\\online-store\\src\\main\\java\\data\\orderStorage.dat";
    private static final String PRODUCT_FILE_PATH = "C:\\Users\\Yura\\IdeaProjects\\online-store\\src\\main\\java\\data\\productStorage.dat";
    private static final String USER_FILE_PATH = "C:\\Users\\Yura\\IdeaProjects\\online-store\\src\\main\\java\\data\\userStorage.dat";


    public static void serializeOrderStorage(OrderStorage orderStorage) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(ORDER_FILE_PATH))) {
            outputStream.writeObject(orderStorage);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void serializeUserStorage(UserStorage userStorage) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(USER_FILE_PATH))) {
            outputStream.writeObject(userStorage);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void serializeProductStorage(ProductStorage productStorage) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(PRODUCT_FILE_PATH))) {
            outputStream.writeObject(productStorage);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static OrderStorage deserializeOrderStorage() {
        File file = new File(ORDER_FILE_PATH);
        if (!file.exists()) {
            return new OrderStorage();
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(ORDER_FILE_PATH))) {
            Object o = inputStream.readObject();
            if (o instanceof OrderStorage orderStorage) {
                return orderStorage;
            }

        } catch (IOException | ClassNotFoundException exception) {
            exception.getStackTrace();
        }
        return new OrderStorage();
    }

    public static UserStorage deserializeUserStorage() {
        File file = new File(USER_FILE_PATH);
        if (!file.exists()) {
            return new UserStorage();
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(USER_FILE_PATH))) {
            Object o = inputStream.readObject();
            if (o instanceof UserStorage userStorage) {
                return userStorage;
            }

        } catch (IOException | ClassNotFoundException exception) {
            exception.getStackTrace();
        }
        return new UserStorage();
    }

    public static ProductStorage deserializeProductStorage() {
        File file = new File(PRODUCT_FILE_PATH);
        if (!file.exists()) {
            return new ProductStorage();
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PRODUCT_FILE_PATH))) {
            Object o = inputStream.readObject();
            if (o instanceof ProductStorage productStorage) {
                return productStorage;
            }

        } catch (IOException | ClassNotFoundException exception) {
            exception.getStackTrace();
        }
        return new ProductStorage();
    }
}
