package com.domain.service;

import com.domain.dto.CustomerInfoDTO;
import com.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerInfoService {
  CustomerInfoDTO findByProfileByCuid(UUID uuid );
}
