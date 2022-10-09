package net.shyshkin.war.txttoelasticsearch.service;

import lombok.RequiredArgsConstructor;
import net.shyshkin.war.txttoelasticsearch.config.data.ApiServiceConfigData;
import net.shyshkin.war.txttoelasticsearch.util.SearchUtil;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchUtilService {

    private final ApiServiceConfigData configData;

    public String getSearchName(String fullQueryName) {
        return SearchUtil.getSearchName(fullQueryName, configData.getSearchNameType());
    }
}
