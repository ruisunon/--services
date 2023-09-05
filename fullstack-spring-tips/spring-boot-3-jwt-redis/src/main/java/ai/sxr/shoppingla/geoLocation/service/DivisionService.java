package ai.sxr.shoppingla.geoLocation.service;

import ai.sxr.shoppingla.utils.GenericResponse;
import ai.sxr.shoppingla.geoLocation.dao.DivisionDao;
import ai.sxr.shoppingla.geoLocation.dto.DivisionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableCaching
public class DivisionService {

    private final DivisionDao divisionDao;

    public List<DivisionDto> getDivision(DivisionDto request) {
        return divisionDao.getDivision(request);
    }

    public GenericResponse saveDivision(DivisionDto request) {
        return divisionDao.saveDivision(request);
    }
}
