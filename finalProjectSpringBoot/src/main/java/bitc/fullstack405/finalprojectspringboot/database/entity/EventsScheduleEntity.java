package bitc.fullstack405.finalprojectspringboot.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "event_schedule")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "scheduleId")
public class EventsScheduleEntity {

    // schedule_id idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    // 행사 시작 시간
    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    // 행사 종료 시간
    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    // 해당 회차 행사 일자
    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    // 행사 (fk)
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private EventEntity event;

    @OneToMany(mappedBy = "eventsSchedule", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<AttendInfoEntity> attendInfoList = new ArrayList<>();
}
