package com.nixmash.rabbitmq.common;


import com.google.inject.Binder;
import com.google.inject.Module;
import com.nixmash.rabbitmq.common.service.ReservationService;
import com.nixmash.rabbitmq.common.service.ReservationServiceImpl;
import com.nixmash.rabbitmq.common.ui.CommonUI;
import com.nixmash.rabbitmq.common.ui.ICommonUI;
import io.bootique.BQRuntime;
import io.bootique.test.junit.BQTestFactory;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class CommonTestBase implements Module {

    private static final Logger logger = LoggerFactory.getLogger(CommonTestBase.class);

    protected static ReservationService reservationService;
    protected static CommonUI commonUI;

    @ClassRule
    public static BQTestFactory TEST_FACTORY = new BQTestFactory();

    @BeforeClass
    public static void setupDB() throws SQLException {
        BQRuntime runtime = TEST_FACTORY
                .app("--config=classpath:test.yml")
                .module(CommonTestBase.class)
                .autoLoadModules()
                .createRuntime();

        reservationService = runtime.getInstance(ReservationServiceImpl.class);
        commonUI= runtime.getInstance(CommonUI.class);
    }

    @Test
    public void msgGoAway() {
        assertNotNull(reservationService);
    }


    @Override
    public void configure(Binder binder) {
        binder.bind(ReservationService.class).to(ReservationServiceImpl.class);
        binder.bind(ICommonUI.class).to(CommonUI.class);
    }
}
