package bitc.fullstack405.finalprojectspringboot.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "event_app")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "appId")
public class EventAppEntity {

    // event_app idx
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "app_id", nullable = false)
    private Long appId;

    // 행사 수료 여부 Y/N
    @Column(name = "event_comp", length = 1, nullable = false)
    @ColumnDefault("'N'")
    private Character eventComp;

    // 행사 신청일
    @CreatedDate
    @Column(name = "app_date", nullable = false)
    private LocalDate appDate;

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
}
