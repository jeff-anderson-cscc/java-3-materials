package edu.cscc.mvc.simple;

import edu.cscc.designpatterns.repository.Order;

public interface OrdersController {
    void show();
    void edit();
    void save(Order order);
}
