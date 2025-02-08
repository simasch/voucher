package ch.martinelli.demo.windsurf.voucher.layout;

import ch.martinelli.demo.windsurf.voucher.view.CreateVoucherView;
import ch.martinelli.demo.windsurf.voucher.view.VoucherView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Voucher Management");
        logo.addClassNames(
            LumoUtility.FontSize.LARGE,
            LumoUtility.Margin.MEDIUM
        );

        addToNavbar(new DrawerToggle(), logo);
    }

    private void createDrawer() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Vouchers", VoucherView.class, VaadinIcon.TICKET.create()));
        nav.addItem(new SideNavItem("Create Voucher", CreateVoucherView.class, VaadinIcon.PLUS.create()));

        addToDrawer(nav);
    }
}
