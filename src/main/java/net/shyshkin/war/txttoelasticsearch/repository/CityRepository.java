package net.shyshkin.war.txttoelasticsearch.repository;

import net.shyshkin.war.txttoelasticsearch.model.vk.City;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;
import reactor.core.publisher.Mono;

public interface CityRepository extends ReactiveElasticsearchRepository<City, Integer> {

    Mono<City> findFirstByTitleOrAreaOrRegion(String title, String area, String region);

    @Query("{\"bool\":{\"should\":[{\"match\":{\"title\":\"?0\"}},{\"match\":{\"area\":\"?0\"}},{\"match\":{\"region\":\"?0\"}}]}}")
    Mono<City> findCityMatchingAddress(String address);

}
