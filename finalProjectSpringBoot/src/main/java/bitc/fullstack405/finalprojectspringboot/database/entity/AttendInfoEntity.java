package bitc.fullstack405.finalprojectspringboot.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    // fk
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private UserEntity user;

    // fk
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @ToString.Exclude
    private EventEntity event;

    public void updateAttendInfo(Character attend) {
        this.attend = attend;
    }
}