package ch.martinelli.demo.windsurf.voucher.repository;

import ch.martinelli.demo.windsurf.voucher.entity.Voucher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    
    @Override
    @EntityGraph(attributePaths = {"customer"})
    List<Voucher> findAll();
    
    @EntityGraph(attributePaths = {"customer"})
    Optional<Voucher> findByCode(String code);
    
    List<Voucher> findByUsedAtNullAndValidUntilAfterAndValidFromBeforeAndCustomer_Id(
        LocalDateTime validUntil,
        LocalDateTime validFrom,
        Long customerId
    );
    
    boolean existsByCode(String code);
}
