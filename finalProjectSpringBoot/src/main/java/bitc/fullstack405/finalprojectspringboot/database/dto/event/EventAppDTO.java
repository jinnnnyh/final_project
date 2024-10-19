package bitc.fullstack405.finalprojectspringboot.database.dto.event;

import bitc.fullstack405.finalprojectspringboot.database.entity.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class EventAppDTO {
  private Long userId;
  private String userAccount;
  private String name;
  private String userPhone;
  private String userDepart;
  private Long appId;
  private Character eventComp;
  private Role role;
  private List<AttendInfoDTO> attendInfoDTOList;
}
