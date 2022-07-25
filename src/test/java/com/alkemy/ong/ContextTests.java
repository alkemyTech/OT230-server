package com.alkemy.ong;


import com.alkemy.ong.auth.utility.RoleEnum;
import com.alkemy.ong.context.InMemoryUserDetails;
import com.alkemy.ong.models.entity.RoleEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.request.AuthRequest;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = InMemoryUserDetails.class,
        properties = "spring.main.allow-bean-definition-overriding=true")
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class ContextTests {

    private static final String ADMIN_EMAIL = "admin@authtest.com";
    private static final String USER_EMAIL = "user@authtest.com";
    private static final String PASSWORD = "12345678";

    protected static final String BEARER = "Bearer ";

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    public MockMvc mockMvc;


    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;



    @Before()
    public void setup() {
        createUserData();
        createRoles();
    }

    private void createUserData() {
        if (userRepository.findByEmail(ADMIN_EMAIL) == null) {
            saveAdminUser();
        }
        if (userRepository.findByEmail(USER_EMAIL) == null) {
            saveStandardUser();
        }
    }
    private void createRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.saveAll(List.of(
                    buildRole(RoleEnum.USER),
                    buildRole(RoleEnum.ADMIN)));
        }
    }
    private void saveStandardUser() {
        userRepository.save(buildUser(
                "Mariano",
                "Martinez",
                "12345678",
                USER_EMAIL,
                RoleEnum.USER.getFullRoleName()));
    }
    protected UserEntity saveAdminRandomEmail(){
        UserEntity user = buildUser(
                "Roberto",
                "Gómez",
                "12345678",
                generateEmail(),
                RoleEnum.ADMIN.getFullRoleName()
        );
        return userRepository.save(user);
    }
    protected UserEntity saveStandardUserRandomEmail(){
        UserEntity user = buildUser(
                "Roberto",
                "Gómez",
                "12345678",
                generateEmail(),
                RoleEnum.USER.getFullRoleName()
        );
        return userRepository.save(user);
    }

    private void saveAdminUser() {
        userRepository.save(buildUser(
                "Felipe",
                "Pettinato",
                "12345678",
                ADMIN_EMAIL,
                RoleEnum.ADMIN.getSimpleRoleName()));
    }


    protected UserEntity buildUser(String firstName, String lastName, String password, String email, String role) {
        return UserEntity.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .roleId(roleRepository.findByName(role))
                .build();
    }

    private RoleEntity buildRole(RoleEnum roleEnum) {
        return RoleEntity.builder()
                .name(roleEnum.getFullRoleName())
                .description(roleEnum.getSimpleRoleName())
                .build();
    }
    protected String getAuthorizationTokenForAdminUser() throws Exception {
        return getAuthorizationTokenForUser(ADMIN_EMAIL);
    }
    protected String getAuthorizationTokenForAdminUser(String email) throws Exception {
        return getAuthorizationTokenForUser(email);
    }

    protected String getAuthorizationTokenForStandardUser() throws Exception {
        return getAuthorizationTokenForUser(USER_EMAIL);
    }
    protected String getAuthorizationTokenForStandardUser(String email) throws Exception {
        return getAuthorizationTokenForUser(email);
    }

    private String getAuthorizationTokenForUser(String email) throws Exception {
        String content = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(AuthRequest.builder()
                        .email(email)
                        .password(PASSWORD)
                        .build()))).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return JsonPath.read(content, "$.token");
    }
    //Generate Email Random
    protected String getUniqueId() {
        return String.format("%s_%s", UUID.randomUUID().toString().substring(0, 5), System.currentTimeMillis() / 1000);
    }
    protected String generateEmail() {
        return String.format("%s@%s", getUniqueId(), "test.com");
    }





}
