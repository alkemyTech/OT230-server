package com.alkemy.ong.models.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@SQLDelete(sql = "UPDATE users SET soft_delete = true WHERE id = ?")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "soft_delete = false")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty(message = "the first name can't be null")
    @NotBlank(message = "the first name can't be blank")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @NotEmpty(message = "the last name can't be null")
    @NotBlank(message = "the last name can't be blank")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Email(message = "the email is not valid")
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    @NotEmpty(message = "the password can't be null")
    @Column(nullable = false)
    private String password;

    private String photo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<RoleEntity> roleId;

    @Column(name = "timeStamp")
    private Timestamp timestamp;

    @Column(name = "soft_delete",columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean softDelete = false;
}
