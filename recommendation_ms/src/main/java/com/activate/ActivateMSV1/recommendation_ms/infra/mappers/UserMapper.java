package com.activate.ActivateMSV1.recommendation_ms.infra.mappers;

import com.activate.ActivateMSV1.recommendation_ms.domain.User;
import com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", expression = "java(userDTO.getId() != null ? userDTO.getId().toString() : null)")
    com.activate.ActivateMSV1.recommendation_ms.infra.model.User toRepoModelUser(com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO userDTO);

    @Mapping(target = "id", expression = "java(user.getId() != null ? user.getId().toString() : null)")
    com.activate.ActivateMSV1.recommendation_ms.infra.model.User toRepoModelUser(com.activate.ActivateMSV1.recommendation_ms.domain.User user);

    @Mapping(target = "id", expression = "java(modelRepoUser.getId() != null ? Long.parseLong(modelRepoUser.getId()) : null)")
    com.activate.ActivateMSV1.recommendation_ms.domain.User toDomainUser(com.activate.ActivateMSV1.recommendation_ms.infra.model.User modelRepoUser);

    com.activate.ActivateMSV1.recommendation_ms.infra.DTO.UserDTO toUserDTO(com.activate.ActivateMSV1.recommendation_ms.domain.User user);
}
