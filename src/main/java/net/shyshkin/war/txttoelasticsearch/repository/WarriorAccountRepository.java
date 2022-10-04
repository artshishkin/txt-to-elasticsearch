package net.shyshkin.war.txttoelasticsearch.repository;

import net.shyshkin.war.txttoelasticsearch.model.WarriorAccount;
import org.springframework.data.elasticsearch.repository.ReactiveElasticsearchRepository;

public interface WarriorAccountRepository extends ReactiveElasticsearchRepository<WarriorAccount, String> {
}
