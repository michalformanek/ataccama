package cz.mformanek.ataccama.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchemaService {

    private final DataSource dataSource;

    @SneakyThrows
    public List<Map<String, String>> getSchemas() {
        //We could create repository for this, or call connection.getMetadata().getCatalogs(), which gives less information about schemas
        final String query = "SELECT * FROM `SCHEMATA`";
        final var connection = dataSource.getConnection();
        connection.setCatalog("INFORMATION_SCHEMA");
        final var result = connection.prepareStatement(query).executeQuery();
        return map(result);
    }

    @SneakyThrows
    private List<Map<String, String>> map(ResultSet rs) {
        List<Map<String, String>> result = new ArrayList<>();
        var rsMetaData = rs.getMetaData();
        var columnCount = rsMetaData.getColumnCount();

        while (rs.next()) {
            Map<String, String> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                final String key = rsMetaData.getColumnName(i);
                final String value = rs.getString(i);
                row.put(key, value);
            }
            result.add(row);
        }
        return result;
    }

}
