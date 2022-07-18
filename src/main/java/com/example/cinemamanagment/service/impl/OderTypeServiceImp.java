package com.example.cinemamanagment.service.impl;

import com.example.cinemamanagment.exeptions.ResourceAlreadyExistsException;
import com.example.cinemamanagment.exeptions.ResourceNotFoundException;
import com.example.cinemamanagment.model.domain.OrderType;
import com.example.cinemamanagment.model.dto.OrderTypeDTO;
import com.example.cinemamanagment.model.enums.OrderTypeEnum;
import com.example.cinemamanagment.repository.OrderTypeRepository;
import com.example.cinemamanagment.service.OrderTypeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class OderTypeServiceImp implements OrderTypeService {
    @Autowired
    private OrderTypeRepository orderTypeRepository;

    @Override
    public void autoSave() {

        List<OrderTypeEnum> typeEnums = Arrays.stream(OrderTypeEnum.values()).toList();
        for (OrderTypeEnum typeEnum : typeEnums) {
            OrderTypeDTO orderTypeDTO = orderTypeRepository.save(new OrderType(typeEnum.name())).map2DTO();
            log.debug(SERVICE_NAME + " auto saved, DTO:{}", orderTypeDTO);
        }
    }

    @Override
    public OrderTypeDTO save(OrderTypeDTO dto) {
        log.debug("Request come to " + SERVICE_NAME + " service to save, DTO:{}", dto);

        // check order type exists by name
        if (orderTypeRepository.existsByName(dto.getName())) {
            throw new ResourceAlreadyExistsException(SERVICE_NAME + " already exists by name: " + dto.getName());
        }

        // save order type
        OrderTypeDTO savedOrderTypeDTO = orderTypeRepository.save(dto.map2Entity()).map2DTO();
        log.debug(SERVICE_NAME + " saved, DTO:{}", savedOrderTypeDTO);

        return savedOrderTypeDTO;
    }

    @Override
    public List<OrderTypeDTO> findAll(Pageable pageable) {
        log.debug("Request come to " + SERVICE_NAME + " service to get all, page: {}", pageable);

        return orderTypeRepository.findAll(pageable)
                .stream()
                .map(OrderType::map2DTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderTypeDTO findById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to find by id:{}", id);

        OrderType orderType = orderTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        OrderTypeDTO orderTypeDTO = orderType.map2DTO();
        log.debug(SERVICE_NAME + " found, DTO:{}", orderTypeDTO);

        return orderTypeDTO;
    }

    @Override
    public OrderTypeDTO update(Long id, OrderTypeDTO dto) {
        log.debug("Request come to " + SERVICE_NAME + " service to update by id:{},  DTO:{}", id, dto);

        // find order type by id
        OrderType optionalOrderType = orderTypeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(SERVICE_NAME + " not found by id:" + id));

        // update order type
        OrderType orderType = dto.map2Entity();
        orderType.setId(optionalOrderType.getId());

        OrderTypeDTO saveOrderTypeDTO = orderTypeRepository.save(orderType).map2DTO();
        log.debug(SERVICE_NAME + " updated, DTO:{}", saveOrderTypeDTO);

        return saveOrderTypeDTO;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Request come to " + SERVICE_NAME + " service to delete by id:{}", id);

        if (!orderTypeRepository.existsById(id))
            throw new ResourceNotFoundException(SERVICE_NAME + " not found by id: " + id);

        orderTypeRepository.deleteById(id);
        log.debug(SERVICE_NAME + " deleted by id:{}", id);
    }
}
