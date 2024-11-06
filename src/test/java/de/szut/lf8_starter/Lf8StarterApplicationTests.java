package de.szut.lf8_starter;

import de.szut.lf8_starter.projekt.ProjektController;
import de.szut.lf8_starter.projekt.ProjektService;
import de.szut.lf8_starter.projekt.dto.ProjektCreateDto;
import de.szut.lf8_starter.request.RequestEmployeeService;
import de.szut.lf8_starter.request.RequestKundenService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Lf8StarterApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RequestEmployeeService employeeServiceRequest;

    @Mock
    private RequestKundenService kundenServiceRequest;

    @Mock
    private ProjektService projektService;

    @InjectMocks
    private ProjektController projektController;

    String validJwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzUFQ0dldiNno5MnlQWk1EWnBqT1U0RjFVN0lwNi1ELUlqQWVGczJPbGU0In0.eyJleHAiOjE3MzA4OTE4ODcsImlhdCI6MTczMDg4ODI4NywianRpIjoiNGFhMDVjMmItNzU4OC00NWJiLThiM2QtNWExZmE0YzFlMWE2IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5zenV0LmRldi9hdXRoL3JlYWxtcy9zenV0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU1NDZjZDIxLTk4NTQtNDMyZi1hNDY3LTRkZTNlZWRmNTg4OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLW1hbmFnZW1lbnQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiJiNTc5NGNmMy00Y2I1LTRjODctYjA5Yy0xMWUwNGY0OGRmNTciLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6NDIwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsicHJvZHVjdF9vd25lciIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1zenV0IiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.WtSKe8OGRlysoTXaxBLDL_0Ad2HSmV-_Jw7ZcjroBY5DMDwtBEspv7rXQFzUMVgxCmcIgUm7gshCGZ1WQafi-89J8SXHbwINI1pm_SJzWTXypX0SGIzHGIUhZwUfqYb1FXh0_mM0ujws1Xw85XrxTIvNyFtSva8lWM9MySeDlHH2M8sCIWNRIfL4exw57R5mPVaLV9WEJM-EEuZzLi_Xdqsn-vIMn5bRHjUx9Q_8Dw3aPJCQLmgd3ZLwZEzdOqhsqZ5crvf1PzS_nY8mv5OJEdumar9xxp3oLBQEv6tpTXpI0KdfdKE5VIqA9OIEQBRMnTs486gLwyPO6oRxVL-blg"; // Hier muss ein gültiges Token verwendet werden.


    @Test
    void createProjectSuccessfully() throws Exception {
        ProjektCreateDto projektCreateDto = new ProjektCreateDto();
        String content = """ 
                { 
                  "bezeichnung": "Email Notifyer",
                  "verantwortlicherMitarbeiter": 297,
                  "kundenId": 1,
                  "kundenName": "Thorsten",
                  "kommentar": "ein microservice der email versendet",
                  "startDatum": "2024-10-10",
                  "gepEndDatum": "2024-12-10",
                  "tatEndDatum": "2024-12-25",
                  "qualifikationen": ["php", "java", "c++", "Docker", "React" ,"Bootstrap"]
                 } """;

        final var contentAsString = this.mockMvc.perform(post("/project").header("Authorization", "Bearer " + employeeServiceRequest.getJwtToken()).content(content).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("bezeichnung", is("Email Notifyer")))
                .andExpect((ResultMatcher) jsonPath("verantwortlicherMitarbeiter", is(297)))
                .andExpect((ResultMatcher) jsonPath("kundenId", is(1)))
                .andExpect((ResultMatcher) jsonPath("kundenName", is("Thorsten")))
                .andExpect((ResultMatcher) jsonPath("startDatum", is("2024-10-10")))
                .andExpect((ResultMatcher) jsonPath("gepEndDatum", is("2024-12-10")))
                .andExpect((ResultMatcher) jsonPath("tatEndDatum", is("2024-12-25")))
                .andExpect((ResultMatcher) jsonPath("qualifikationen", is("[\"php\", \"java\", \"c++\", \"Docker\", \"React\" ,\"Bootstrap\"]")))
                .andExpect((ResultMatcher) jsonPath("kommentar", is("ein microservice der email versendet"))).andReturn()
                .getResponse() .getContentAsString();
    }

    @Test
    public void testGetAllProjects_withValidToken_shouldReturnOk() throws Exception {

        mockMvc.perform(get("/project/all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProjectById() throws Exception {
        // Angenommene ID des Projekts (ersetze dies mit einer gültigen ID aus der Datenbank oder dem Setup)
        long projectId = 1L; // Beispiel ID für das Projekt

        // Perform the GET request to get the project by ID
        mockMvc.perform(get("/project/{id}", projectId)  // Der Endpunkt, den du testen möchtest
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken) // Token im Header
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());  // Wir erwarten Status 200 OK
    }
}

