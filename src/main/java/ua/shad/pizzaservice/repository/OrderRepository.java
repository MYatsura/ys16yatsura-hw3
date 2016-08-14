package ua.shad.pizzaservice.repository;

import ua.shad.pizzaservice.domain.Order;

/**
 *
 * @author andrii
 */
public interface OrderRepository {
    
    Order getOrderById(Integer id);
    
    Integer saveOrder(Order order);
    
}
