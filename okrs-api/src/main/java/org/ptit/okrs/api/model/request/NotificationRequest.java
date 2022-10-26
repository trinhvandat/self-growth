package org.ptit.okrs.api.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NotificationRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String content;
}
