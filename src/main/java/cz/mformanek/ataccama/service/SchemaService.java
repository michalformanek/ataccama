package cz.mformanek.ataccama.service;

import cz.mformanek.ataccama.mapper.MapMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchemaService {

    private final JdbcTemplate jdbcTemplate;

    @SneakyThrows
    public List<Map<String, String>> getSchemas() {
        //We could create repository for this, or autowire datasource and connection.getMetadata().getCatalogs()
        //which gives less information about schemas
        final String query = "SELECT * FROM INFORMATION_SCHEMA.SCHEMATA";
        return jdbcTemplate.query(query, new MapMapper());
    }

}
