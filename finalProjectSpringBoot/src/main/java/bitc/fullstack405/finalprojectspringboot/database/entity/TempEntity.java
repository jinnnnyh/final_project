package bitc.fullstack405.finalprojectspringboot.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "test")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TempEntity {

  @Id
  @Column(name = "test_id", nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreatedDate
  @Column
  private LocalDateTime createdDate;

  @LastModifiedDate
  @Column
  private LocalDateTime updatedDate;
}
