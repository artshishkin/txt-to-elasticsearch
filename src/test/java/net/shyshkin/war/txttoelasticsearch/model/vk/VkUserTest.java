package net.shyshkin.war.txttoelasticsearch.model.vk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class VkUserTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void objectMapperShouldMapMilitaryCorrectly() throws JsonProcessingException {
        //given
        String userJson = user01();

        //when
        var vkUser = objectMapper.readValue(userJson, VkUser.class);

        //then
        assertThat(vkUser.getMilitary())
                .hasSize(1)
                .satisfies(item -> assertAll(
                                () -> assertThat(item.getCountryId()).isEqualTo(16),
                                () -> assertThat(item.getUnit()).isEqualTo("201 военая база"),
                                () -> assertThat(item.getUnitId()).isEqualTo(37382),
                                () -> assertThat(item.getFrom()).isEqualTo(2013),
                                () -> assertThat(item.getUntil()).isEqualTo(2014)
                        ),
                        Index.atIndex(0));
    }

    @Test
    void objectMapperShouldMapRelativesCorrectly() throws JsonProcessingException {
        //given
        String userJson = user01();

        //when
        var vkUser = objectMapper.readValue(userJson, VkUser.class);

        //then
        assertThat(vkUser.getRelatives())
                .hasSize(6)
                .allSatisfy(relative -> assertAll(
                                () -> assertThat(relative.getType()).isEqualTo(Relative.Type.SIBLING),
                                () -> assertThat(relative.getId()).isNotNull()
                        )
                );
    }

    @Test
    void objectMapperShouldMapCareerCorrectly() throws JsonProcessingException {
        //given
        String userJson = user01();

        //when
        var vkUser = objectMapper.readValue(userJson, VkUser.class);

        //then
        assertThat(vkUser.getCareer())
                .hasSize(4)
                .allSatisfy(careerItem -> assertAll(
                                () -> assertThat(careerItem.getCityId()).isEqualTo(424),
                                () -> assertThat(careerItem.getCountryId()).isEqualTo(1),
                                () -> assertThat(careerItem.getCompany()).isIn("\"ЗАО\" РЗЗ", "самозанятый", "МУП РТС", "МУТП"),
                                () -> assertThat(careerItem.getPosition()).isEqualTo("Электрогазосварщик"),
                                () -> assertThat(careerItem.getFrom()).isIn(2012, 2014, 2015, 2018),
                                () -> assertThat(careerItem.getUntil()).isIn(2013, 2015, null, 2018)
                        )
                );
    }

    private static String user01() {
        return "{\n" +
                "      \"id\": 188222243,\n" +
                "      \"nickname\": \"\",\n" +
                "      \"domain\": \"bolshakovevgen\",\n" +
                "      \"bdate\": \"14.5.1994\",\n" +
                "      \"city\": {\n" +
                "        \"id\": 99,\n" +
                "        \"title\": \"Новосибирск\"\n" +
                "      },\n" +
                "      \"country\": {\n" +
                "        \"id\": 1,\n" +
                "        \"title\": \"Россия\"\n" +
                "      },\n" +
                "      \"has_photo\": 1,\n" +
                "      \"has_mobile\": 1,\n" +
                "      \"books\": \"\",\n" +
                "      \"quotes\": \"\",\n" +
                "      \"about\": \"\",\n" +
                "      \"movies\": \"\",\n" +
                "      \"activities\": \"\",\n" +
                "      \"music\": \"\",\n" +
                "      \"mobile_phone\": \"+79836078117\",\n" +
                "      \"home_phone\": \"\",\n" +
                "      \"site\": \"\",\n" +
                "      \"status\": \"Сварка аргоном услуги сварщика сантехника строительство тел: +7 983 607 81 17\",\n" +
                "      \"last_seen\": {\n" +
                "        \"platform\": 4,\n" +
                "        \"time\": 1664455749\n" +
                "      },\n" +
                "      \"followers_count\": 112,\n" +
                "      \"occupation\": {\n" +
                "        \"name\": \"самозанятый\",\n" +
                "        \"type\": \"work\"\n" +
                "      },\n" +
                "      \"career\": [\n" +
                "        {\n" +
                "          \"city_id\": 424,\n" +
                "          \"company\": \"\\\"ЗАО\\\" РЗЗ\",\n" +
                "          \"country_id\": 1,\n" +
                "          \"from\": 2015,\n" +
                "          \"position\": \"Электрогазосварщик\",\n" +
                "          \"until\": 2018\n" +
                "        },\n" +
                "        {\n" +
                "          \"city_id\": 424,\n" +
                "          \"company\": \"самозанятый\",\n" +
                "          \"country_id\": 1,\n" +
                "          \"from\": 2018,\n" +
                "          \"position\": \"Электрогазосварщик\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"city_id\": 424,\n" +
                "          \"company\": \"МУП РТС\",\n" +
                "          \"country_id\": 1,\n" +
                "          \"from\": 2014,\n" +
                "          \"position\": \"Электрогазосварщик\",\n" +
                "          \"until\": 2015\n" +
                "        },\n" +
                "        {\n" +
                "          \"city_id\": 424,\n" +
                "          \"company\": \"МУТП\",\n" +
                "          \"country_id\": 1,\n" +
                "          \"from\": 2012,\n" +
                "          \"position\": \"Электрогазосварщик\",\n" +
                "          \"until\": 2013\n" +
                "        }\n" +
                "      ],\n" +
                "      \"military\": [\n" +
                "        {\n" +
                "          \"country_id\": 16,\n" +
                "          \"unit\": \"201 военая база\",\n" +
                "          \"unit_id\": 37382,\n" +
                "          \"from\": 2013,\n" +
                "          \"until\": 2014\n" +
                "        }\n" +
                "      ],\n" +
                "      \"university\": 0,\n" +
                "      \"university_name\": \"\",\n" +
                "      \"faculty\": 0,\n" +
                "      \"faculty_name\": \"\",\n" +
                "      \"graduation\": 0,\n" +
                "      \"home_town\": \"СССР\",\n" +
                "      \"relation\": 4,\n" +
                "      \"personal\": {\n" +
                "        \"alcohol\": 5,\n" +
                "        \"inspired_by\": \"\",\n" +
                "        \"langs\": [\n" +
                "          \"Русский\"\n" +
                "        ],\n" +
                "        \"langs_full\": [\n" +
                "          {\n" +
                "            \"id\": 0,\n" +
                "            \"native_name\": \"Русский\"\n" +
                "          }\n" +
                "        ],\n" +
                "        \"life_main\": 0,\n" +
                "        \"people_main\": 0,\n" +
                "        \"political\": 1,\n" +
                "        \"religion\": \"атеист\",\n" +
                "        \"smoking\": 5\n" +
                "      },\n" +
                "      \"universities\": [],\n" +
                "      \"schools\": [\n" +
                "        {\n" +
                "          \"city\": 424,\n" +
                "          \"class\": \"б\",\n" +
                "          \"class_id\": 2,\n" +
                "          \"country\": 1,\n" +
                "          \"id\": \"17722\",\n" +
                "          \"name\": \"Гимназия №11 (бывш. шк. №11)\",\n" +
                "          \"year_from\": 2001,\n" +
                "          \"year_graduated\": 2010,\n" +
                "          \"year_to\": 2010\n" +
                "        },\n" +
                "        {\n" +
                "          \"city\": 424,\n" +
                "          \"class\": \"\",\n" +
                "          \"class_id\": 0,\n" +
                "          \"country\": 1,\n" +
                "          \"id\": \"279521\",\n" +
                "          \"name\": \"Профессиональный лицей № 47\",\n" +
                "          \"type\": 9,\n" +
                "          \"type_str\": \"Проф. лицей\",\n" +
                "          \"year_from\": 2010,\n" +
                "          \"year_to\": 2013,\n" +
                "          \"speciality\": \"электрогазосваршик\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"relatives\": [\n" +
                "        {\n" +
                "          \"type\": \"sibling\",\n" +
                "          \"id\": 54203246\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"sibling\",\n" +
                "          \"id\": 119993085\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"sibling\",\n" +
                "          \"id\": 85076925\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"sibling\",\n" +
                "          \"id\": 201103408\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"sibling\",\n" +
                "          \"id\": 361488591\n" +
                "        },\n" +
                "        {\n" +
                "          \"type\": \"sibling\",\n" +
                "          \"id\": 392317269\n" +
                "        }\n" +
                "      ],\n" +
                "      \"counters\": {\n" +
                "        \"albums\": 1,\n" +
                "        \"audios\": 0,\n" +
                "        \"followers\": 112,\n" +
                "        \"friends\": 81,\n" +
                "        \"gifts\": 35,\n" +
                "        \"online_friends\": 4,\n" +
                "        \"pages\": 160,\n" +
                "        \"photos\": 217,\n" +
                "        \"subscriptions\": 0,\n" +
                "        \"videos\": 317,\n" +
                "        \"mutual_friends\": 0,\n" +
                "        \"clips_followers\": 193\n" +
                "      },\n" +
                "      \"sex\": 2,\n" +
                "      \"first_name\": \"Евгений\",\n" +
                "      \"last_name\": \"Большаков\",\n" +
                "      \"can_access_closed\": true,\n" +
                "      \"is_closed\": false\n" +
                "    }";
    }

}