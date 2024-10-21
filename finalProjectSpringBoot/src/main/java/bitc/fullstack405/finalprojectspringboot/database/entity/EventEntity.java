package bitc.fullstack405.finalprojectspringboot.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "event")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "eventId")
public class EventEntity {

    // event idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    // 행사 제목
    @Column(name = "event_title", length = 100, nullable = false)
    private String eventTitle;

    // 행사 상세 내용
    @Column(name = "event_content", length = 500, nullable = false)
    private String eventContent;

    // 행사 승인 여부 (1: 대기, 2: 승인, 3: 거부)
    @Column(name = "event_accept", nullable = false)
    @ColumnDefault("1")
    private int eventAccept;

    // 행사 글 등록일
    @CreatedDate
    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    // 행사 포스터(이름 + 타입)
    @Column(name = "event_poster", length = 500)
    private String eventPoster;

    // 행사 승인일자
    @Column(name = "accepted_date")
    private LocalDate acceptedDate;

    // 게시일 - 회원에게 행사글이 보일 날짜 (행사 시작일로부터 2주 전)
    @Column(name = "visible_date", nullable = false)
    private LocalDate visibleDate;

    // 게시 마감일 - 회원에게 행사글이 보이지 않게 할 날짜 (행사 시작일로부터 1주 전)
    @Column(name = "invisible_date", nullable = false)
    private LocalDate invisibleDate;

    // 행사 참여 마감 인원 (최대 인원)
    @Column(name = "max_people", nullable = false)
    private int maxPeople;

    // 참여 마감 여부 Y/N
    @Column(name = "is_registration_open", length = 1, nullable = true)
    @ColumnDefault("'Y'")
    private Character isRegistrationOpen;

    // 행사 글 등록자 (fk)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity posterUser;

    // 행사 승인자 (fk)
    @ManyToOne
    @JoinColumn(name = "approver")
    @ToString.Exclude
    private UserEntity approver;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<EventScheduleEntity> scheduleList = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<EventAppEntity> eventAppList = new ArrayList<>();

}