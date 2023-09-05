package org.testcontainers.jdbc.mssqlserver;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.testcontainers.jdbc.AbstractJDBCDriverTest;

import java.util.Arrays;
import java.util.EnumSet;

@RunWith(Parameterized.class)
public class MSSQLServerJDBCDriverTest extends AbstractJDBCDriverTest {

    @Parameterized.Parameters(name = "{index} - {0}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(
            new Object[][] {
                {
                    "jdbc:tc:sqlserver:2017-CU12://hostname:hostport;databaseName=databasename",
                    EnumSet.noneOf(Options.class),
                },
            }
        );
    }
}
