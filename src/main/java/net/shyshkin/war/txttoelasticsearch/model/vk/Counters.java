package net.shyshkin.war.txttoelasticsearch.model.vk;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Counters {

    private Integer albums; // — количество фотоальбомов;
    private Integer videos; // — количество видеозаписей;
    private Integer audios; // — количество аудиозаписей;
    private Integer photos; // — количество фотографий;
    private Integer notes; // — количество заметок;
    private Integer friends; // — количество друзей;
    private Integer groups; // — количество сообществ;
    private Integer gifts;
    private Integer subscriptions;
    private Integer clips_followers;
    private Integer online_friends; // — количество друзей онлайн;
    private Integer mutual_friends; // — количество общих друзей;
    private Integer user_videos; // — количество видеозаписей с пользователем;
    private Integer followers; // — количество подписчиков;
    private Integer pages; // — количество объектов в блоке «Интересные страницы».

}
