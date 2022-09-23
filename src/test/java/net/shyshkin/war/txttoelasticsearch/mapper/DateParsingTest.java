package net.shyshkin.war.txttoelasticsearch.mapper;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

class DateParsingTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("d.M.uuuu");

    @ParameterizedTest
    @ValueSource(strings = {
            "02.07.1983", "2.7.1983", "2.07.1983", "02.7.1983"
    })
    void parseDate(String dateString) {

        //when
        LocalDate date = LocalDate.parse(dateString, FORMATTER);

        //then
        assertThat(date).isNotNull();
    }
}