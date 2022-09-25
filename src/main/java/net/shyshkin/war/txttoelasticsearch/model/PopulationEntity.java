package net.shyshkin.war.txttoelasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopulationEntity {

    private Long id;
    private String regionId;
    private Integer age;
    private Long men;
    private Long women;

}
