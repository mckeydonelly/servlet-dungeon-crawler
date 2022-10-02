package com.mckeydonelly.servletdungeoncrawler.servlet;

import com.mckeydonelly.servletdungeoncrawler.user.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StartServletTest {
    @Mock
    private HttpServletRequest request;
    @Spy
    private HttpServletResponse response;
    @Mock
    private UserManager userManager;

    @Test
    void should_create_user_and_redirect() throws IOException {
        StartServlet startServlet = new StartServlet(userManager);

        startServlet.doPost(request, response);

        verify(userManager).createUser(request);
        verify(response).sendRedirect(anyString());
    }
}