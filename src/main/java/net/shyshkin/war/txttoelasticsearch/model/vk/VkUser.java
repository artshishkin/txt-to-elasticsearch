package net.shyshkin.war.txttoelasticsearch.model.vk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.List;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkUser {

    private Long id;
    private String firstName;
    private String lastName;
    @JsonProperty("bdate")
    private String birthDate;
    private City city;
    private Country country;
    private LastSeen lastSeen;

    private String deactivated;
    @JsonProperty("is_closed")
    private Boolean isClosed;
    private String about;
    private String activities;
    private String books;
    private List<CareerItem> career;
    private JsonNode connections;
    private JsonNode contacts;
    private Counters counters; //Поле возвращается только в методе users.getSearchName при запросе информации об одном пользователе, с передачей пользовательского access_token.
    private String domain;
    private JsonNode education;
    private JsonNode exports;
    private Integer followersCount;
    private Integer hasMobile;
    private Integer hasPhoto;
    private String homeTown;
    private List<MilitaryItem> military;
    private String movies;
    private String music;
    private String mobilePhone;
    private String homePhone;
    private String nickname;
    private Occupation occupation;
    private JsonNode personal;
    private String quotes;
    private List<Relative> relatives;
    private Integer relation;
    private JsonNode schools;
    private Integer sex;
    private String site;
    private String status;
    private JsonNode universities;

}
