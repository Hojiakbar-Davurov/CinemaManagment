package com.example.cinemamanagment.mapper;

import com.example.cinemamanagment.model.domain.Cinema;
import com.example.cinemamanagment.model.dto.CinemaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        config = IgnoreUnmappedMapperConfig.class,
        componentModel = "spring",
        uses = {}
)
public interface CinemaMapper extends EntityMapper<CinemaDTO, Cinema> {
    default Cinema fromId(Long id) {
        if (id == null) {
            return null;
        }

        return Cinema.builder()
                .id(id)
                .build();
    }

    @Override
    @Named(value = "toDto")
    @Mapping(source = "pupil.id", target = "pupilId")
    CinemaDTO toDto(Cinema entity);

    @Override
    @Named(value = "toEntity")
    @Mapping(source = "pupilId", target = "pupil")
    Cinema toEntity(CinemaDTO dto);
}
