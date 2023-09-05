package ai.sxr.shoppingla.geoLocation.service;

import ai.sxr.shoppingla.utils.GenericResponse;
import ai.sxr.shoppingla.geoLocation.dao.UpazilaDao;
import ai.sxr.shoppingla.geoLocation.dto.UpazilaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@EnableCaching
public class UpazilaService {

    private final UpazilaDao upazilaDao;

    public List<UpazilaDto> getUpazila(UpazilaDto dto) {
        return upazilaDao.getUpazila(dto);
    }

    public GenericResponse saveUpazila(UpazilaDto request) {
        return upazilaDao.saveUpazila(request);
    }
}
