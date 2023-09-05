package ai.sxr.shoppingla.geoLocation.controller;

import ai.sxr.shoppingla.geoLocation.service.UpazilaService;
import ai.sxr.shoppingla.utils.GenericResponse;
import ai.sxr.shoppingla.geoLocation.dto.DistrictDto;
import ai.sxr.shoppingla.geoLocation.dto.DivisionDto;
import ai.sxr.shoppingla.geoLocation.dto.UpazilaDto;
import ai.sxr.shoppingla.geoLocation.service.DistrictService;
import ai.sxr.shoppingla.geoLocation.service.DivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/geo")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GeoLocationController {

    private final DivisionService divisionService;
    private final DistrictService districtService;
    private final UpazilaService upazilaService;

    @PostMapping(path = "/division/get")
    @PreAuthorize("hasAuthority('division:get')")
    public List<DivisionDto> getDivision(DivisionDto request) {
        return divisionService.getDivision(request);
    }

    @PostMapping(path = "/division/save")
    @PreAuthorize("hasAuthority('division:save')")
    public GenericResponse saveDistrict(DivisionDto dto) {
        return divisionService.saveDivision(dto);
    }

    @PostMapping(path = "/district/get")
    @PreAuthorize("hasAuthority('district:get')")
    public List<DistrictDto> getDistrictList(DistrictDto dto) {
        return districtService.getDistrict(dto);
    }

    @PostMapping(path = "/district/save")
    @PreAuthorize("hasAuthority('district:save')")
    public GenericResponse saveDistrict(DistrictDto dto) {
        return districtService.saveDistrict(dto);
    }

    @PostMapping(path = "/upazila/get")
    @PreAuthorize("hasAuthority('upazila:get')")
    public List<UpazilaDto> getUpazilaList(UpazilaDto dto) {
        return upazilaService.getUpazila(dto);
    }

    @PostMapping(path = "/upazila/save")
    @PreAuthorize("hasAuthority('upazila:save')")
    public GenericResponse saveUpazila(UpazilaDto dto) {
        return upazilaService.saveUpazila(dto);
    }
}
