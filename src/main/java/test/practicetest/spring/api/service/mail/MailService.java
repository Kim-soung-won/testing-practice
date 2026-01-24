package test.practicetest.spring.api.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import test.practicetest.spring.client.mail.MailSendClient;
import test.practicetest.spring.domain.history.mail.MailSendHistory;
import test.practicetest.spring.domain.history.mail.MailSendHistoryRepository;

@Service
@RequiredArgsConstructor
public class MailService {
    private final MailSendClient mailSendClient;
    private final MailSendHistoryRepository mailSendHistoryRepository;
    public boolean sendMail(String fromMail, String email, String title, String content) {
        boolean result = mailSendClient.sendEmail(fromMail, email, title, content);
        if(result) {
            mailSendHistoryRepository.save(MailSendHistory.builder()
                    .fromMail(fromMail)
                    .toMail(email)
                    .title(title)
                    .content(content)
                    .build());
            return true;
        }
        return false;
    }
}
