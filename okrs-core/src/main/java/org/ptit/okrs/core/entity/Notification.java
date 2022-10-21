package org.ptit.okrs.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;

    @Column(name = "content")
    private String content;

    private User user;

    @AllArgsConstructor(staticName = "of")
    @Data
    @NoArgsConstructor
    protected static class User {
        private String userId;
    }
}
