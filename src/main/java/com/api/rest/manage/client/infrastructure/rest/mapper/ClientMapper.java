package com.api.rest.manage.client.infrastructure.rest.mapper;

import com.api.rest.manage.client.domain.model.Client;
import com.api.rest.manage.client.infrastructure.entity.ClientEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "documentType", target = "documentType"),
            @Mapping(source = "documentNumber", target = "documentNumber"),
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "lastName", target = "lastName"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "brithDate", target = "brithDate"),
            @Mapping(source = "photo", target = "photo")
    })
    Client toProduct(ClientEntity client);

    Iterable<Client>toClients(Iterable<ClientEntity> clientEntities);

    @InheritInverseConfiguration
    ClientEntity toClientEntity(Client client);
}