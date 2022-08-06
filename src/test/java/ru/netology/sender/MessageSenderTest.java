package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.Map;


class MessageSenderTest {
    public static final String RUSSIAN_IP = "172.0.32.11";
    public static final String USA_IP = "96.44.183.149";
    public static final String GERMANY_IP = "101.44.183.149";
    public static final String BRAZIL_IP = "96.44.183.149";
    private final GeoService geoService = Mockito.mock(GeoService.class);
    private final LocalizationService localizationService = Mockito.mock(LocalizationService.class);
    private final MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

    @BeforeEach
    void setUp() {
        Mockito.when(geoService.byIp(RUSSIAN_IP)).thenReturn(new Location("Krasnoyarsk", Country.RUSSIA, null, 0));
        Mockito.when(geoService.byIp(USA_IP)).thenReturn(new Location("Michigan", Country.USA, null, 0));
        Mockito.when(geoService.byIp(GERMANY_IP)).thenReturn(new Location("Hamburg", Country.GERMANY, null, 0));
        Mockito.when(geoService.byIp(BRAZIL_IP)).thenReturn(new Location("Fortaleza", Country.BRAZIL, null, 0));
        Mockito.when(localizationService.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        Mockito.when(localizationService.locale(Country.USA))
                .thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.GERMANY))
                .thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.BRAZIL))
                .thenReturn("Welcome");
    }


    @ParameterizedTest
    /*@CsvSource({
            RUSSIAN_IP + "," + "Добро пожаловать",
            USA_IP + "," + "Welcome",
            GERMANY_IP + "," + "Welcome",
            BRAZIL_IP + "," + "Welcome",
    })*/
    @ValueSource(strings = {RUSSIAN_IP, USA_IP, GERMANY_IP, BRAZIL_IP})
    public void testLanguageMessage(String ipForCheck) {
        String messageForChek = messageSender.send(Map.of(MessageSenderImpl.IP_ADDRESS_HEADER, ipForCheck));
        boolean needLanguage;
        if (ipForCheck.equals(RUSSIAN_IP)) {
            needLanguage = checkLanguage(messageForChek, 'a', 'z');
        } else {
            needLanguage = checkLanguage(messageForChek, 'а', 'я');
        }
        Assertions.assertTrue(needLanguage);
    }

    private boolean checkLanguage(String controlMessage, char start, char end) {
        for (char i = start; i <= end; i++) {
            if (controlMessage.contains(String.valueOf(i).toLowerCase())) {
                return false;
            }
        }
        return true;
    }
}