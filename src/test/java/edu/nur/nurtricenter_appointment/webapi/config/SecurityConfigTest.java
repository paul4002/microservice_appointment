package edu.nur.nurtricenter_appointment.webapi.config;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@ExtendWith(MockitoExtension.class)
@DisplayName("SecurityConfig Tests")
class SecurityConfigTest {

	private SecurityConfig securityConfig;

	@BeforeEach
	void setUp() {
		securityConfig = new SecurityConfig();
	}

	// ===== JwtAuthenticationConverter Tests =====

	@Test
	@DisplayName("Should create JwtAuthenticationConverter bean")
	void testJwtAuthenticationConverterBeanCreation() {
		// Arrange & Act
		JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();

		// Assert
		assertNotNull(converter, "JwtAuthenticationConverter should not be null");
		assertTrue(converter instanceof JwtAuthenticationConverter,
				"Should be instance of JwtAuthenticationConverter");
	}

	@Test
	@DisplayName("Should extract roles from realm_access claim")
	void testExtractRolesFromRealmAccessClaim() {
		// Arrange
		JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();
		Map<String, Object> realmAccess = new HashMap<>();
		realmAccess.put("roles", Arrays.asList("admin", "user", "manager"));
		Jwt jwt = createJwtWithClaims(realmAccess);

		// Act
		Collection<GrantedAuthority> authorities = converter.convert(jwt).getAuthorities();

		// Assert
		assertNotNull(authorities, "Authorities should not be null");
		assertEquals(3, authorities.size(), "Should have 3 authorities");
		assertTrue(authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(auth -> auth.equals("ROLE_admin")), "Should contain ROLE_admin");
		assertTrue(authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(auth -> auth.equals("ROLE_user")), "Should contain ROLE_user");
		assertTrue(authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(auth -> auth.equals("ROLE_manager")), "Should contain ROLE_manager");
	}

	@Test
	@DisplayName("Should add ROLE_ prefix to extracted roles")
	void testRolePrefixConversion() {
		// Arrange
		JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();
		Map<String, Object> realmAccess = new HashMap<>();
		realmAccess.put("roles", Arrays.asList("guest", "premium"));
		Jwt jwt = createJwtWithClaims(realmAccess);

		// Act
		Collection<GrantedAuthority> authorities = converter.convert(jwt).getAuthorities();

		// Assert
		assertEquals(2, authorities.size(), "Should have 2 authorities");
		authorities.forEach(auth ->
			assertTrue(auth.getAuthority().startsWith("ROLE_"),
				"All authorities should have ROLE_ prefix")
		);
	}

	@Test
	@DisplayName("Should return empty list when realm_access is null")
	void testHandleNullRealmAccess() {
		// Arrange
		JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();
		Jwt jwt = createJwtWithClaims(null);

		// Act
		Collection<GrantedAuthority> authorities = converter.convert(jwt).getAuthorities();

		// Assert
		assertNotNull(authorities, "Authorities should not be null");
		assertTrue(authorities.isEmpty(), "Should return empty list when realm_access is null");
	}

	@Test
	@DisplayName("Should return empty list when realm_access roles are null")
	void testHandleNullRolesInRealmAccess() {
		// Arrange
		JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();
		Map<String, Object> realmAccess = new HashMap<>();
		realmAccess.put("roles", null);
		Jwt jwt = createJwtWithClaims(realmAccess);

		// Act
		Collection<GrantedAuthority> authorities = converter.convert(jwt).getAuthorities();

		// Assert
		assertNotNull(authorities, "Authorities should not be null");
		assertTrue(authorities.isEmpty(), "Should return empty list when roles are null");
	}

