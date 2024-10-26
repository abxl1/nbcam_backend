package com.sparta.trelloproject.domain.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequest {

  private Long workspaceId;
  private String title;
  private String explaination;
}
