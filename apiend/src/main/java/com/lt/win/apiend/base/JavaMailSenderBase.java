//package com.lt.win.apiend.base;
//
//import com.lt.win.service.cache.redis.ConfigCache;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.Contract;
//import org.jetbrains.annotations.NotNull;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.MailException;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
///**
// * 邮箱发送服务
// *
// * @author david
// */
//@Service
//@Slf4j
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//public class JavaMailSenderBase {
//    private final JavaMailSender javaMailSender;
//    private final ConfigCache configCache;
//
//    private static String formatInfo() {
//        return "\n===================\t[ Ses START SUCCESS ]\t========================================================="
//                + "\nFrom---------------\t{}"
//                + "\nTo-----------------\t{}"
//                + "\nSubject------------\t{}"
//                + "\nText---------------\t{}"
//                + "\n===================\t[ Ses  END  SUCCESS ]\t=========================================================\n";
//    }
//
//    /**
//     * 异常日志输出格式
//     *
//     * @return 异常格式
//     */
//    @NotNull
//    @Contract(pure = true)
//    private static String formatError() {
//        return "\n===================\t[ {} START FAILURE ]\t========================================================="
//                + "\nFrom---------------\t{}"
//                + "\nTo-----------------\t{}"
//                + "\nSubject------------\t{}"
//                + "\nText---------------\t{}"
//                + "\nExceptionInfo------\t{}"
//                + "\n===================\t[ {}  END  FAILURE ]\t=========================================================\n";
//    }
//
//    public void send(String code, String address) {
//        var config = configCache.getSesConfig();
//        var text = config.getContext().replace("%s", code);
//        try {
//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setTo(address);
//            simpleMailMessage.setFrom(config.getFrom());
//            simpleMailMessage.setSubject(config.getSubject());
//            simpleMailMessage.setText(text);
//            javaMailSender.send(simpleMailMessage);
//            log.info(formatInfo(), config.getFrom(), address, config.getSubject(), text);
//        } catch (MailException e) {
//            log.info(formatError(), "SES", config.getFrom(), address, config.getSubject(), text, e.getMessage(), "SES");
//            throw new RuntimeException(e);
//        }
//    }
//}
