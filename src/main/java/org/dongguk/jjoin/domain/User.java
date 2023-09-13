package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dongguk.jjoin.domain.type.EUserRole;
import org.dongguk.jjoin.domain.type.ELoginProvider;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private ELoginProvider provider;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private EUserRole role;

    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "is_login", nullable = false)
    private Boolean isLogin;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "device_token")
    private String deviceToken;

    //--------------------------------------------------------
    @OneToMany(mappedBy = "Group_member", fetch = FetchType.LAZY)
    List<Group_member> group_members = new ArrayList<>();
}
