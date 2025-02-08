package ch.martinelli.demo.windsurf.voucher.service;

import ch.martinelli.demo.windsurf.voucher.entity.Customer;
import ch.martinelli.demo.windsurf.voucher.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }
}
