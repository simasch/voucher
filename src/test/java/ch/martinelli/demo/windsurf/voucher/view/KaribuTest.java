package ch.martinelli.demo.windsurf.voucher.view;

import com.github.mvysny.kaributesting.v10.MockVaadin;
import com.github.mvysny.kaributesting.v10.Routes;
import com.github.mvysny.kaributesting.v10.spring.MockSpringServlet;
import com.vaadin.flow.component.UI;

import ch.martinelli.demo.windsurf.voucher.TestcontainersConfiguration;

import java.util.Locale;

import kotlin.jvm.functions.Function0;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
public abstract class KaribuTest {

    protected static Routes routes;

    @Autowired
    protected ApplicationContext ctx;

    @BeforeAll
    public static void discoverRoutes() {
        Locale.setDefault(Locale.GERMAN);
        routes = new Routes().autoDiscoverViews("ch.martinelli.demo.windsurf.voucher.view");
    }

    @BeforeEach
    public void setup() {
        final Function0<UI> uiFactory = UI::new;
        MockVaadin.setup(uiFactory, new MockSpringServlet(routes, ctx, uiFactory));
    }

    @AfterEach
    public void tearDown() {
        MockVaadin.tearDown();
    }
}
