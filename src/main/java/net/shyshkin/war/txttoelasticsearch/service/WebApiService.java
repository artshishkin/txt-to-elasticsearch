package net.shyshkin.war.txttoelasticsearch.service;

import net.shyshkin.war.txttoelasticsearch.model.City;
import reactor.core.publisher.Flux;

public interface WebApiService {

    Flux<City> getCities();

}
