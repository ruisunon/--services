package io.cita.app.business.servicedetail.employee.manager.service.impl;

import io.cita.app.business.servicedetail.employee.manager.model.ServiceDetailRequest;
import io.cita.app.business.servicedetail.employee.manager.service.ManagerServiceDetailService;
import io.cita.app.mapper.ServiceDetailMapper;
import io.cita.app.model.domain.entity.ServiceDetail;
import io.cita.app.model.dto.ServiceDetailDto;
import io.cita.app.repository.CategoryRepository;
import io.cita.app.repository.EmployeeRepository;
import io.cita.app.repository.ServiceDetailRepository;
import io.cita.app.util.StringWrapperUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.cita.app.exception.wrapper.CategoryNotFoundException;
import io.cita.app.exception.wrapper.EmployeeNotFoundException;
import io.cita.app.exception.wrapper.ServiceDetailNotFoundException;

import java.util.Comparator;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class ManagerServiceDetailServiceImpl implements ManagerServiceDetailService {
	
	private final EmployeeRepository employeeRepository;
	private final ServiceDetailRepository serviceDetailRepository;
	private final CategoryRepository categoryRepository;
	
	@Override
	public Page<ServiceDetailDto> fetchAll(final String username) {
		log.info("** Fetch all service details by manager.. *");
		
		final var manager = this.employeeRepository
				.findByCredentialUsernameIgnoringCase(username.strip())
				.orElseThrow(() -> new EmployeeNotFoundException(
						"Employee with username: %s not found".formatted(username)));
		
		return new PageImpl<>(this.serviceDetailRepository
				.findAllByCategorySaloonId(manager.getSaloon().getId()).stream()
					.map(ServiceDetailMapper::toDto)
					.sorted(Comparator
							.comparing((final ServiceDetailDto sd) -> sd.getCategoryDto().getName())
							.thenComparing(ServiceDetailDto::getName))
					.toList());
	}
	
	@Override
	public ServiceDetailDto fetchById(final Integer serviceDetailId) {
		log.info("** Fetch service detail by id by manager.. *");
		return this.serviceDetailRepository.findById(serviceDetailId)
				.map(ServiceDetailMapper::toDto)
				.orElseThrow(ServiceDetailNotFoundException::new);
	}
	
	@Transactional
	@Override
	public Boolean deleteServiceDetail(final Integer serviceDetailId) {
		log.info("** Delete service detail by id by manager.. *");
		this.serviceDetailRepository.deleteById(serviceDetailId);
		return !this.serviceDetailRepository.existsById(serviceDetailId);
	}
	
	@Transactional
	@Override
	public ServiceDetailDto saveServiceDetail(final ServiceDetailRequest serviceDetailRequest) {
		log.info("** Save new service detail.. *");
		
		final var category = this.categoryRepository.findById(serviceDetailRequest.getCategoryId())
				.orElseThrow(CategoryNotFoundException::new);
		
		final var serviceDetail = ServiceDetail.builder()
				.name(serviceDetailRequest.getName().strip().toLowerCase())
				.description(StringWrapperUtils
						.trimIfBlank(serviceDetailRequest.getDescription()))
				.isAvailable(serviceDetailRequest.getIsAvailable() == null || serviceDetailRequest.getIsAvailable())
				.duration(serviceDetailRequest.getDuration())
				.priceUnit(serviceDetailRequest.getPriceUnit())
				.category(category)
				.build();
		
		return ServiceDetailMapper.toDto(this.serviceDetailRepository.save(serviceDetail));
	}
	
	@Transactional
	@Override
	public ServiceDetailDto updateServiceDetail(final ServiceDetailRequest serviceDetailRequest) {
		log.info("** Update service detail.. *");
		
		final var category = this.categoryRepository.findById(serviceDetailRequest.getCategoryId())
				.orElseThrow(CategoryNotFoundException::new);
		final var serviceDetail = this.serviceDetailRepository.findById(serviceDetailRequest.getServiceDetailId())
				.orElseThrow(ServiceDetailNotFoundException::new);
		
		serviceDetail.setName(serviceDetailRequest.getName().strip().toLowerCase());
		serviceDetail.setDescription(StringWrapperUtils
				.trimIfBlank(serviceDetailRequest.getDescription()));
		serviceDetail.setIsAvailable(serviceDetailRequest.getIsAvailable() == null || serviceDetailRequest.getIsAvailable());
		serviceDetail.setDuration(serviceDetailRequest.getDuration());
		serviceDetail.setPriceUnit(serviceDetailRequest.getPriceUnit());
		serviceDetail.setCategory(category);
		
		return ServiceDetailMapper.toDto(this.serviceDetailRepository.save(serviceDetail));
	}
	
}



