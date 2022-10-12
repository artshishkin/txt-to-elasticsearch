package net.shyshkin.war.txttoelasticsearch.service;

import net.shyshkin.war.txttoelasticsearch.dto.SearchRequest;
import net.shyshkin.war.txttoelasticsearch.model.vk.City;
import net.shyshkin.war.txttoelasticsearch.model.vk.VkBatchSearchUserResponse;
import net.shyshkin.war.txttoelasticsearch.model.vk.VkUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface WebApiService {

    Flux<City> getCities();

    Flux<VkUser> searchUser(SearchRequest searchRequest);

    Mono<VkBatchSearchUserResponse> searchUsers(List<SearchRequest> searchRequests);

}
