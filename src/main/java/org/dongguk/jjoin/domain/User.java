package org.dongguk.jjoin.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dongguk.jjoin.domain.type.EUserRole;
import org.dongguk.jjoin.domain.type.ELoginProvider;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "provider", nullable = false)
    @Enumerated(EnumType.STRING)
    private ELoginProvider provider;

    @Column(name ="serial_id")
    private String serialId;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<ClubMember> groups = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Schedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Album> albums = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    List<Enrollment> enrollments = new ArrayList<>();

    @Builder
    public User(ELoginProvider provider, String serialId, String password, String name,
                String introduction, String email, EUserRole role) {
        this.provider = provider;
        this.serialId = serialId;
        this.password = password;
        this.name = name;
        this.introduction = introduction;
        this.email = email;
        this.role = role;
        this.createdDate = Timestamp.valueOf(LocalDateTime.now());
        this.isLogin = true;
        this.refreshToken = null;
        this.deviceToken = null;
    }
}
