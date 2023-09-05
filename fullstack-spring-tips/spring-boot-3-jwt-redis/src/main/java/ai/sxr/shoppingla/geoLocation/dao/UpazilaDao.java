package ai.sxr.shoppingla.geoLocation.dao;

import ai.sxr.shoppingla.auth.model.User;
import ai.sxr.shoppingla.auth.service.AuthenticationService;
import ai.sxr.shoppingla.geoLocation.dto.UpazilaDto;
import ai.sxr.shoppingla.geoLocation.model.District;
import ai.sxr.shoppingla.geoLocation.model.Upazila;
import ai.sxr.shoppingla.geoLocation.repository.DistrictRepository;
import ai.sxr.shoppingla.geoLocation.repository.UpazilaRepository;
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
public class UpazilaDao {

    private final BaseRepository baseRepository;
    private final DistrictRepository districtRepository;
    private final UpazilaRepository upazilaRepository;
    private final AuthenticationService authenticationService;

    public List<UpazilaDto> getUpazila(UpazilaDto request) {
        List<Upazila> upazilaList;
        List<UpazilaDto> districtDtoList = new ArrayList<>();
        try {
            if (request.getDistrictId() != null) {
                Long districtId = request.getDistrictId();
                upazilaList = upazilaRepository.findAllByDistrictIdOrderById(districtRepository.findById(districtId).get());
                for (Upazila upazila :
                        upazilaList) {
                    UpazilaDto upazilaDto = new UpazilaDto();
                    BeanUtils.copyProperties(upazila, upazilaDto);
                    upazilaDto.setDistrictId(districtId);

                    districtDtoList.add(upazilaDto);
                }
            } else if (request.getId() != null) {
                Optional<Upazila> upazilaOptional = upazilaRepository.findById(request.getId());
                UpazilaDto dto = new UpazilaDto();
                if (upazilaOptional.isPresent()) {
                    BeanUtils.copyProperties(upazilaOptional.get(), dto);
                    dto.setDistrictId(upazilaOptional.get().getDistrictId().getId());
                    districtDtoList.add(dto);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return districtDtoList;
    }

    public GenericResponse saveUpazila(UpazilaDto request) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = authenticationService.findUserByUserName(auth.getName());

            Upazila upazila = new Upazila();

            if (request.getName() != null && request.getDistrictId() != null) {
                Optional<District> optionalDistrict = districtRepository.findById(request.getDistrictId());
                if (optionalDistrict.isPresent()) {
                    if (request.getId() != null) {
                        Optional<Upazila> optionalUpazila = upazilaRepository.findById(request.getId());
                        if (optionalUpazila.isPresent()) {
                            upazila = optionalUpazila.get();
                            BeanUtils.copyProperties(request, upazila);

                            upazila.setModificationTime(new Date());
                            upazila.setModifiedBy(user);

                            baseRepository.merge(upazila);
                        } else {
                            return new GenericResponse(ResponseCode.BAD_REQUEST.getCode(), "Wrong Id");
                        }
                    } else {
                        BeanUtils.copyProperties(request, upazila);
                        upazila.setDistrictId(optionalDistrict.get());
                        upazila.setCreatedBy(user);
                        upazila.setCreationTime(new Date());

                        baseRepository.persist(upazila);
                    }

                    return new GenericResponse();
                } else {
                    return new GenericResponse(ResponseCode.DATA_NOT_FOUND.getCode(), "Wrong District Id");
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
