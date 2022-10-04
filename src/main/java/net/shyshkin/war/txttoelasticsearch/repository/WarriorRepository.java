package net.shyshkin.war.txttoelasticsearch.repository;

import net.shyshkin.war.txttoelasticsearch.model.WarriorDoc;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface WarriorRepository extends ReactiveElasticsearchRepository<WarriorDoc, String> {
}
