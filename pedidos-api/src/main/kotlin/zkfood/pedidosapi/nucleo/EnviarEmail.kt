package zkfood.pedidosapi.nucleo

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.SimpleMailMessage
import org.springframework.stereotype.Service

@Service
class EnviarEmail(private val javaMailSender: JavaMailSender) {
    fun enviarEmail(destinatario: String, assunto: String, corpo: String) {
        val mensagem = SimpleMailMessage();
        mensagem.setTo(destinatario);
        mensagem.setSubject(assunto);
        mensagem.setText(corpo);

        javaMailSender.send(mensagem);
    }
}