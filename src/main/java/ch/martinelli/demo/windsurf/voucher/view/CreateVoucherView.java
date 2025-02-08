package ch.martinelli.demo.windsurf.voucher.view;

import ch.martinelli.demo.windsurf.voucher.entity.Customer;
import ch.martinelli.demo.windsurf.voucher.entity.Voucher;
import ch.martinelli.demo.windsurf.voucher.layout.MainLayout;
import ch.martinelli.demo.windsurf.voucher.service.CustomerService;
import ch.martinelli.demo.windsurf.voucher.service.VoucherService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Route(value = "vouchers/create", layout = MainLayout.class)
@PageTitle("Create Voucher")
public class CreateVoucherView extends VerticalLayout {

    private final VoucherService voucherService;
    private final Binder<VoucherFormData> binder;

    private BigDecimalField amountField;
    private DatePicker validFromDate;
    private DatePicker validUntilDate;
    private ComboBox<Customer> customerComboBox;

    public CreateVoucherView(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.binder = new Binder<>(VoucherFormData.class);
        this.setSpacing(true);
        this.setPadding(true);

        // Create voucher form
        amountField = new BigDecimalField("Amount");
        validFromDate = new DatePicker("Valid From");
        validUntilDate = new DatePicker("Valid Until");
        customerComboBox = new ComboBox<>("Customer");
        customerComboBox.setItems(customerService.findAll());
        customerComboBox.setItemLabelGenerator(customer -> customer.getFirstName() + " " + customer.getLastName());

        // Setup binder
        binder.forField(amountField)
            .asRequired("Amount is required")
            .bind(VoucherFormData::getAmount, VoucherFormData::setAmount);

        binder.forField(validFromDate)
            .asRequired("Valid from date is required")
            .bind(VoucherFormData::getValidFrom, VoucherFormData::setValidFrom);

        binder.forField(validUntilDate)
            .asRequired("Valid until date is required")
            .bind(VoucherFormData::getValidUntil, VoucherFormData::setValidUntil);

        binder.forField(customerComboBox)
            .asRequired("Customer is required")
            .bind(VoucherFormData::getCustomer, VoucherFormData::setCustomer);

        Button createButton = new Button("Create Voucher", e -> createVoucher());
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Layout for form
        FormLayout formLayout = new FormLayout();
        formLayout.setResponsiveSteps(
            new ResponsiveStep("0", 1),
            new ResponsiveStep("500px", 2)
        );

        // Add components in specific order
        formLayout.add(amountField, customerComboBox);
        formLayout.add(validFromDate, validUntilDate);

        // Set column spans
        formLayout.setColspan(amountField, 1);
        formLayout.setColspan(customerComboBox, 1);
        formLayout.setColspan(validFromDate, 1);
        formLayout.setColspan(validUntilDate, 1);

        // Back button
        Button backButton = new Button("Back", e -> getUI().ifPresent(ui -> ui.navigate(VoucherView.class)));
        backButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        // Layout for buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(createButton, backButton);
        buttonLayout.setSpacing(true);

        // Add components
        add(formLayout, buttonLayout);
        
        // Initialize with some default values
        binder.setBean(createDefaultFormData());
    }

    private VoucherFormData createDefaultFormData() {
        VoucherFormData formData = new VoucherFormData();
        formData.setValidFrom(LocalDate.now());
        formData.setValidUntil(LocalDate.now().plusMonths(3));
        return formData;
    }

    private void createVoucher() {
        try {
            VoucherFormData formData = new VoucherFormData();
            if (binder.writeBeanIfValid(formData)) {
                Voucher voucher = voucherService.createVoucher(
                    formData.getAmount(),
                    LocalDateTime.of(formData.getValidFrom(), LocalTime.MIN),
                    LocalDateTime.of(formData.getValidUntil(), LocalTime.MAX),
                    formData.getCustomer().getId()
                );

                if (voucher != null) {
                    Notification.show("Voucher created: " + voucher.getCode());
                    clearForm();
                    // Navigate back to the main view
                    getUI().ifPresent(ui -> ui.navigate(VoucherView.class));
                }
            }
        } catch (Exception e) {
            Notification.show("Failed to create voucher: " + e.getMessage());
        }
    }

    private void clearForm() {
        binder.setBean(createDefaultFormData());
    }

    public static class VoucherFormData {
        private BigDecimal amount;
        private LocalDate validFrom;
        private LocalDate validUntil;
        private Customer customer;

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public LocalDate getValidFrom() {
            return validFrom;
        }

        public void setValidFrom(LocalDate validFrom) {
            this.validFrom = validFrom;
        }

        public LocalDate getValidUntil() {
            return validUntil;
        }

        public void setValidUntil(LocalDate validUntil) {
            this.validUntil = validUntil;
        }

        public Customer getCustomer() {
            return customer;
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }
    }
}
