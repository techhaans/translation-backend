package com.dc.facadeImpl.fi;

import com.dc.facade.fd.CustomerInfofacade;
import com.domain.dto.CustomerInfoDTO;
import com.domain.model.Customer;
import com.domain.service.CustomerInfoService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CustomerInfofacadeImpl implements CustomerInfofacade {

    private final CustomerInfoService customerInfoService;

    CustomerInfofacadeImpl(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }


    @Override
    public CustomerInfoDTO findByProfileByCuid(UUID cuid) {
        return customerInfoService.findByProfileByCuid(cuid);
    }
}
