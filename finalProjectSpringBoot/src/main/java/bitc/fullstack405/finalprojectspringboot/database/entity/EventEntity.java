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
@Builder(toBuilder = true)  // toBuilder 옵션 추가
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

    // 행사 일자
    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    // 행사 승인 여부
    @Column(name = "event_accept", length = 1, nullable = false)
    @ColumnDefault("'N'")
    private Character eventAccept;

    // 행사 글 등록일
    @CreatedDate
    @Column(name = "upload_date", nullable = false)
    private LocalDateTime uploadDate;

    // 행사 포스터
    @Column(name = "event_poster", length = 500)
    private String eventPoster;

    // fk
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<AttendInfoEntity> attendInfoList = new ArrayList<>();

    public void updateEvent(String eventTitle, String eventContent, LocalDate eventDate, String eventPoster) {
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventDate = eventDate;
        this.eventPoster = eventPoster;
    }

    public void acceptEvent() {
        this.eventAccept = 'Y';
    }

    public void rejectEvent() {
        this.eventAccept = 'N';
    }
}