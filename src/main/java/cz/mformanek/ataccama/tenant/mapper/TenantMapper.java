package cz.mformanek.ataccama.tenant.mapper;

import cz.mformanek.ataccama.tenant.dto.TenantDto;
import cz.mformanek.ataccama.tenant.model.Tenant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TenantMapper {

    TenantMapper INSTANCE = Mappers.getMapper(TenantMapper.class);

    TenantDto map(Tenant car);

    List<TenantDto> map(List<Tenant> employees);

}
