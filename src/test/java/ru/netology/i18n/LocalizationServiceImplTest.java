package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.entity.Country;

class LocalizationServiceImplTest {
    private final LocalizationService localizationService = new LocalizationServiceImpl();
    private final Country[] countries = Country.values();

    @Test
    void locale() {
        for (int i = 0; i < countries.length; i++) {
            String controlMessageText;
            if (countries[i].toString().equals("RUSSIA")) {
                controlMessageText = "Добро пожаловать";
            } else {
                controlMessageText = "Welcome";
            }
            String messageText = localizationService.locale(countries[i]);
            Assertions.assertEquals(controlMessageText, messageText);
        }
    }
}