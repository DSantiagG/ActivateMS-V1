package com.activate.ActivateMSV1.user_management_ms.infra.repository;

import com.activate.ActivateMSV1.user_management_ms.infra.repository.model.ModUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<ModUser,Long> {
}
