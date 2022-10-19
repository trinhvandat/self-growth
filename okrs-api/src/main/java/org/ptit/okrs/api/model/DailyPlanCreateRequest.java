package org.ptit.okrs.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class DailyPlanCreateRequest {
  @NotBlank
  private String title;

  private String description;

  private String keyResultId;
}