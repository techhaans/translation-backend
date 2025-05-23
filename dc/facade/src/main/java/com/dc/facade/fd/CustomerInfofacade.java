package com.dc.facade.fd;

import com.domain.dto.CustomerInfoDTO;
import com.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerInfofacade {

    CustomerInfoDTO findByProfileByCuid(UUID cuid);
}
