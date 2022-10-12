package net.shyshkin.war.txttoelasticsearch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.shyshkin.war.txttoelasticsearch.config.data.ApiServiceConfigData;
import net.shyshkin.war.txttoelasticsearch.dto.SearchRequest;
import net.shyshkin.war.txttoelasticsearch.exception.WebApiServiceException;
import net.shyshkin.war.txttoelasticsearch.model.vk.City;
import net.shyshkin.war.txttoelasticsearch.model.vk.VkBatchSearchUserResponse;
import net.shyshkin.war.txttoelasticsearch.model.vk.VkUser;
import net.shyshkin.war.txttoelasticsearch.util.SearchUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkApiService implements WebApiService {

    public static final ParameterizedTypeReference<List<VkBatchSearchUserResponse>> BATCH_SEARCH_USER_RESPONSE = new ParameterizedTypeReference<>() {
    };
    private final WebClient webClient;
    private final ApiServiceConfigData configData;

    @Override
    public Flux<City> getCities() {
        AtomicLong counter = new AtomicLong();
        return webClient
                .get().uri(configData.getCitiesEndpoint())
                .accept(configData.getAcceptType())
                .exchangeToFlux(response -> {
                    log.debug("Status code: {}", response.statusCode());
                    log.debug("Headers: {}", response.headers().asHttpHeaders());
                    return response.bodyToFlux(City.class);
                })
                .doOnNext(city -> {
                    if (counter.incrementAndGet() % 1000 == 0) {
                        log.debug("Getting City {}: {}", counter.get(), city);
                    }
                });
    }

    @Override
    public Flux<VkUser> searchUser(SearchRequest searchRequest) {
        String searchName = SearchUtil.getSearchName(searchRequest.getName(), configData.getSearchNameType());
        return webClient
                .get().uri(
                        uriBuilder -> uriBuilder
                                .path(configData.getSearchEndpoint())
                                .queryParam("name", searchName)
                                .queryParam("bday", searchRequest.getBday())
                                .queryParam("bmonth", searchRequest.getBmonth())
                                .queryParam("byear", searchRequest.getByear())
                                .queryParam("city", searchRequest.getCity())
                                .build()
                )
                .accept(configData.getAcceptType())
                .exchangeToFlux(response -> {
                    log.debug("Status code: {}", response.statusCode());
                    log.debug("Headers: {}", response.headers().asHttpHeaders());
                    if (!response.statusCode().is2xxSuccessful())
                        return response.bodyToMono(String.class)
                                .map(WebApiServiceException::new)
                                .flatMapMany(Mono::error);
                    return response.bodyToFlux(VkUser.class);
                })
                .doOnNext(user -> log.debug("Found {} for {}", user, searchRequest));
    }

    @Override
    public Mono<VkBatchSearchUserResponse> searchUsers(List<SearchRequest> searchRequests) {
        return webClient
                .post().uri(configData.getSearchEndpoint())
                .bodyValue(searchRequests)
                .accept(configData.getAcceptType())
                .exchangeToMono(response -> {
                    log.debug("Status code: {}", response.statusCode());
                    log.debug("Headers: {}", response.headers().asHttpHeaders());
                    if (!response.statusCode().is2xxSuccessful())
                        return response.bodyToMono(String.class)
                                .map(WebApiServiceException::new)
                                .flatMap(Mono::error);
                    return response.bodyToMono(VkBatchSearchUserResponse.class);
                })
                .doOnNext(batchSearchUserResponse -> batchSearchUserResponse
                        .getResponse()
                        .stream()
                        .filter(resp -> resp.getCount() > 0)
                        .forEach(resp -> log.debug("Found {} ", resp.getItems()))
                );
    }
}
