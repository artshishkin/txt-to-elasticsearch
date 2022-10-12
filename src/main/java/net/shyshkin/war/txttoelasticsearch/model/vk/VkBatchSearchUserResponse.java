package net.shyshkin.war.txttoelasticsearch.model.vk;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class VkBatchSearchUserResponse extends CommonResponse {

    private List<SearchUserResponse> response;

    @Data
    public static class SearchUserResponse {

        private Integer count;
        private Set<VkUser> items;

    }

}
