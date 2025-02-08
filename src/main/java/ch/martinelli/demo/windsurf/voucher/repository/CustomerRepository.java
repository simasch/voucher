package ch.martinelli.demo.windsurf.voucher.repository;

import ch.martinelli.demo.windsurf.voucher.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
