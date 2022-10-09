package net.shyshkin.war.txttoelasticsearch.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.shyshkin.war.txttoelasticsearch.model.vk.City;
import net.shyshkin.war.txttoelasticsearch.model.vk.VkUser;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(indexName = "warrior-account")
public class WarriorAccount extends WarriorDoc {

    private City city;
    private List<VkUser> accounts;

}
