package ua.shad.pizzaservice.service;

import ua.shad.pizzaservice.domain.Order;
import ua.shad.pizzaservice.domain.Customer;

/**
 *
 * @author andrii
 */
public interface OrderService {

    Order placeNewOrder(Customer customer, Integer... pizzasID);

    Order getOrderById(Integer id);
    
    Order addPizzaToOrder(Order order, Integer pizzaId);
 
    Order deletePizzaFromOrder(Order order, Integer pizzaId);
    
    Order updateOrder(Order order);
    
}

