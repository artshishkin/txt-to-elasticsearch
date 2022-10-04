package net.shyshkin.war.txttoelasticsearch.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "cities")
public class City {

    @Id
    private Integer id;
    private String title;
    private String area;
    private String region;

}
