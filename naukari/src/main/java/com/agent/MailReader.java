package com.agent;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.search.FlagTerm;
import java.util.Properties;

public class MailReader {
    public static void pollAndAutoReply() throws Exception {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        Session session = Session.getInstance(props);
        Store store = session.getStore("imaps");
        store.connect(System.getenv("IMAP_HOST"), System.getenv("IMAP_USER"), System.getenv("IMAP_PASS"));
        Folder inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        Message[] msgs = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
        for (Message m : msgs) {
            String subj = m.getSubject() == null ? "" : m.getSubject();
            String from = ((InternetAddress)m.getFrom()[0]).getAddress();
            String content = m.getContent().toString();

            if (subj.toLowerCase().contains("naukri") || subj.toLowerCase().contains("application") || content.toLowerCase().contains("interview")) {
                String replyBody = "Hi,\\n\\nThanks for reaching out. I have received your message and will reply shortly.\\n\\nRegards,\\nVinay";
                Message reply = (MimeMessage)m.reply(false);
                reply.setFrom(new InternetAddress(System.getenv("IMAP_USER")));
                reply.setText(replyBody);
                Transport.send(reply);
            }

            m.setFlag(Flags.Flag.SEEN, true);
        }

        inbox.close(false);
        store.close();
    }
}
