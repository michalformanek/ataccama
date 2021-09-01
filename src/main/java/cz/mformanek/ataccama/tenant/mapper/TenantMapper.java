package cz.mformanek.ataccama.tenant.mapper;

import cz.mformanek.ataccama.tenant.dto.TenantDto;
import cz.mformanek.ataccama.tenant.model.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TenantMapper {

    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);

    Tenant map(TenantDto tenant);

    TenantDto map(Tenant tenant);

    Tenant update(@MappingTarget Tenant original, Tenant modified);

    List<TenantDto> map(List<Tenant> tenant);

}
