package com.example.employeemanager.configuration;

import com.example.employeemanager.dto.CheckDTO;
import com.example.employeemanager.entity.Check;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapper {
    @Bean()
    public org.modelmapper.ModelMapper modelMapper2() {
        org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        modelMapper.typeMap(Check.class, CheckDTO.class)
//                .addMappings(mapper -> {
//                    mapper.map(check -> check.getUser().getCode(), CheckDTO::setCode);
//                    mapper.map(check -> check.getUser().getUsername(), (checkDTO, o) -> checkDTO.getUser().setUsename(o.toString()));
//                });
        return modelMapper;

    }
}
