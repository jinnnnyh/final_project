package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import lombok.Builder;
import lombok.Getter;

import javax.annotation.processing.Generated;

@Getter
@Builder
public class AttendInfoDTO {
  private Long userId;
  private String userAccount;
  private String name;
  private String userPhone;
  private String userDepart;
  private Role role;
}
