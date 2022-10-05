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

    public String getLastFirstName() {
        String fullName = getFullName();
        String[] nameParts = fullName.trim()
                .replaceAll("\\s+"," ")
                .split(" ");
        if (nameParts.length < 3) return fullName;
        return nameParts[0] + " " + nameParts[1];
    }

}
