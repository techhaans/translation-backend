package com.dc.api.controller;
import com.dc.facade.fd.CustomerInfofacade;
import com.domain.dto.CustomerInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/customer-info")
public class CustomerInfoController {

     private final CustomerInfofacade customerInfofacade;

     CustomerInfoController(CustomerInfofacade customerInfofacade) {
         this.customerInfofacade = customerInfofacade;
     }

     @GetMapping("/profile")
     public ResponseEntity<CustomerInfoDTO> getCustomerProfile(@RequestParam UUID cuid) {
          CustomerInfoDTO dto = customerInfofacade.findByProfileByCuid(cuid);
          return ResponseEntity.ok(dto);
     }
}
