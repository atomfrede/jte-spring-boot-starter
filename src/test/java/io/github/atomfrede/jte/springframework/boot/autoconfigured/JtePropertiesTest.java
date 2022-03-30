package io.github.atomfrede.jte.springframework.boot.autoconfigured;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.core.env.Environment;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JtePropertiesTest {

    private Environment environment = mock(Environment.class);
    private JteProperties subject;

    @BeforeEach
    void before() {
        subject = new JteProperties();
    }

    @Test
    void shouldDetermineIfProductionIsEnabled() {

        subject.setProductionProfileName("prod");

        when(environment.getActiveProfiles()).thenReturn(new String[]{"default", "dev", "cloud"});
        assertThat(subject.isProductionEnabled(environment)).isFalse();

        when(environment.getActiveProfiles()).thenReturn(new String[]{"default", "dev", "cloud", "prod"});
        assertThat(subject.isProductionEnabled(environment)).isTrue();
    }
}
