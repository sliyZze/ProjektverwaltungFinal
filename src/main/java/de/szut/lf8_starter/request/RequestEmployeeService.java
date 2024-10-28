package de.szut.lf8_starter.request;

import de.szut.lf8_starter.exceptionHandling.EmployeeNotFoundException;
import de.szut.lf8_starter.mitarbeiter.dto.MitarbeiterGetDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestEmployeeService extends RequestService {

    public RequestEmployeeService(RestTemplate restTemplate) {
        super(restTemplate);
    }

    public MitarbeiterGetDto getEmployee(Long employeeId)
    {
        String employeeServiceUrl = "https://employee.szut.dev/employees/" + employeeId;

        try {
            ResponseEntity<MitarbeiterGetDto> response = getRestTemplate().exchange(employeeServiceUrl, HttpMethod.GET, getEntityWithAuthorization(), MitarbeiterGetDto.class);
            MitarbeiterGetDto employee = response.getBody();
            assert employee != null;
            return employee;
        }catch (Exception e){
            throw new EmployeeNotFoundException("Employee with id " + employeeId);
        }
    }

}
