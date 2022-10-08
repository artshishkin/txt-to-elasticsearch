package net.shyshkin.war.txttoelasticsearch.model.vk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Occupation {

    private Type type; // — тип.
    private Integer id; // — идентификатор школы, вуза, сообщества компании (в которой пользователь работает);
    private String name; // — название школы, вуза или места работы;

    private Integer graduateYear;
    private Integer countryId;
    private Integer cityId;

    public enum Type {
        @JsonProperty("work")
        WORK, // — работа;
        @JsonProperty("school")
        SCHOOL, // — среднее образование;
        @JsonProperty("university")
        UNIVERSITY // — высшее образование.
    }
}
