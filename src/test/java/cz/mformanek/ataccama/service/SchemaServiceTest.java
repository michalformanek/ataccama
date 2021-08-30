package cz.mformanek.ataccama.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchemaServiceTest {

    @Mock
    DataSource dataSource;

    @Mock
    Connection connection;

    @Mock
    DatabaseMetaData databaseMetaData;

    @InjectMocks
    SchemaService schemaService;

    @Test
    void shouldGetSchemasFromDatabaseMetadata() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getMetaData()).thenReturn(databaseMetaData);
        final ResultSet schemas = schemaService.getSchemas();
        verify(dataSource, times(1)).getConnection();
    }
}