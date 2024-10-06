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
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "attendId")
public class AttendInfoEntity {

    // attend idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attend_id", nullable = false)
    private Long attendId;

    // 참석 여부
    @Column(name = "attend", length = 1, nullable = false)
    @ColumnDefault("'N'")
    private Character attend;

    // 참석일
    @Column(name = "attend_date")
    private LocalDate attendDate;

    // 출석 시간
    @Column(name = "check_in_time")
    private LocalTime checkInTime;

    // 퇴실 시간
    @Column(name = "check_out_time")
    private LocalTime checkOutTime;

    // 지각 여부 Y/N
    @Column(name = "late_or_not", length = 1, nullable = false)
    @ColumnDefault("'N'")
    private Character lateOrNot;

    // 참석자 (fk)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    // 참석한 행사 (fk)
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private EventEntity event;

    public void updateAttendInfo(Character attend) {
        this.attend = attend;
    }
}