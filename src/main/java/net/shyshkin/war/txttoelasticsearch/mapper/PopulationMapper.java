package net.shyshkin.war.txttoelasticsearch.mapper;

import net.shyshkin.war.txttoelasticsearch.model.PopulationEntity;
import net.shyshkin.war.txttoelasticsearch.model.PopulationXlsx;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PopulationMapper {

    @Mapping(target = "regionId", source = "sheetName")
    @Mapping(target = "men", source = "allMen")
    @Mapping(target = "women", source = "allWomen")
    PopulationEntity toEntity(PopulationXlsx xlsx);

}
