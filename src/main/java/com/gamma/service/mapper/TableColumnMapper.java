package com.gamma.service.mapper;


import com.gamma.service.entity.GeneratorTableColumnEntity;
import com.gamma.service.vo.GeneratorTableColumnVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TableColumnMapper {

    TableColumnMapper INSTANCE = Mappers.getMapper( TableColumnMapper.class );

    GeneratorTableColumnVO entityToVo(GeneratorTableColumnEntity entity);

}
