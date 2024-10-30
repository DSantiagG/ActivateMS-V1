package com.activate.ActivateMSV1.recommendation_ms.infra.mappers;

import com.activate.ActivateMSV1.recommendation_ms.domain.Event;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventInfoDTO toEventInfoDTO(Event event);

    @Mapping(target = "id", expression = "java(event.getId() != null ? Long.parseLong(event.getId()) : null)")
    EventInfoDTO toEventInfoDTO(com.activate.ActivateMSV1.recommendation_ms.infra.model.Event event);

    Event toDomainEvent(EventInfoDTO eventInfoDTO);

    @Mapping(target = "id", expression = "java(eventRepoModel.getId() != null ? Long.parseLong(eventRepoModel.getId()) : null)")
    Event toDomainEvent(com.activate.ActivateMSV1.recommendation_ms.infra.model.Event eventRepoModel);

    @Mapping(target = "id", expression = "java(eventInfoDTO.getId() != null ? eventInfoDTO.getId().toString() : null)")
    com.activate.ActivateMSV1.recommendation_ms.infra.model.Event toRepoModelEvent(EventInfoDTO eventInfoDTO);

    @Mapping(target = "id", expression = "java(event.getId() != null ? event.getId().toString() : null)")
    com.activate.ActivateMSV1.recommendation_ms.infra.model.Event toRepoModelEvent(Event event);
}