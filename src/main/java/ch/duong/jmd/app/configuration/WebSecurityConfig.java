package ch.duong.jmd.#APP_ABBREVIATION.configuration;

import ch.duong.jmd.#APP_ABBREVIATION.entity.enumeration.AppConfigurationKey;
import ch.duong.jmd.#APP_ABBREVIATION.security.AuthenticationExceptionEntryPoint;
import ch.duong.jmd.#APP_ABBREVIATION.security.JwtAuthenticationFilter;
import ch.duong.jmd.#APP_ABBREVIATION.utils.AppConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

   private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

   @Value("${admin.username}")
   private String adminUsername;
   @Value("${admin.password}")
   private String adminPassword;
   @Value("${admin.role}")
   private String adminRole;

   @Value("${ldap.url1}")
   private String ldapUrl1;
   @Value("${ldap.url2}")
   private String ldapUrl2;
   @Value("${ldap.baseDN}")
   private String ldapBaseDN;
   @Value("${ldap.connect.timeout}")
   private String ldapTimeout;

   private final AppConfigUtils appConfigUtils;

   private final AuthenticationExceptionEntryPoint unauthorizedHandler;

   public WebSecurityConfig(AppConfigUtils appConfigUtils, AuthenticationExceptionEntryPoint unauthorizedHandler) {
      this.unauthorizedHandler = unauthorizedHandler;
      this.appConfigUtils = appConfigUtils;
   }

   @Bean
   public InMemoryUserDetailsManager userDetailsService() {
      UserDetails user = User
              .withUsername(adminUsername)
              .password(adminPassword)
              .roles(adminRole)
              .build();
      return new InMemoryUserDetailsManager(user);
   }

   @Bean
   AuthenticationManager ldapAuthenticationManager() {
      LdapBindAuthenticationManagerFactory factory =
              new LdapBindAuthenticationManagerFactory(contextSourceIdm());
      factory.setUserDnPatterns("uid={0}");
      return factory.createAuthenticationManager();
   }

   @Bean
   public JwtAuthenticationFilter authenticationTokenFilterBean() {
      return new JwtAuthenticationFilter();
   }

   @Bean
   public LdapContextSource contextSourceIdm() {
      final LdapContextSource contextSource = new LdapContextSource();
      final String[] urls = {
              appConfigUtils.getStringValue(AppConfigurationKey.LDAP_URL_1, ldapUrl1),
              appConfigUtils.getStringValue(AppConfigurationKey.LDAP_URL_2, ldapUrl2)
      };

      logger.info("ldap url 1: {} - ldap url 2: {}",
              appConfigUtils.getStringValue(AppConfigurationKey.LDAP_URL_1, ldapUrl1),
              appConfigUtils.getStringValue(AppConfigurationKey.LDAP_URL_2, ldapUrl2));

      contextSource.setUrls(urls);
      contextSource.setBase(appConfigUtils.getStringValue(AppConfigurationKey.LDAP_BASE_DN, ldapBaseDN));

      Map<String, Object> environment = new HashMap<>();
      environment.put("com.sun.jndi.ldap.connect.timeout", appConfigUtils.getStringValue(AppConfigurationKey.LDAP_CONNECT_TIMEOUT, ldapTimeout));
      contextSource.setBaseEnvironmentProperties(environment);
      return contextSource;
   }

   @Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      return http
              .cors()
              .and()
              .csrf().disable()
              .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
              .exceptionHandling()
              .authenticationEntryPoint(unauthorizedHandler)
              .and()
              .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .authorizeHttpRequests((authz) -> authz
                      .requestMatchers("/auth/login", "/auth/autologin", "/actuator/*").permitAll()
                      .anyRequest().authenticated())
              .build();
   }

   @Bean
   CorsConfigurationSource corsConfigurationSource() {
      var configuration = new CorsConfiguration();

      configuration.setAllowedOriginPatterns(List.of("*"));
      configuration.setAllowCredentials(true);
      configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

      configuration.addAllowedMethod(HttpMethod.GET);
      configuration.addAllowedMethod(HttpMethod.HEAD);
      configuration.addAllowedMethod(HttpMethod.POST);
      configuration.addAllowedMethod(HttpMethod.PUT);
      configuration.addAllowedMethod(HttpMethod.DELETE);
      configuration.addAllowedMethod(HttpMethod.PATCH);

      var source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", configuration);
      return source;
   }

   @Bean
   public BCryptPasswordEncoder encoder() {
      return new BCryptPasswordEncoder();
   }
}
