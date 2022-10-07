package net.shyshkin.war.txttoelasticsearch.model.vk;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MilitaryItem {

    private String unit; // — номер части;
    private Integer unitId; // — идентификатор части в базе данных;
    private Integer countryId; // — идентификатор страны, в которой находится часть;
    private Integer from; // — год начала службы;
    private Integer until; // — год окончания службы

}
