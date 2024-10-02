package bitc.fullstack405.finalprojectspringboot.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "notiId")
public class NotificationEntity {

    // notification idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_id", nullable = false)
    private Long notiId;

    // 공지 제목
    @Column(name = "noti_title", length = 100, nullable = false)
    private String notiTitle;

    // 공지 내용
    @Column(name = "noti_content", length = 500, nullable = false)
    private String notiContent;

    // 공지 등록일
    @Column(name = "noti_date", nullable = false)
    private LocalDateTime notiDate;

    // fk
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;
}