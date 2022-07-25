package com.alkemy.ong.auth.config.seeder;

import com.alkemy.ong.auth.utility.RoleEnum;
import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.models.entity.RoleEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Profile("test")
public class DataBaseSeederTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MembersRepository membersRepository;
    @Autowired
    private PasswordEncoder encoder;


    //Users
    private static final String PASSWORD = "12345678";
    private static final String ADMIN_EMAIL = "admin@authtest.com";
    private static final String USER_EMAIL = "user@authtest.com";
    private static final String firstNameUser[] = {"Patricia", "Ronald", "Carlos", "Nathaniel", "Caitlin", "Juan", "Thanos", "Will"};
    private static final String lastNameUser[] = {"Brett", "Kathleen", "Brandi", "Craig", "Katrina", "Peralta", "Smith", "Doll"};

    //Activities
    private static final String name[] = {"Apoyo Escolar para el nivel Primario", "Apoyo Escolar Nivel Secundaria", "Tutorías"};


    @EventListener
    public void seed(ContextRefreshedEvent event) throws IOException {
        if (roleRepository.findAll().isEmpty()) {
            createRoles();
        }
        if (userRepository.findAll().isEmpty()) {
            createUsers();
        }

        if(membersRepository.findAll().isEmpty())
            createMembers();
    }

    private void createRoles() {
        createRole(1L, RoleEnum.ADMIN);
        createRole(2L, RoleEnum.USER);
    }

    private void createUsers() {
        createUsers(RoleEnum.ADMIN, ADMIN_EMAIL);
        createUsers(RoleEnum.USER, USER_EMAIL);
    }

    private void createUsers(RoleEnum applicationRole, String email) {

        userRepository.save(UserEntity.builder()
                .firstName("Roberto")
                .lastName("Franco")
                .email(email)
                .password(encoder.encode(PASSWORD))
                .roleId(createListRole(applicationRole))
                .build());

    }

    private Set<RoleEntity> createListRole(RoleEnum applicationRole) {
        Set<RoleEntity> roles = roleRepository.findByName(applicationRole.getFullRoleName());
        return roles;
    }

    private void createRole(Long id, RoleEnum applicationRole) {
        RoleEntity role = new RoleEntity();
        role.setId(id);
        role.setName(applicationRole.getFullRoleName());
        role.setDescription(applicationRole.name());
        roleRepository.save(role);
    }

    private void createMembers() {
        for (int i = 0; i < 20; i++) {
            membersRepository.save(
               MemberEntity.builder()
                  .name("Member: " + i)
                  .facebookUrl("facebook: " + i)
                  .instagramUrl("instagram: " + i)
                  .linkedinUrl("linkedIn: " + i)
                  .image("image: " + i)
                  .description("description: " + i)
                  .build()
            );
        }
    }
}