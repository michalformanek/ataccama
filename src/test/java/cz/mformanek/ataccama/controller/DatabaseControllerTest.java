package cz.mformanek.ataccama.controller;

import cz.mformanek.ataccama.service.SchemaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DatabaseController.class)
class DatabaseControllerTest {

    @Autowired
    DatabaseController databaseController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SchemaService schemaService;

    @Test
    void shouldHaveSchemasEndpoint() throws Exception {
        mockMvc.perform(get("/database/schemas"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldObtainSchemasFromService() {
        databaseController.schemas();
        verify(schemaService, times(1)).getSchemas();
    }

}