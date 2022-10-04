
```java
Mono<City> findFirstByTitleOrAreaOrRegion(String title,String area,String region);
```

```json
{
  "from": 0,
  "size": 1,
  "query": {
    "bool": {
      "should": [
        {
          "query_string": {
            "query": "РЕСП. УДМУРТСКАЯ, Р\\-Н СЮМСИНСКИЙ, СТ. ПИЖИЛ, УЛ. ЛЕСНАЯ, ДОМ 6",
            "fields": [
              "title^1.0"
            ],
            "type": "best_fields",
            "default_operator": "and",
            "max_determinized_states": 10000,
            "enable_position_increments": true,
            "fuzziness": "AUTO",
            "fuzzy_prefix_length": 0,
            "fuzzy_max_expansions": 50,
            "phrase_slop": 0,
            "escape": false,
            "auto_generate_synonyms_phrase_query": true,
            "fuzzy_transpositions": true,
            "boost": 1
          }
        },
        {
          "query_string": {
            "query": "РЕСП. УДМУРТСКАЯ, Р\\-Н СЮМСИНСКИЙ, СТ. ПИЖИЛ, УЛ. ЛЕСНАЯ, ДОМ 6",
            "fields": [
              "area^1.0"
            ],
            "type": "best_fields",
            "default_operator": "and",
            "max_determinized_states": 10000,
            "enable_position_increments": true,
            "fuzziness": "AUTO",
            "fuzzy_prefix_length": 0,
            "fuzzy_max_expansions": 50,
            "phrase_slop": 0,
            "escape": false,
            "auto_generate_synonyms_phrase_query": true,
            "fuzzy_transpositions": true,
            "boost": 1
          }
        },
        {
          "query_string": {
            "query": "РЕСП. УДМУРТСКАЯ, Р\\-Н СЮМСИНСКИЙ, СТ. ПИЖИЛ, УЛ. ЛЕСНАЯ, ДОМ 6",
            "fields": [
              "region^1.0"
            ],
            "type": "best_fields",
            "default_operator": "and",
            "max_determinized_states": 10000,
            "enable_position_increments": true,
            "fuzziness": "AUTO",
            "fuzzy_prefix_length": 0,
            "fuzzy_max_expansions": 50,
            "phrase_slop": 0,
            "escape": false,
            "auto_generate_synonyms_phrase_query": true,
            "fuzzy_transpositions": true,
            "boost": 1
          }
        }
      ],
      "adjust_pure_negative": true,
      "boost": 1
    }
  },
  "post_filter": {
    "bool": {
      "must": [
        {
          "bool": {
            "adjust_pure_negative": true,
            "boost": 1
          }
        },
        {
          "bool": {
            "adjust_pure_negative": true,
            "boost": 1
          }
        }
      ],
      "adjust_pure_negative": true,
      "boost": 1
    }
  },
  "version": true,
  "explain": false
}
```
