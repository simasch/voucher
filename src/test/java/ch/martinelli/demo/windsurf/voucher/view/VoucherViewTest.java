package ch.martinelli.demo.windsurf.voucher.view;

import ch.martinelli.demo.windsurf.voucher.entity.Voucher;
import com.github.mvysny.kaributesting.v10.GridKt;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import org.junit.jupiter.api.Test;

import static com.github.mvysny.kaributesting.v10.LocatorJ._get;
import static org.assertj.core.api.Assertions.assertThat;

class VoucherViewTest extends KaribuTest {

    @Test
    void shouldDisplayVouchers() {
        UI.getCurrent().navigate(VoucherView.class);

        Grid<Voucher> grid = _get(Grid.class);
        assertThat(GridKt._size(grid)).isEqualTo(3);
    }

}
