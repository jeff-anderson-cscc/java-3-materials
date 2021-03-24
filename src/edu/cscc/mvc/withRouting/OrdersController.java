package edu.cscc.mvc.withRouting;

import edu.cscc.designpatterns.repository.Order;
import edu.cscc.designpatterns.repository.OrderRepository;
import edu.cscc.mvc.withRouting.framework.ApplicationController;
import edu.cscc.mvc.withRouting.framework.MVCContext;

import java.util.List;

public class OrdersController extends ApplicationController {

    private final OrderRepository orderRepository;

    public OrdersController(MVCContext context) {
        super(context);
        orderRepository = OrderRepository.getInstance();
    }

    public void index() {
        List<Order> orders = orderRepository.readAll();
        render(new OrdersIndex(context, orders));
    }

}
