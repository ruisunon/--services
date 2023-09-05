package org.testcontainers.utility;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;

public class DefaultImageNameSubstitutorTest {

    public static final DockerImageName ORIGINAL_IMAGE = DockerImageName.parse("foo");

    public static final DockerImageName SUBSTITUTE_IMAGE = DockerImageName.parse("bar");

    private ConfigurationFileImageNameSubstitutor underTest;

    @Rule
    public MockTestcontainersConfigurationRule config = new MockTestcontainersConfigurationRule();

    @Before
    public void setUp() {
        underTest = new ConfigurationFileImageNameSubstitutor(TestcontainersConfiguration.getInstance());
    }

    @Test
    public void testConfigurationLookup() {
        Mockito
            .doReturn(SUBSTITUTE_IMAGE)
            .when(TestcontainersConfiguration.getInstance())
            .getConfiguredSubstituteImage(eq(ORIGINAL_IMAGE));

        final DockerImageName substitute = underTest.apply(ORIGINAL_IMAGE);

        assertThat(substitute).as("match is found").isEqualTo(SUBSTITUTE_IMAGE);
        assertThat(substitute.isCompatibleWith(ORIGINAL_IMAGE)).as("compatibility is automatically set").isTrue();
    }
}
