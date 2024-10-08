package bitc.fullstack405.finalprojectspringboot.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attend_info")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "attendId")
public class AttendInfoEntity {

    // attend idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attend_id", nullable = false)
    private Long attendId;

    // 참석일
    @Column(name = "attend_date")
    private LocalDate attendDate;

    // 출석 시간
    @Column(name = "check_in_time")
    private LocalTime checkInTime;

    // 퇴실 시간
    @Column(name = "check_out_time")
    private LocalTime checkOutTime;

    // 당일 수료 여부 Y/N
    @Column(name = "attend_comp", length = 1, nullable = false)
    @ColumnDefault("'N'")
    private Character lateOrNot;

    // 참석자 (fk)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    // 참석한 행사 회차 (fk)
    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    @ToString.Exclude
    private EventsScheduleEntity eventsSchedule;
}