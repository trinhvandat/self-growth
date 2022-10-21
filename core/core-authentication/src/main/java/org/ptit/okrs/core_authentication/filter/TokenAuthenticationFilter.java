package org.ptit.okrs.core_authentication.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ptit.okrs.core_authentication.service.AuthTokenService;
import org.ptit.okrs.core_authentication.service.AuthUserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

  private final AuthTokenService authTokenService;
  private final AuthUserService authUserService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {
    log.info("(doFilterInternal)request: {}, response: {}, filterChain: {}", request, response, filterChain);
    final String accessToken = request.getHeader("Authorization");

    if (Objects.isNull(accessToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    if (!"Bearer ".startsWith(accessToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    var jwtToken = accessToken.substring(7);
    String userId;
    try {
      userId = authTokenService.getSubjectFromAccessToken(jwtToken);
    } catch (Exception ex) {
      log.error("(doFilterInternal)get subject token failed");
      filterChain.doFilter(request, response);
      return;
    }

    if (Objects.nonNull(userId) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
      var user = authUserService.findById(userId);
      if (authTokenService.validateAccessToken(jwtToken, userId)) {
        var usernamePasswordAuthToken = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
