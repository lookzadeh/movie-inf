package io.rasha.movie.info.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ApiKeyAuthenticationFilter extends OncePerRequestFilter {

  private final String headerName = "Movie-Edit-API-KEY";

  @Value("${api-key}")
  private String movie_insert_APIKey;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String apiKey = request.getHeader(headerName);

    // Authenticate the request
    if (isValidApiKey(apiKey)) {
      filterChain.doFilter(request, response);
    } else {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter().write("Invalid API key");
    }
  }

  private boolean isValidApiKey(String apiKey) {
    // Validate the API key (e.g., check against a database or a predefined list)
    // Return true if the API key is valid, false otherwise
    return (apiKey != null && apiKey.equals(movie_insert_APIKey));
  }
}

