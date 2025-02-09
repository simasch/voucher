package ch.martinelli.demo.windsurf.voucher.service;

import ch.martinelli.demo.windsurf.voucher.entity.Voucher;
import ch.martinelli.demo.windsurf.voucher.repository.CustomerRepository;
import ch.martinelli.demo.windsurf.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public VoucherService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Voucher createVoucher(BigDecimal amount, LocalDateTime validFrom, LocalDateTime validUntil, Long customerId) {
        var customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Voucher voucher = new Voucher();
        voucher.setCode(generateUniqueCode());
        voucher.setAmount(amount);
        voucher.setValidFrom(validFrom);
        voucher.setValidUntil(validUntil);
        voucher.setCustomer(customer);

        return voucherRepository.save(voucher);
    }

    @Transactional
    public Optional<Voucher> redeemVoucher(String code, Long customerId) {
        var now = LocalDateTime.now();
        return voucherRepository.findByUsedAtNullAndValidUntilAfterAndValidFromBeforeAndCustomer_Id(
                now, now, customerId)
                .stream()
                .findFirst()
                .map(voucher -> {
                    voucher.setUsedAt(now);
                    return voucherRepository.save(voucher);
                });
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        } while (voucherRepository.existsByCode(code));
        return code;
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.findAll();
    }
}
