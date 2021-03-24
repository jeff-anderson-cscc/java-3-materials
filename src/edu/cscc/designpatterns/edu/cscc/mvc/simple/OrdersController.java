package edu.cscc.designpatterns.edu.cscc.mvc.simple;

import edu.cscc.designpatterns.repository.Order;

public interface OrdersController {

    public void show();
    public void edit();
    public void save(Order order);

}
