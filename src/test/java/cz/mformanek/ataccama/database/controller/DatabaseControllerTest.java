package cz.mformanek.ataccama.database.controller;

import cz.mformanek.ataccama.database.service.DatabaseService;
import cz.mformanek.ataccama.interceptor.DataSourceSwitchingInterceptor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DatabaseController.class)
class DatabaseControllerTest {

    @Autowired
    DatabaseController databaseController;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DatabaseService databaseService;

    @MockBean
    DataSourceSwitchingInterceptor dataSourceSwitchingInterceptor;

    @BeforeEach
    public void setup() throws Exception {
        when(dataSourceSwitchingInterceptor.preHandle(any(),any(),any())).thenReturn(true);
    }

    @Test
    void shouldReturnSchemasOfDatabase() throws Exception {
        mockMvc.perform(get("/database/test/schemas"))
                .andExpect(status().isOk());
        verify(databaseService, times(1)).getSchemas();
    }

    @Test
    void shouldReturnTablesOfSchema() throws Exception {
        mockMvc.perform(get("/database/test/sample/tables"))
                .andExpect(status().isOk());
        verify(databaseService, times(1)).getTables(eq("sample"));
    }

    @Test
    void shouldReturnColumnsOfTable() throws Exception {
        mockMvc.perform(get("/database/db/schema/table/columns"))
                .andExpect(status().isOk());
        verify(databaseService, times(1)).getColumns(eq("schema"), eq("table"));
    }

    @Test
    void shouldReturnDataPreviewOfTable() throws Exception {
        mockMvc.perform(get("/database/db/schema/table/data"))
                .andExpect(status().isOk());
        verify(databaseService, times(1)).getDataPreview(eq("schema"), eq("table"));
    }


}