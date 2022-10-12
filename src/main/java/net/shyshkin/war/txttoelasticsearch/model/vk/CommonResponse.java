package net.shyshkin.war.txttoelasticsearch.model.vk;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public abstract class CommonResponse {

    private JsonNode error;

}
