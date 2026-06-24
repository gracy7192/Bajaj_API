package com.bajaj.api.service;

import com.bajaj.api.dto.BfhlRequestDto;
import com.bajaj.api.dto.BfhlResponseDto;
import java.util.Map;

public interface BfhlService {
    BfhlResponseDto processData(BfhlRequestDto request);
    Map<String, Object> getOperationCode();
}
