package cz.mformanek.ataccama.tenant.dto;

import lombok.Data;

@Data
public class TenantDto {

    String name;
    String hostname;
    String port;
    String databaseName;
    String username;
    String password; //TODO: Sanitize output

}