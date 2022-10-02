package com.mckeydonelly.servletdungeoncrawler.servlet;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletRegistration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppConfigurationServletTest {
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ServletContext servletContext;
    @Mock
    private ServletRegistration.Dynamic servletRegistration;

    @Test
    void should_not_return_exceptions() {
        AppConfigurationServlet appConfigurationServlet = new AppConfigurationServlet();
        when(servletContextEvent.getServletContext()).thenReturn(servletContext);
        when(servletContext.addServlet(anyString(), any(Servlet.class))).thenReturn(servletRegistration);

        assertDoesNotThrow(() -> appConfigurationServlet.contextInitialized(servletContextEvent));
    }
}