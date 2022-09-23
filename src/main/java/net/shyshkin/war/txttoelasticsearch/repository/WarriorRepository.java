package net.shyshkin.war.txttoelasticsearch.repository;

import net.shyshkin.war.txttoelasticsearch.model.WarriorDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface WarriorRepository extends ElasticsearchRepository<WarriorDoc, Integer> {
}
