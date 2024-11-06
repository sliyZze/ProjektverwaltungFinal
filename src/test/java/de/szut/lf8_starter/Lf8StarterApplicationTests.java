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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    String validJwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICIzUFQ0dldiNno5MnlQWk1EWnBqT1U0RjFVN0lwNi1ELUlqQWVGczJPbGU0In0.eyJleHAiOjE3MzA4OTYxMTIsImlhdCI6MTczMDg5MjUxMiwianRpIjoiZjVjYWJlZjMtMzhjNy00MTMzLTlhMjMtYzkyZGFlMGIwN2E3IiwiaXNzIjoiaHR0cHM6Ly9rZXljbG9hay5zenV0LmRldi9hdXRoL3JlYWxtcy9zenV0IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjU1NDZjZDIxLTk4NTQtNDMyZi1hNDY3LTRkZTNlZWRmNTg4OSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLW1hbmFnZW1lbnQtc2VydmljZSIsInNlc3Npb25fc3RhdGUiOiI3OWFlNzI3ZC1mYzQ0LTRlYzUtOWM2MC1iYjNhYmNiNWFjZGYiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6NDIwMCJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsicHJvZHVjdF9vd25lciIsIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1zenV0IiwidW1hX2F1dGhvcml6YXRpb24iLCJ1c2VyIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsInByZWZlcnJlZF91c2VybmFtZSI6InVzZXIifQ.dmlmR_wGCKVOT-75QdMOR75JHtEk3SmYKGwuKR0Qg1M3tHKI1rzFUZWqkTa5t1QpaiAZoRjFSSgtLuM5lK9-pkSjqgQ8pQ6d2Xz2qdx074OrkAsN2LQzfAbv0Mzq3SGw3Mm5m53dmngXmcd45AhMQZJfIZDFF7eogSLT2SBDMsEf_Nlp74vSWFsf7C0kX2BRxjTOC_T2YfQwkWeXC86PX9a2Z8RVjAQ8hkPub0S1fjYn91VOesCp4i_6CI2cVvQDd_EqUFs0mJzDiu0I9SXZakyKqlof2E0ct5YKccn2l5adUalV5GGM5LMRGCHODPTMTBz346c6R1eE1rYQl5QayQ"; // Hier muss ein gültiges Token verwendet werden.


    @Test
    void testCreate() throws Exception {
        String content = """ 
                { 
                  "bezeichnung": "Email Notifyer",
                  "verantwortlicherMitarbeiter": 298,
                  "kundenId": 0,
                  "kundenName": "Thorsten",
                  "kommentar": "ein microservice der email versendet",
                  "startDatum": "2024-10-10",
                  "gepEndDatum": "2024-12-10",
                  "tatEndDatum": "2024-12-25",
                  "qualifikationen": ["php", "java", "c++", "Docker", "React" ,"Bootstrap"]
                 } """;

        final var contentAsString = this.mockMvc.perform(post("/project").header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken).content(content).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("bezeichnung", is("Email Notifyer")))
                .andExpect((ResultMatcher) jsonPath("verantwortlicherMitarbeiter", is(298)))
                .andExpect((ResultMatcher) jsonPath("kundenId", is(0)))
                .andExpect((ResultMatcher) jsonPath("kundenName", is("Thorsten")))
                .andExpect((ResultMatcher) jsonPath("startDatum", is("2024-10-10")))
                .andExpect((ResultMatcher) jsonPath("gepEndDatum", is("2024-12-10")))
                .andExpect((ResultMatcher) jsonPath("tatEndDatum", is("2024-12-25")))
                .andExpect((ResultMatcher) jsonPath("qualifikationen", is("[\"php\", \"java\", \"c++\", \"Docker\", \"React\" ,\"Bootstrap\"]")))
                .andExpect((ResultMatcher) jsonPath("kommentar", is("ein microservice der email versendet"))).andReturn().getResponse().getContentAsString();
    }

    @Test
    public void testGetAllProjects() throws Exception {

        mockMvc.perform(get("/project/all")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProjectById() throws Exception {

        long id = 1L;

        mockMvc.perform(get("/project/{id}", id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testGetAllEmployeeProjects() throws Exception {

        long id = 297L;

        mockMvc.perform(get("/project/employee/{id}", id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testGetAllEmployees() throws Exception{

        long id = 1L;

        mockMvc.perform(get("/project/{id}/employees", id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddEmployee() throws Exception{

        long id = 1L;
        long eid = 297L;

        mockMvc.perform(post("/project/{id}/add/employee/{eid}", id,eid)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testRemoveEmployee() throws Exception{

        long id = 1L;
        long eid = 297L;

        mockMvc.perform(delete("/project/{id}/add/employee/{eid}", id,eid)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDelete() throws Exception{

        long id = 1L;
        long eid = 297L;

        mockMvc.perform(delete("/project/{id}", id,eid)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + validJwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}

