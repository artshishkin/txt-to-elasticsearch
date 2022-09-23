package net.shyshkin.war.txttoelasticsearch.mapper;

import net.shyshkin.war.txttoelasticsearch.model.WarriorDoc;
import net.shyshkin.war.txttoelasticsearch.model.WarriorTxt;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WarriorMapper {

    @Mapping(target = "birthDate", dateFormat = "d.M.uuuu")
    WarriorDoc toDoc(WarriorTxt txt);

}
