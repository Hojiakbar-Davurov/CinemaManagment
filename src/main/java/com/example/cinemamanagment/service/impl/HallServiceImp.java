package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.Film;
import com.example.cinemamanagment.model.dto.FilmDTO;
import com.example.cinemamanagment.model.dto.HallDTO;
import com.example.cinemamanagment.repository.HallRepository;
import com.example.cinemamanagment.service.FilmService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class HallServiceImp implements FilmService {
    @Autowired
    private HallRepository hallRepository;

    @Override
    public HallDTO save(HallDTO hallDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", hallDTO);

        // check film exists by name and cinema id
        if (hallRepository.existsByNameAndCinemaId(hallDTO.getName(), hallDTO.getCinemaId())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + hallDTO.getName() + "  and cinema id: " + hallDTO.getCinemaId());
        }

        // save hall
        FilmDTO savedFilmDTO = filmRepository.save(filmDTO.map2Entity()).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedFilmDTO);

        return savedFilmDTO;
    }

    @Override
    public List<FilmDTO> findAll() {
        log.debug("Request come to " + SERVICE_NAME + " service to get all");

        return filmRepository.findAll()
                .stream()
                .map(Film::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public FilmDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        FilmDTO filmDTO = film.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", filmDTO);

        return filmDTO;
    }

    @Override
    public FilmDTO update(Long id, FilmDTO filmDTO) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, filmDTO);

        // find film by id
        Film optionalFilm = filmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));
        // update film
        Film film = filmDTO.map2Entity();
        film.setId(optionalFilm.getId());

        FilmDTO saveFilmDTO = filmRepository.save(film).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveFilmDTO);

        return saveFilmDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!filmRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id);

        filmRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
