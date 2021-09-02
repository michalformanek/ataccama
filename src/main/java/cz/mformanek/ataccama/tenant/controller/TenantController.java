package cz.mformanek.ataccama.tenant.controller;

import cz.mformanek.ataccama.tenant.dto.TenantDto;
import cz.mformanek.ataccama.tenant.mapper.TenantMapper;
import cz.mformanek.ataccama.tenant.model.Tenant;
import cz.mformanek.ataccama.tenant.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @GetMapping
    public List<TenantDto> getAllTenants() {
        final var tenants = tenantService.getTenants();
        return TenantMapper.INSTANCE.map(tenants);
    }

    @Operation(summary = "Get a tenant by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tenant found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TenantDto.class))}),
            @ApiResponse(responseCode = "404", description = "Tenant not found", content = @Content)})
    @GetMapping("{id}")
    public TenantDto getTenantById(@Parameter(description = "id of tenant to be fetched") @PathVariable String id) {
        final var tenant = tenantService.getTenantById(id);
        return TenantMapper.INSTANCE.map(tenant);

    }

    @Operation(summary = "Create new tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "tenant created", content = {@
                    Content(mediaType = "application/json", schema = @Schema(implementation = Tenant.class))})})
    @PostMapping
    public TenantDto createTenant(@RequestBody @Valid TenantDto tenantDto) {
        final var tenant = TenantMapper.INSTANCE.map(tenantDto);
        final var savedTenant = tenantService.saveTenant(tenant);
        return TenantMapper.INSTANCE.map(savedTenant);
    }

    @Operation(summary = "Update tenant by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "tenant updated successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TenantDto.class))}),
            @ApiResponse(responseCode = "404", description = "No tenant exists with given id", content = @Content)})
    @PutMapping("{id}")
    public TenantDto updateTenant(
            @Parameter(description = "id of tenant to be updated") @PathVariable String id,
            @RequestBody @Valid TenantDto tenantDto) {
        final var tenant = TenantMapper.INSTANCE.map(tenantDto);
        final var updatedTenant = tenantService.updateTenant(id, tenant);
        return TenantMapper.INSTANCE.map(updatedTenant);
    }


    @Operation(summary = "Delete tenant by its id")
    @DeleteMapping("{id}")
    public void deleteTenant(@Parameter(description = "id of tenant to be deleted") @PathVariable String id) {
        tenantService.deleteTenant(id);
    }

}
