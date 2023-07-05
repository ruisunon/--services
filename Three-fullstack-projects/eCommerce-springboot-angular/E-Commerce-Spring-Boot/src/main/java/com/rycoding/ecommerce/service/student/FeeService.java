package com.rycoding.ecommerce.service.student;

import com.rycoding.ecommerce.dto.FeeDTO;
import com.rycoding.ecommerce.dto.GeneralResponse;

public interface FeeService {
    GeneralResponse payFee(Long studentId, FeeDTO feeDto);
}
