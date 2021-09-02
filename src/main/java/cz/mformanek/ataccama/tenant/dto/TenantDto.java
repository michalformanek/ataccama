package cz.mformanek.ataccama.tenant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.*;

@Data
public class TenantDto {

    @NotBlank
    String name;

    @NotBlank
    String hostname;

    @NotBlank
    String port;

    @NotBlank
    String databaseName;

    @NotBlank
    String username;

    @NotBlank
    @JsonProperty(access = WRITE_ONLY)
    String password;

}