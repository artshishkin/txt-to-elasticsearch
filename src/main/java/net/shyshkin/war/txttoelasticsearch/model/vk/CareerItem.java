package net.shyshkin.war.txttoelasticsearch.model.vk;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CareerItem {

    private Integer groupId;  // — идентификатор сообщества (если доступно, иначе company);
    private String company;  // — название компании (если доступно, иначе group_id);
    private Integer countryId;  // — идентификатор страны;
    private Integer cityId;  // — идентификатор города (если доступно, иначе city_name);
    private String city_name;  // — название города (если доступно, иначе city_id);
    private Integer from;  // — год начала работы;
    private Integer until;  // — год окончания работы;
    private String position;  // — должность.

}