	@Test
	@DisplayName("Should return empty list when realm_access claim is missing")
	void testHandleMissingRealmAccessClaim() {
		// Arrange
		JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();
		Map<String, Object> realmAccess = new HashMap<>();
		Jwt jwt = createJwtWithClaims(realmAccess);

		// Act
		Collection<GrantedAuthority> authorities = converter.convert(jwt).getAuthorities();

		// Assert
		assertNotNull(authorities, "Authorities should not be null");
		assertTrue(authorities.isEmpty(), "Should return empty list when realm_access is missing");
	}

	@Test
	@DisplayName("Should handle single role correctly")
	void testHandleSingleRole() {
		// Arrange
		JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();
		Map<String, Object> realmAccess = new HashMap<>();
		realmAccess.put("roles", Arrays.asList("admin"));
		Jwt jwt = createJwtWithClaims(realmAccess);

		// Act
		Collection<GrantedAuthority> authorities = converter.convert(jwt).getAuthorities();

		// Assert
		assertEquals(1, authorities.size(), "Should have 1 authority");
		assertEquals("ROLE_admin", authorities.iterator().next().getAuthority(),
				"Should be ROLE_admin");
	}

	@Test
	@DisplayName("Should handle empty roles list correctly")
	void testHandleEmptyRolesList() {
		// Arrange
		JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();
		Map<String, Object> realmAccess = new HashMap<>();
		realmAccess.put("roles", Collections.emptyList());
		Jwt jwt = createJwtWithClaims(realmAccess);

		// Act
		Collection<GrantedAuthority> authorities = converter.convert(jwt).getAuthorities();

		// Assert
		assertNotNull(authorities, "Authorities should not be null");
		assertTrue(authorities.isEmpty(), "Should return empty list for empty roles");
	}

	@Test
	@DisplayName("Should handle multiple roles with special characters")
	void testHandleRolesWithSpecialCharacters() {
		// Arrange
		JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();
		Map<String, Object> realmAccess = new HashMap<>();
		realmAccess.put("roles", Arrays.asList("admin-super", "user_read", "manager.write"));
		Jwt jwt = createJwtWithClaims(realmAccess);

		// Act
		Collection<GrantedAuthority> authorities = converter.convert(jwt).getAuthorities();

		// Assert
		assertEquals(3, authorities.size(), "Should have 3 authorities");
		assertTrue(authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(auth -> auth.equals("ROLE_admin-super")), "Should handle hyphens");
		assertTrue(authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(auth -> auth.equals("ROLE_user_read")), "Should handle underscores");
		assertTrue(authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(auth -> auth.equals("ROLE_manager.write")), "Should handle dots");
	}

	@Test
	@DisplayName("Should preserve role order from JWT claim")
	void testPreserveRoleOrder() {
		// Arrange
		JwtAuthenticationConverter converter = securityConfig.jwtAuthenticationConverter();
		Map<String, Object> realmAccess = new HashMap<>();
		List<String> orderedRoles = Arrays.asList("first", "second", "third");
		realmAccess.put("roles", orderedRoles);
		Jwt jwt = createJwtWithClaims(realmAccess);

		// Act
		Collection<GrantedAuthority> authorities = converter.convert(jwt).getAuthorities();

		// Assert
		List<String> authorityStrings = authorities.stream()
				.map(GrantedAuthority::getAuthority)
				.toList();
		assertEquals(3, authorityStrings.size(), "Should have 3 authorities");
		assertEquals("ROLE_first", authorityStrings.get(0), "First role should be ROLE_first");
		assertEquals("ROLE_second", authorityStrings.get(1), "Second role should be ROLE_second");
		assertEquals("ROLE_third", authorityStrings.get(2), "Third role should be ROLE_third");
	}

	// ===== Helper Methods =====

	private Jwt createJwtWithClaims(Map<String, Object> realmAccess) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", "test-subject");

		if (realmAccess != null) {
			claims.put("realm_access", realmAccess);
		}

		return Jwt.withTokenValue("test-token")
				.header("alg", "HS256")
				.claims(c -> c.putAll(claims))
				.build();
	}
}
