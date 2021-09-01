package cz.mformanek.ataccama.database.service;

import cz.mformanek.ataccama.database.dao.InformationSchemaDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DatabaseServiceTest {

    @Mock
    InformationSchemaDao informationSchemaDao;

    @InjectMocks
    DatabaseService databaseService;

    @Test
    void shouldReturnSchemasFromDao() {
        final var data = List.of(Map.of("key", "value"));
        when(informationSchemaDao.getSchemas()).thenReturn(data);
        final var schemas = databaseService.getSchemas();
        verify(informationSchemaDao, times(1)).getSchemas();
        assertSame(schemas, data);
    }

    @Test
    void shouldReturnTablesOfSchema() {
        final var data = List.of(Map.of("key", "value"));
        when(informationSchemaDao.getTables(anyString())).thenReturn(data);
        final var tables = databaseService.getTables("test");
        verify(informationSchemaDao, times(1)).getTables(anyString());
        assertSame(tables, data);
    }

    @Test
    void shouldReturnColumnsOfTable() {
        final var data = List.of(Map.of("key", "value"));
        when(informationSchemaDao.getColumns(anyString(), anyString())).thenReturn(data);
        final var tables = databaseService.getColumns("test", "test");
        verify(informationSchemaDao, times(1)).getColumns(anyString(), anyString());
        assertSame(tables, data);
    }


    @Test
    void shouldReturnDataPreview() {
        databaseService.getDataPreview("table", "schema");
    }

}