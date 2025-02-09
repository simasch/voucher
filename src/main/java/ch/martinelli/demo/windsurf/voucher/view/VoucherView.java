package ch.martinelli.demo.windsurf.voucher.view;

import ch.martinelli.demo.windsurf.voucher.entity.Customer;
import ch.martinelli.demo.windsurf.voucher.entity.Voucher;
import ch.martinelli.demo.windsurf.voucher.layout.MainLayout;
import ch.martinelli.demo.windsurf.voucher.service.VoucherService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.PageTitle;

import java.time.LocalDateTime;

@Route(value = "vouchers", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Voucher Management")
public class VoucherView extends VerticalLayout {

    private final Grid<Voucher> grid;
    private final VoucherService voucherService;

    public VoucherView(VoucherService voucherService) {
        this.voucherService = voucherService;
        this.setSpacing(true);
        this.setPadding(true);


        // Grid showing all vouchers
        grid = new Grid<>(Voucher.class);
        grid.removeAllColumns();

        grid.addColumn(Voucher::getCode).setHeader("Code");
        grid.addColumn(Voucher::getAmount).setHeader("Amount");

        // Format dates as dd.MM.yyyy
        grid.addColumn(voucher -> voucher.getValidFrom().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .setHeader("Valid From");

        grid.addColumn(voucher -> voucher.getValidUntil().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .setHeader("Valid Until");

        grid.addColumn(voucher -> voucher.getUsedAt() != null ?
                        voucher.getUsedAt().format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "Not Used")
                .setHeader("Used At");

        // Add customer name column
        grid.addColumn(voucher -> {
            Customer customer = voucher.getCustomer();
            return customer != null ? customer.getFirstName() + " " + customer.getLastName() : "";
        }).setHeader("Customer");

        // Add redeem button column
        grid.addComponentColumn(voucher -> {
            if (voucher.getUsedAt() == null &&
                    voucher.getValidFrom().isBefore(LocalDateTime.now()) &&
                    voucher.getValidUntil().isAfter(LocalDateTime.now())) {

                Button redeemButton = new Button("Redeem", e -> {
                    voucherService.redeemVoucher(voucher.getCode(), voucher.getCustomerId())
                            .ifPresentOrElse(
                                    v -> {
                                        Notification.show("Voucher redeemed successfully");
                                        refreshGrid();
                                    },
                                    () -> Notification.show("Failed to redeem voucher")
                            );
                });
                redeemButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

                return redeemButton;
            }
            return new HorizontalLayout(); // Empty for used or invalid vouchers
        }).setHeader("Actions").setAutoWidth(true);

        grid.setAllRowsVisible(true);
        refreshGrid();

        add(grid);
    }

    private void refreshGrid() {
        grid.setItems(voucherService.getAllVouchers());
    }
}
