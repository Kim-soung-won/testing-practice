package test.practicetest.spring.api.service.mail;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import test.practicetest.spring.client.mail.MailSendClient;
import test.practicetest.spring.domain.history.mail.MailSendHistory;
import test.practicetest.spring.domain.history.mail.MailSendHistoryRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;


// Mock 객체 생성 Spring Bean 주입 없이 직접 생성
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MailServiceTest {

    @Mock
    private MailSendClient mailSendClient;
    @Mock
    private MailSendHistoryRepository mailSendHistoryRepository;

    // MailService에 Mock 객체 주입
    // 생성자를 보고 주입할 Mock 객체를 판단
    @InjectMocks
    private MailService mailService;

    @DisplayName("메일 전송 테스트")
    @Test
    void sendMail(){
        //given
        // 가짜 객체가 특정 상황에서 어떤 값을 리턴하도록 설정 하기 위해 Mockito.when().thenReturn() 사용
        // any() 메서드를 통해 어떤 값이 오더라도 상관없도록 설정
        // 여기서는 mailSendClient.sendEmail() 메서드가 어떤 값이 오더라도 true를 리턴하도록 설정
        Mockito.when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        Mockito.when(mailSendHistoryRepository.save(any(MailSendHistory.class)))
                .thenReturn(MailSendHistory.builder().build());

        //when
        boolean result = mailService.sendMail("","","","");

        //then
        assertThat(result).isTrue();

        // 해당 Mock 객체의 save 메서드가 1회 호출되었는지 검증
        // sendMail 메서드가 실행되면 실행되기 때문에, 이후에 검증할 수 있다.
        Mockito.verify(mailSendHistoryRepository, Mockito.times(1)).save(any(MailSendHistory.class));
    }

}