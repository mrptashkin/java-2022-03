package homework;


import java.util.*;

public class CustomerReverseOrder {

    LinkedList<Customer> customerList = new LinkedList<>();

    public void add(Customer customer) {
        customerList.push(customer);
    }

    public Customer take() {
        return customerList.pop();
    }
}
