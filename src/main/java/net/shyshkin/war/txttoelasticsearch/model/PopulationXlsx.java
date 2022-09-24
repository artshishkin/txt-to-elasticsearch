package net.shyshkin.war.txttoelasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PopulationXlsx {

    private String sheetName;
    private Integer age;
    private Long allMenWomen;
    private Long allMen;
    private Long allWomen;
    private Long cityMenWomen;
    private Long cityMen;
    private Long cityWomen;
    private Long countryMenWomen;
    private Long countryMen;
    private Long countryWomen;

}
