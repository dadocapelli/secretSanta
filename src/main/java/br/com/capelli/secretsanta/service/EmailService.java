package br.com.capelli.secretsanta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.capelli.secretsanta.modelo.Caracteristica;
import br.com.capelli.secretsanta.modelo.Resultado;
import br.com.capelli.secretsanta.modelo.Sorteio;

@Stateless
public class EmailService {

    private Session mailSession;
    private Properties props;

    private static final Logger logger = Logger.getLogger(EmailService.class);
    private static final String CONTENT_TYPE_HTML = "text/html; charset=UTF-8";

    public void sendMail(
            @Observes(during = TransactionPhase.AFTER_SUCCESS) Sorteio sorteio) {

        for (Resultado resultado : sorteio.getResultados()) {

            try {
                logger.info(
                        "# Envio email para " + resultado.getEu().getNome());
                if (StringUtils.isBlank(resultado.getEu().getEmail())) {
                    logger.error("Campo email vazio.");
                    continue;
                }

                if (mailSession == null) {
                    props = getDefaultProporties();
                    mailSession = Session.getInstance(props);
                }

                Message message = new MimeMessage(mailSession);

                message.setFrom(new InternetAddress(
                        props.getProperty("mail.smtp.user")));
                message.setSubject("AMIGO 2015: " + resultado.getEu().getNome()
                        + " tirou...");

                List<InternetAddress> mailsTo = new ArrayList<InternetAddress>();
                mailsTo.add(new InternetAddress(resultado.getEu().getEmail()));

                message.setRecipients(RecipientType.TO,
                        mailsTo.toArray(new Address[mailsTo.size()]));

                Multipart mp = new MimeMultipart("mixed");

                MimeBodyPart mbpBody = new MimeBodyPart();
                mbpBody.setContent(corpoEmail(resultado), CONTENT_TYPE_HTML);
                mp.addBodyPart(mbpBody);

                message.setContent(mp);

                Transport.send(message);

            } catch (Exception e) {
                logger.error("Falha para enviar email para "
                        + resultado.getEu().getNome(), e);
            } finally {
                logger.info("Email enviado");
            }

        }
    }

    private String corpoEmail(Resultado resultado) {

        Caracteristica caracteristica = resultado.getMeuAmigoSecreto()
                .getMeuAmigoDados();

        StringBuilder message = new StringBuilder();
        message.append("<b>").append(resultado.toString()).append("</b>");
        message.append("<br/><br/>");
        message.append("O valor do presente será em média R$100,00");
        message.append("<br/><br/>");
        message.append("Dados do seu amigo(a):").append("<br/>");
        message.append("- idade:").append(caracteristica.getIdade())
                .append("<br/>");
        message.append("- sapato:").append(caracteristica.getSapato())
                .append("<br/>");
        message.append("- camisa:").append(caracteristica.getCamisa())
                .append("<br/>");
        message.append("- calça:").append(caracteristica.getCalca())
                .append("<br/><br/>");

        message.append("Não conte para ninguém!").append("<br/>");

        return message.toString();
    }

    public Properties getDefaultProporties() {
        Properties props = new Properties();
        props.put("mail.host", "icarta.pro");
        props.put("mail.smtp.host", "smtp.icarta.pro");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.user", "bernardo@idtrust.com.br");
        props.put("mail.smtp.pass", "icarta1234");

        return props;
    }

}
