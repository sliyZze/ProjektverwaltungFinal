package de.szut.lf8_starter.request;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
//Author: Tobias
@Service
public class RequestKundenService extends RequestService {

    public RequestKundenService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    //dummy request fürs checken des Kunden
    public boolean checkKunde(Long customerId)
    {
//        String customersServiceUrl = "https://customer.szut.dev/customers/" + customerId;
//
//        try {
//          ResponseEntity<?> response = getRestTemplate().exchange(customersServiceUrl, HttpMethod.GET, getEntityWithAuthorization(), CustomerDto.class);
//          return response.getBody.getId == customerId;
//        }catch (Exception e){
//          throw new EmployeeNotFoundException("Customer with id " + customerId + e.getMessage());
//        }
        return customerId == 0;
    }

}
