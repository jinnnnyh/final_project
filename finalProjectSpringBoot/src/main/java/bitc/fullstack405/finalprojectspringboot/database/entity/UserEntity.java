package bitc.fullstack405.finalprojectspringboot.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
public class UserEntity implements UserDetails {

    // user idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    // user 이름
    @Column(name = "user_name", length = 45, nullable = false)
    private String userName;

    // user 전화번호
    @Column(name = "user_phone", length = 45, nullable = false)
    private String userPhone;

    // user 등급
    @Enumerated(EnumType.STRING)
    @Column(name = "user_permission", nullable = false)
    private Role role;

    // user 아이디
    @Column(name = "user_account", length = 45, nullable = false, unique = true)
    private String userAccount;

    // user 비밀번호
    @Column(name = "user_pw", length = 45, nullable = false)
    private String userPw;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<NotificationEntity> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<EventEntity> eventList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<AttendInfoEntity> attendInfoList = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return userAccount;
    }

    @Override
    public String getPassword() {
        return userPw;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}