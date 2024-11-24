package org.example.avanzoapi.domain.entitites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "token_table")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID tokenId;

    private String content;

    @Column(name = "timeStamp", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date generatedTimeStamp;

    private Boolean isTokenActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User userToken;

    public Token(String content, User tokenUser) {
        super();
        this.content = content;
        this.userToken = tokenUser;
        this.generatedTimeStamp = Date.from(Instant.now());
        this.isTokenActive = true;
    }
}
