package com.activate.ActivateMSV1.recommendation_ms.infra.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    com.activate.ActivateMSV1.recommendation_ms.infra.model.User toRepoModelUser(com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO userDTO);

    com.activate.ActivateMSV1.recommendation_ms.domain.User toDomainUser(com.activate.ActivateMSV1.recommendation_ms.infra.model.User modelRepoUser);
}
