package net.shyshkin.war.txttoelasticsearch.repository;

import net.shyshkin.war.txttoelasticsearch.model.vk.City;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface CityRepository extends ReactiveElasticsearchRepository<City, Integer> {
}
