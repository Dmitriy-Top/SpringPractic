package ru.epam.spring.hometask.CLI;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

/** */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PromptProvider extends DefaultPromptProvider {

    /**
     * Getter for the Prompt.
     *
     * @return String
     */
    @Override
    public final String getPrompt() {
        return "$";
    }

    /**
     * Getter for the Providername.
     *
     * @return String
     */
    @Override
    public String getProviderName() {
        return "default prompt provider";
    }

}
