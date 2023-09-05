package ai.sxr.shoppingla.geoLocation.dao;

import ai.sxr.shoppingla.auth.model.User;
import ai.sxr.shoppingla.auth.service.AuthenticationService;
import ai.sxr.shoppingla.geoLocation.dto.DistrictDto;
import ai.sxr.shoppingla.geoLocation.model.District;
import ai.sxr.shoppingla.geoLocation.model.Division;
import ai.sxr.shoppingla.geoLocation.repository.DistrictRepository;
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
public class DistrictDao {

    private final BaseRepository baseRepository;
    private final DivisionRepository divisionRepository;
    private final DistrictRepository districtRepository;
    private final AuthenticationService authenticationService;

    public List<DistrictDto> getDistrict(DistrictDto request) {
        List<District> districtList;
        List<DistrictDto> districtDtoList = new ArrayList<>();
        try {
            if (request.getDivisionId() != null) {
                Long divisionId = request.getDivisionId();
                districtList = districtRepository.findAllByDivisionIdOrderById(divisionId);
                for (District district :
                        districtList) {
                    DistrictDto districtDto = new DistrictDto();
                    BeanUtils.copyProperties(district, districtDto);
                    districtDto.setDivisionId(divisionId);

                    districtDtoList.add(districtDto);
                }
            } else if (request.getId() != null) {
                Optional<District> districtOptional = districtRepository.findById(request.getId());
                DistrictDto dto = new DistrictDto();
                if (districtOptional.isPresent()) {
                    BeanUtils.copyProperties(districtOptional.get(), dto);
                    dto.setDivisionId(districtOptional.get().getDivision().getId());
                    districtDtoList.add(dto);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return districtDtoList;
    }

    public GenericResponse saveDistrict(DistrictDto request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = authenticationService.findUserByUserName(auth.getName());

            District district = new District();

            if (request.getName() != null && request.getDivisionId() != null) {
                Optional<Division> optionalDivision = divisionRepository.findById(request.getDivisionId());
                if (optionalDivision.isPresent()) {
                    if (request.getId() != null) {
                        Optional<District> optionalDistrict = districtRepository.findById(request.getId());
                        if (optionalDistrict.isPresent()) {
                            district = optionalDistrict.get();
                            BeanUtils.copyProperties(request, district);

                            district.setModificationTime(new Date());
                            district.setModifiedBy(user);

                            baseRepository.merge(district);
                        } else {
                            return new GenericResponse(ResponseCode.BAD_REQUEST.getCode(), "Wrong Id");
                        }
                    } else {
                        BeanUtils.copyProperties(request, district);
                        district.setDivision(optionalDivision.get());
                        district.setCreatedBy(user);
                        district.setCreationTime(new Date());

                        baseRepository.persist(district);
                    }

                    return new GenericResponse();
                } else {
                    return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong Division Id");
                }
            } else {
                return new GenericResponse(ResponseCode.REQUIRED_PARAMETER_MISSING.getCode(), "REQUIRED PARAMETER MISSING");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return new GenericResponse(ResponseCode.SERVICE_ERROR.getCode(), "SERVICE ERROR");
        }
    }
}
