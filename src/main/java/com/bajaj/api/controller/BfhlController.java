package com.bajaj.api.controller;

import com.bajaj.api.dto.BfhlRequestDto;
import com.bajaj.api.dto.BfhlResponseDto;
import com.bajaj.api.service.BfhlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/bfhl")
@CrossOrigin(origins = "*")
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping
    public ResponseEntity<BfhlResponseDto> processPostRequest(@RequestBody BfhlRequestDto request) {
        BfhlResponseDto response = bfhlService.processData(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> processGetRequest() {
        Map<String, Object> response = bfhlService.getOperationCode();
        return ResponseEntity.ok(response);
    }
}
