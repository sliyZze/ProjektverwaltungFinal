package de.szut.lf8_starter;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/employeeservice")
public class RequestEmployeeService {

    private final RestTemplate restTemplate;

    // Setze die Header
    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity<String> entity = new HttpEntity<>(headers);

    public RequestEmployeeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public MitarbeiterGetDto getEmployee(Long verantwortlicherId)
    {
        String employeeServiceUrl = "https://employee.szut.dev/employees/" + verantwortlicherId;
        headers.set("Authorization", getJwtToken());
        headers.set("accept", "application/json");

        try {
            ResponseEntity<MitarbeiterGetDto> response = restTemplate.exchange(employeeServiceUrl, HttpMethod.GET, entity, MitarbeiterGetDto.class);
            MitarbeiterGetDto employee = response.getBody();
            assert employee != null;
            return employee;
        }catch (Exception e){
            throw new EmployeeNotFoundException("Employee with id " + verantwortlicherId);
        }
    }

    private String getJwtToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) auth;
            Jwt jwt = jwtAuthenticationToken.getToken();
            String tokenValue = jwt.getTokenValue(); // Hier bekommst du den reinen JWT-String
            return "Bearer " + tokenValue; // Der pure JWT-Token wird zurückgegeben
        }

        return "No JWT Token found!";
    }

}
