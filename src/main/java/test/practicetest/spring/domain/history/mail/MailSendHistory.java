package test.practicetest.spring.domain.history.mail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import test.practicetest.spring.domain.base.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MailSendHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fromMail;
    private String toMail;
    private String title;
    private String content;

    @Builder
    public MailSendHistory(String fromMail, String toMail, String title, String content) {
        this.fromMail = fromMail;
        this.toMail = toMail;
        this.title = title;
        this.content = content;
    }
}
