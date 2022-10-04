package net.shyshkin.war.txttoelasticsearch.writer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.shyshkin.war.txttoelasticsearch.model.WarriorDoc;
import net.shyshkin.war.txttoelasticsearch.repository.WarriorRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElasticsearchItemWriter implements ItemWriter<WarriorDoc> {

    private final WarriorRepository repository;

    @Override
    public void write(List<? extends WarriorDoc> items) throws Exception {
        repository.saveAll(items)
                .count()
                .doOnNext(count -> log.debug("Saved {} documents into Elasticsearch, starting from {}", count, items.get(0)))
                .subscribe();
    }
}
