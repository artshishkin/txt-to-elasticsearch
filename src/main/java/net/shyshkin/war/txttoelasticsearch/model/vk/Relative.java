package net.shyshkin.war.txttoelasticsearch.model.vk;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Relative {

    private Integer id; // — идентификатор пользователя;
    private String name; // — имя родственника (если родственник не является пользователем ВКонтакте, то предыдущее значение id возвращено не будет);
    private Type type; // — тип родственной связи. Возможные значения:

    public enum Type {
        @JsonProperty("child")
        CHILD,          //сын/дочь;
        @JsonProperty("sibling")
        SIBLING,        //брат/сестра;
        @JsonProperty("parent")
        PARENT,         //отец/мать;
        @JsonProperty("grandparent")
        GRANDPARENT,    //дедушка/бабушка;
        @JsonProperty("grandchild")
        GRANDCHILD      //внук/внучка.
    }
}
