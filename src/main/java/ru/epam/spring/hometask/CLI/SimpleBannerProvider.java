package ru.epam.spring.hometask.CLI;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitrii_Topolnik on 7/13/2017.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleBannerProvider extends DefaultBannerProvider {

    public String getBanner() {
        StringBuffer buf = new StringBuffer();
        buf.append("=======================================")
                .append(OsUtils.LINE_SEPARATOR);
        buf.append("*          Spring HW #1             *")
                .append(OsUtils.LINE_SEPARATOR);
        buf.append("=======================================")
                .append(OsUtils.LINE_SEPARATOR);
        return buf.toString();
    }

    public String getVersion() {
        return "1.0";
    }

    public String getWelcomeMessage() {
        return "Welcome to application for managing a movie theater";
    }

    public String getProviderName() {
        return "Dmitriy Top";
    }
}
