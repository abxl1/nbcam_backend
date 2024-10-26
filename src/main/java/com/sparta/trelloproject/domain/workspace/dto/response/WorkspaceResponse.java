package com.sparta.trelloproject.domain.workspace.dto.response;

import com.sparta.trelloproject.domain.workspace.entity.Workspace;
import lombok.Getter;

@Getter
public class WorkspaceResponse {

  private Long id;
  private String title;
  private String explaination;

  public WorkspaceResponse(Workspace workspace) {
    this.id = workspace.getId();
    this.title = workspace.getTitle();
    this.explaination = workspace.getExplaination();
  }
}
