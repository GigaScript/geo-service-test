package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class GeoServiceTest {
    private final GeoService geoService = new GeoServiceImpl();

    @ParameterizedTest
    @CsvSource({
            GeoServiceImpl.MOSCOW_IP + "," + "RUSSIA",
            GeoServiceImpl.NEW_YORK_IP + "," + "USA",
    })
    public void byIp(String ip, String country) {
        Assertions.assertEquals(geoService.byIp(ip).getCountry().toString(), country);
    }

    @ParameterizedTest
    @ValueSource(strings = {GeoServiceImpl.LOCALHOST})
    public void byIpNullCountry(String ip) {
        Assertions.assertNull(geoService.byIp(ip).getCountry());
    }

    @ParameterizedTest
    @ValueSource(strings = {"GeoServiceImpl.LOCALHOST"})
    public void byIpWrongIp(String ip) {
        Assertions.assertNull(geoService.byIp(ip));
    }

    @Test
    void byCoordinates() {
        Assertions.assertThrows(RuntimeException.class,()->geoService.byCoordinates(53.1,55.3));
    }
}