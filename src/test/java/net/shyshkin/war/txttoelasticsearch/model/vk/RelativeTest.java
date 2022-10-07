package net.shyshkin.war.txttoelasticsearch.model.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class RelativeTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void objectMapperShouldMapPropertiesCorrectly() throws JsonProcessingException {
        //given
        String jsonRelative = "{\n" +
                "   \"type\": \"sibling\",\n" +
                "   \"name\": \"Peter\",\n" +
                "   \"id\": 54203246\n" +
                "}";

        //when
        Relative relative = objectMapper.readValue(jsonRelative, Relative.class);

        //then
        assertAll(
                () -> assertThat(relative.getId()).isEqualTo(54203246),
                () -> assertThat(relative.getType()).isEqualTo(Relative.Type.SIBLING),
                () -> assertThat(relative.getName()).isEqualTo("Peter")
        );
    }
}