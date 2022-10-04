package net.shyshkin.war.txttoelasticsearch.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(indexName = "warriors")
public class WarriorDoc {

    @Id
    private String id;
    private String fullName;
    @JsonProperty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.uuuu")
    @Field(type = FieldType.Date, format = {}, pattern = "dd.MM.uuuu")
    private LocalDate birthDate;
    private String address;
    private Long districtNumber;
    private String districtName;

}
