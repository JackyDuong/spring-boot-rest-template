package ch.duong.jmd.#APP_ABBREVIATION.utils;

import ch.duong.jmd.#APP_ABBREVIATION.entity.enumeration.AppConfigurationKey;
import ch.duong.jmd.#APP_ABBREVIATION.repository.AppConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppConfigUtils {
    private final AppConfigurationRepository repository;

    @Autowired
    public AppConfigUtils(AppConfigurationRepository repository) {
        this.repository = repository;
    }

    public int getIntValue(AppConfigurationKey key) {
        return getIntValue(key, -1);
    }

    public int getIntValue(final AppConfigurationKey key, final int ifEmpty) {
        var optValue = repository.findByKey(key.name());

        if (optValue.isPresent()) {
            try {
                return Integer.parseInt(optValue.get().getValue());
            } catch (Exception ignored) {
            }
        }

        return ifEmpty;
    }

    public String getStringValue(AppConfigurationKey key) {
        return getStringValue(key, null);
    }

    public String getStringValue(final AppConfigurationKey key, final String ifEmpty) {
        var optValue = repository.findByKey(key.name());

        if (optValue.isPresent()) {
            return optValue.get().getValue();
        }

        return ifEmpty;
    }
}
