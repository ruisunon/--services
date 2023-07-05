package io.cita.app.business.profile.customer.service;

import io.cita.app.model.dto.CustomerDto;
import io.cita.app.model.dto.request.ClientPageRequest;
import io.cita.app.business.profile.customer.model.CustomerProfileRequest;
import io.cita.app.business.profile.customer.model.CustomerProfileResponse;

public interface CustomerProfileService {
	
	CustomerProfileResponse fetchProfile(final String username, final ClientPageRequest clientPageRequest);
	CustomerDto updateProfileInfo(final CustomerProfileRequest customerProfileRequest);
	
}



