package net.shyshkin.war.txttoelasticsearch.repository;

import net.shyshkin.war.txttoelasticsearch.model.City;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface CityRepository extends ReactiveElasticsearchRepository<City, Integer> {
}
