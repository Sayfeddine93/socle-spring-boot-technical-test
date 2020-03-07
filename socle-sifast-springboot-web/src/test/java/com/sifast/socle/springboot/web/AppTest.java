package com.sifast.socle.springboot.web;

import com.sifast.socle.springboot.dao.RoleDao;
import com.sifast.socle.springboot.dao.UserDao;
import com.sifast.socle.springboot.model.Role;
import com.sifast.socle.springboot.model.User;
import com.sifast.socle.springboot.service.services.impl.RoleServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class AppTest {

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleServiceImpl roleServiceImpl;

    @Autowired
    private UserDao userDao;

    @Autowired
    AuthenticationManager authenticationManager;

    private Role role;
    private Role secondRole;
    private Role thirdRole;
    List<Role> rolesDb;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        role = new Role();
        role.setDescription("test01");
        role.setDesignation("ROLE_TEST");
        // role 2
        secondRole = new Role();
        secondRole.setDescription("test02");
        secondRole.setDesignation("ROLE_TEST");
        // role 3
        thirdRole = new Role();
        thirdRole.setDescription("test03");
        thirdRole.setDesignation("ROLE_TEST");

        rolesDb = new ArrayList<>();
    }

    @Test
    public void saveRoleTest() {
        when(roleDao.save(role)).thenReturn(role);
        Role roleDb = roleServiceImpl.save(role);
        assertEquals(role.getDescription(), roleDb.getDescription());
        assertEquals(role.getDesignation(), roleDb.getDesignation());
    }

    @Test
    public void isAllRoleSizeEqualsThree() {
        rolesDb.add(roleServiceImpl.save(role));
        rolesDb.add(roleServiceImpl.save(secondRole));
        rolesDb.add(roleServiceImpl.save(thirdRole));
        when(roleDao.findAll()).thenReturn(rolesDb);
        List<Role> roles = roleServiceImpl.findAll();
        assertEquals(rolesDb.size(), roles.size());
    }

    @Test
    public void isAdminUsernameExist() {
        List<User> users = userDao.findAll();
        assertTrue(users.stream().anyMatch(user -> "admin".equals(user.getUsername())));
    }

    @Test
    public void isFindAllRolesWebServiceCallHttpStatusCodeAccepted() {
        OAuth2RestTemplate template = getSecuredOAuth2RestTemplate();
        HttpEntity<Role> request = new HttpEntity<>(role);
        assertTrue(template
                .exchange("http://localhost:8090/api/roles/", HttpMethod.GET,
                        request,
                        new ParameterizedTypeReference<List<Role>>() {
                        }).getStatusCode().is2xxSuccessful());
    }

    @Test
    public void isSaveRoleWebServiceCallHttpStatusCodeAccepted() {
        role.setDesignation(role.getDesignation()+ LocalDateTime.now().toString());
        OAuth2RestTemplate template = getSecuredOAuth2RestTemplate();
        HttpEntity<Role> request = new HttpEntity<>(role);
        assertTrue(template
                .exchange("http://localhost:8090/api/roles", HttpMethod.POST,
                        request,
                        new ParameterizedTypeReference<Role>() {
                        }).getStatusCode().is2xxSuccessful());
    }

    @Test
    public void findRoleByDesignationWebServiceCall_isHttpStatusCode_Accepted() {
        OAuth2RestTemplate template = getSecuredOAuth2RestTemplate();
        HttpEntity<Role> request = new HttpEntity<>(role);
        assertTrue(template
                .exchange("http://localhost:8090/api/roles/ROLE_ADMIN", HttpMethod.GET,
                        request,
                        new ParameterizedTypeReference<Role>() {
                        }).getStatusCode().is2xxSuccessful());
    }

    private OAuth2RestTemplate getSecuredOAuth2RestTemplate() {
        ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
        details.setClientId("client-id");
        details.setClientSecret("client-secret");
        details.setAccessTokenUri("http://localhost:8090/oauth/token");
        details.setScope(Arrays.asList("read write"));
        details.setAuthenticationScheme(AuthenticationScheme.form);
        details.setGrantType("password");
        details.setUsername("admin");
        details.setPassword("secret123");
        details.setScope(Arrays.asList("trust"));
        OAuth2RestTemplate template = new OAuth2RestTemplate(details);
        return template;
    }
}