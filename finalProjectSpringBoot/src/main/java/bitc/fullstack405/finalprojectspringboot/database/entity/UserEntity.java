package bitc.fullstack405.finalprojectspringboot.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
//public class UserEntity implements UserDetails {
public class UserEntity {

    // user idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    // user 이름
    @Getter
    @Column(name = "user_name", length = 45, nullable = false)
    private String name;

    // user 전화번호
    @Column(name = "user_phone", length = 45, nullable = false)
    private String userPhone;

    // user 소속 기관
    @Column(name = "user_depart", length = 50, nullable = false)
    private String userDepart;

    // user 등급(권한)
    @Enumerated(EnumType.STRING)
    @Column(name = "user_permission", nullable = false)
    private Role role;

    // user 아이디
    @Column(name = "user_account", length = 45, nullable = false, unique = true)
    private String userAccount;

    // user 비밀번호
    @Column(name = "user_pw", length = 200, nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<NotificationEntity> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "posterUser", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<EventEntity> postedEventList = new ArrayList<>();

    @OneToMany(mappedBy = "approver", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<EventEntity> approvedEventList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<AttendInfoEntity> attendInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<EventAppEntity> attendAppList = new ArrayList<>();

    // 스프링 시큐리티 관련
//    // 사용자 권한 출력
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton(new SimpleGrantedAuthority(role.name()));
//    }
//
//    // 사용자 ID 출력
//    @Override
//    public String getUsername() {
//        return userAccount;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}