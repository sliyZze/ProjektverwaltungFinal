package de.szut.lf8_starter.request;

import de.szut.lf8_starter.exceptionHandling.EmployeeNotFoundException;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestKundenService extends RequestService {

    public RequestKundenService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    //dummy fürs checken des Kunden
    public boolean checkKunde(Long customerId)
    {
        String customersServiceUrl = "https://customer.szut.dev/customers/" + customerId;

        try {
//            ResponseEntity<?> response = getRestTemplate().exchange(customersServiceUrl, HttpMethod.GET, getEntityWithAuthorization(), Boolean.class);
            return true;
        }catch (Exception e){
            throw new EmployeeNotFoundException("Customer with id " + customerId + e.getMessage());
        }
    }

}
