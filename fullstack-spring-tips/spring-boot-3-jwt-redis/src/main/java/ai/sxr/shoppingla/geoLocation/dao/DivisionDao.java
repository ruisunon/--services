package ai.sxr.shoppingla.geoLocation.dao;

import ai.sxr.shoppingla.auth.model.User;
import ai.sxr.shoppingla.auth.service.AuthenticationService;
import ai.sxr.shoppingla.geoLocation.dto.DivisionDto;
import ai.sxr.shoppingla.geoLocation.model.Division;
import ai.sxr.shoppingla.geoLocation.repository.DivisionRepository;
import ai.sxr.shoppingla.utils.GenericResponse;
import ai.sxr.shoppingla.utils.ResponseCode;
import ai.sxr.shoppingla.utils.BaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DivisionDao {

    private final BaseRepository baseRepository;
    private final DivisionRepository divisionRepository;
    private final AuthenticationService authenticationService;

    public List<DivisionDto> getDivision(DivisionDto request) {

        List<DivisionDto> divisionDtoList = new ArrayList<>();

        try {

            if (request.getId() != null) {
                Optional<Division> divisionOptional = divisionRepository.findById(request.getId());
                DivisionDto dto = new DivisionDto();
                if (divisionOptional.isPresent()) {
                    BeanUtils.copyProperties(divisionOptional.get(), dto);

                    divisionDtoList.add(dto);
                }
            } else {
                List<Division> divisionList = divisionRepository.findAll();
                for (Division division :
                        divisionList) {
                    DivisionDto divisionDto = new DivisionDto();
                    BeanUtils.copyProperties(division, divisionDto);

                    divisionDtoList.add(divisionDto);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

        return divisionDtoList;
    }

    public GenericResponse saveDivision(DivisionDto request) {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = authenticationService.findUserByUserName(auth.getName());

            Division division = new Division();

            if (request.getName() != null) {
                if (request.getId() != null) {
                    if (divisionRepository.findById(request.getId()).isPresent()) {
                        division = divisionRepository.findById(request.getId()).get();
                        BeanUtils.copyProperties(request, division);

                        division.setModificationTime(new Date());
                        division.setModifiedBy(user);

                        baseRepository.merge(division);
                    } else {
                        return new GenericResponse(ResponseCode.BAD_REQUEST.getCode(), "Wrong id");
                    }
                } else {
                    BeanUtils.copyProperties(request, division);
                    division.setCreatedBy(user);
                    division.setCreationTime(new Date());

                    baseRepository.persist(division);
                }

                return new GenericResponse();
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "REQUIRED PARAMETER MISSING");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "SERVICE ERROR");
        }
    }

}
