package net.shyshkin.war.txttoelasticsearch.service;

import net.shyshkin.war.txttoelasticsearch.dto.SearchRequest;
import net.shyshkin.war.txttoelasticsearch.model.vk.City;
import net.shyshkin.war.txttoelasticsearch.model.vk.VkUser;
import reactor.core.publisher.Flux;

public interface WebApiService {

    Flux<City> getCities();

    Flux<VkUser> searchUser(SearchRequest searchRequest);

}
