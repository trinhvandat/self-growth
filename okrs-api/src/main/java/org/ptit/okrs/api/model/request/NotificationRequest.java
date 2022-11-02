package org.ptit.okrs.api.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class NotificationRequest {

    private String id;

    @NotBlank
    private String content;

    @NotBlank
    private String userId;

}
