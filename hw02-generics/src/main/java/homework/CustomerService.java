package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    TreeMap<Customer, String> treeMap = new TreeMap<>(Comparator.comparing(Customer::getScores));


    public Map.Entry<Customer, String> getSmallest() {
        if (treeMap.firstEntry() == null) {
            return null;
        } else {
            Customer temporaryCustomer = new Customer(treeMap.firstEntry().getKey().getId(), treeMap.firstEntry().getKey().getName(), treeMap.firstEntry().getKey().getScores());
            return new TreeMap.SimpleEntry<>(temporaryCustomer, treeMap.firstEntry().getValue());
        }
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        if (treeMap.higherEntry(customer) == null) {
            return null;
        }
        else {
            Customer temporaryCustomer = new Customer(treeMap.higherEntry(customer).getKey().getId(), treeMap.higherEntry(customer).getKey().getName(), treeMap.higherEntry(customer).getKey().getScores());
            return new TreeMap.SimpleEntry<>(temporaryCustomer, treeMap.higherEntry(customer).getValue());
        }
    }

    public void add(Customer customer, String data) {
        treeMap.put(customer, data);
    }
}
