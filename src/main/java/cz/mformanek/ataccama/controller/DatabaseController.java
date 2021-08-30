package cz.mformanek.ataccama.controller;

import cz.mformanek.ataccama.service.ColumnService;
import cz.mformanek.ataccama.service.DataService;
import cz.mformanek.ataccama.service.SchemaService;
import cz.mformanek.ataccama.service.TableService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "database/{databaseName}")
@RequiredArgsConstructor
public class DatabaseController {

    private final SchemaService schemaService;
    private final TableService tableService;
    private final ColumnService columnService;
    private final DataService dataService;

    @GetMapping("/schemas")
    public List<Map<String, String>> schemas(){
        log.info("fetching schemas");
        return schemaService.getSchemas();
    }

    @GetMapping("/tables")
    public List<Map<String, String>> tables(){
        return tableService.getTables("test");
    }

    @GetMapping("/columns")
    public List<Map<String, String>> columns(){
        return columnService.getColumns("test","tablice");
    }

    @GetMapping("/data")
    public List<Map<String, String>> data(){
        //What is meant by "Data preview of the table"? Actual values or some random mocks
        return dataService.getDataPreview("test","tablice");
    }
}
