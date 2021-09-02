package cz.mformanek.ataccama.database.controller;

import cz.mformanek.ataccama.database.service.DatabaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/database/{databaseName}")
@RequiredArgsConstructor
public class DatabaseController {

    private final DatabaseService databaseService;

    @GetMapping("/schemas")
    public List<Map<String, String>> getSchemas(){
        return databaseService.getSchemas();
    }

    @GetMapping("/{schema}/tables")
    public List<Map<String, String>> getTables(@PathVariable String schema){
        return databaseService.getTables(schema);
    }

    @GetMapping("/{schema}/{table}/columns")
    public List<Map<String, String>> getColumns(@PathVariable String schema, @PathVariable String table){
        return databaseService.getColumns(schema, table);
    }

    @GetMapping("/{schema}/{table}/data")
    public List<Map<String, String>> getDataPreview(@PathVariable String schema, @PathVariable String table){
        //What is meant by "Data preview of the table"? Actual values or some random mocks
        return databaseService.getDataPreview(schema, table);
    }
}
