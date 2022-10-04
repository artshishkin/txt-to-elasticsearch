package net.shyshkin.war.txttoelasticsearch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.shyshkin.war.txttoelasticsearch.config.data.ApiServiceConfigData;
import net.shyshkin.war.txttoelasticsearch.model.vk.City;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class VkApiService implements WebApiService {

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
}
