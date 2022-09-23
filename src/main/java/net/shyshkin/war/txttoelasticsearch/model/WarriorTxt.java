package net.shyshkin.war.txttoelasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WarriorTxt {

    private String fullName;
    private String birthDate;
    private String address;
    private Long districtNumber;
    private String districtName;

}
