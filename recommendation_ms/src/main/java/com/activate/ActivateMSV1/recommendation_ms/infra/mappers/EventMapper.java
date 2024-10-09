package com.activate.ActivateMSV1.recommendation_ms.infra.mappers;

import com.activate.ActivateMSV1.recommendation_ms.domain.Event;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.EventInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    EventInfoDTO toEventInfoDTO(Event event);
    EventInfoDTO toEventInfoDTO(com.activate.ActivateMSV1.recommendation_ms.infra.model.Event event);

    Event toDomainEvent(EventInfoDTO eventInfoDTO);

    Event toDomainEvent(com.activate.ActivateMSV1.recommendation_ms.infra.model.Event eventRepoModel);

    com.activate.ActivateMSV1.recommendation_ms.infra.model.Event toRepoModelEvent(EventInfoDTO eventInfoDTO);
}
