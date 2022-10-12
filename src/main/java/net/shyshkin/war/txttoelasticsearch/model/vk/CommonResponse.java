package net.shyshkin.war.txttoelasticsearch.model.vk;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public abstract class CommonResponse {

    private JsonNode error;
    private JsonNode executeErrors;

}
