package ua.shad.pizzaservice.service;

import ua.shad.pizzaservice.domain.Pizza;
import ua.shad.pizzaservice.domain.Order;
import ua.shad.pizzaservice.domain.Customer;
import ua.shad.pizzaservice.repository.OrderRepository;
import ua.shad.pizzaservice.repository.PizzaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import ua.shad.pizzaservice.PizzaApp;
import ua.shad.pizzaservice.infrastruct.Benchmark;
import ua.shad.pizzaservice.infrastruct.ServiceLocator;

/**
 *
 * @author andrii
 */
@Service("orderService")
public class SimpleOrderService implements OrderService {
    
    private PizzaRepository pizzaRepository;
    private OrderRepository orderRepository;

    public SimpleOrderService() {        
    }
    
    @Autowired
    public SimpleOrderService(
            PizzaRepository pizzaRepozitory, 
            OrderRepository orderRepository) {
        this.pizzaRepository = pizzaRepozitory;
        this.orderRepository = orderRepository;
    }    

    public PizzaRepository getPizzaRepozitory() {
        return pizzaRepository;
    }

    public void setPizzaRepozitory(PizzaRepository pizzaRepozitory) {
        this.pizzaRepository = pizzaRepozitory;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }    
    
    @Override
    @Benchmark(active = true)
    public Order placeNewOrder(Customer customer, Integer ... pizzasID) {
        List<Pizza> pizzas = pizzasListFromArrayOfIds(pizzasID);
        Order newOrder = getNewOrder();
        newOrder.setCustomer(customer);
        newOrder.setPizzas(pizzas);
       
        orderRepository.saveOrder(newOrder);  // set Order Id and save Order to in-memory list
        return newOrder;
    }   
    
    @Lookup
    protected Order getNewOrder() {
        return null;
    }

    private List<Pizza> pizzasListFromArrayOfIds(Integer[] pizzasID) {
        List<Pizza> pizzas = new ArrayList<>();
        for(Integer id : pizzasID){
            pizzas.add(pizzaRepository.getPizzaById(id));  // get Pizza from predifined in-memory list
        }
        return pizzas;
    }
    
    @Override
    public Order getOrderById(Integer id) {
        
        Order order = null;
        try {
            order = orderRepository.getOrderById(id);
        } catch (RuntimeException e) {
            return null;
        }
                
        return order;
    }
    
    @Override
    public Order addPizzaToOrder(Order order, Integer pizzaId) {
        List<Pizza> pizzas = order.getPizzas();
        pizzas.add(pizzaRepository.getPizzaById(pizzaId));
        order.setPizzas(pizzas);
        return order;
    }  
}
