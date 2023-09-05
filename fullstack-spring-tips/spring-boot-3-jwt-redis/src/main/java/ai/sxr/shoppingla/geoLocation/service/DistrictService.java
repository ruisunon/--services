package ai.sxr.shoppingla.geoLocation.service;

import ai.sxr.shoppingla.utils.GenericResponse;
import ai.sxr.shoppingla.geoLocation.dao.DistrictDao;
import ai.sxr.shoppingla.geoLocation.dto.DistrictDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableCaching
public class DistrictService {

    private final DistrictDao districtDao;

    public List<DistrictDto> getDistrict(DistrictDto dto) {
        return districtDao.getDistrict(dto);
    }

    public GenericResponse saveDistrict(DistrictDto request) {
        return districtDao.saveDistrict(request);
    }
}
