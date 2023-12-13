package storage;

import model.Order;
import model.User;
import util.StorageSerializeUtil;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class OrderStorage implements Serializable {

    private List<Order> orders = new LinkedList<>();

    public void add(Order order) {
        orders.add(order);
        StorageSerializeUtil.serializeOrderStorage(this);
    }

    public void print() {
        for (Order order : orders) {
            System.out.println(order);
        }
    }

    public Order getById(String orderId) {
        for (Order order : orders) {
            if (order.getId().equals(orderId)){
                return order;
            }
        }
        return null;
    }

    public void getOrderByUser(User user) {
        for (Order order : orders) {
            if (order.getUser().equals(user)){
                System.out.println(order);
            }
        }
    }

    public List<Order> getOrders() {
        return orders;
    }
}
