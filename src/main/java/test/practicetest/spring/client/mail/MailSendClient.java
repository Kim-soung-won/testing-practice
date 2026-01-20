package test.practicetest.spring.client.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailSendClient {
    public boolean sendEmail(String fromMail, String email, String title, String content) {
        log.info("메일 전송 완료! {} {} {} {}", fromMail, email, title, content);
        throw new IllegalArgumentException("메일 전송 실패!");
    }
}
