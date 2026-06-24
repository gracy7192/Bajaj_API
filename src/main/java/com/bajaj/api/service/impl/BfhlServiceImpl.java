package com.bajaj.api.service.impl;

import com.bajaj.api.dto.BfhlRequestDto;
import com.bajaj.api.dto.BfhlResponseDto;
import com.bajaj.api.service.BfhlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${app.user.name}")
    private String userName;

    @Value("${app.user.dob}")
    private String userDob;

    @Value("${app.user.email}")
    private String userEmail;

    @Value("${app.user.roll-number}")
    private String userRollNumber;

    @Override
    public BfhlResponseDto processData(BfhlRequestDto request) {
        BfhlResponseDto response = new BfhlResponseDto();
        try {
            // Set User Details
            response.setUserId(userName + "_" + userDob);
            response.setEmail(userEmail);
            response.setRollNumber(userRollNumber);

            List<String> oddNumbers = new ArrayList<>();
            List<String> evenNumbers = new ArrayList<>();
            List<String> alphabets = new ArrayList<>();
            List<String> specialCharacters = new ArrayList<>();
            BigInteger sum = BigInteger.ZERO;

            if (request != null && request.getData() != null) {
                for (String item : request.getData()) {
                    if (item == null) {
                        continue;
                    }
                    String trimmed = item.trim();
                    if (trimmed.isEmpty()) {
                        continue;
                    }

                    if (isNumber(trimmed)) {
                        try {
                            BigInteger num = new BigInteger(trimmed);
                            if (num.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                                evenNumbers.add(trimmed);
                            } else {
                                oddNumbers.add(trimmed);
                            }
                            sum = sum.add(num);
                        } catch (NumberFormatException e) {
                            // In case it has leading plus or other issues not parsing to BigInteger
                            specialCharacters.add(item);
                        }
                    } else if (isAlphabetical(trimmed)) {
                        alphabets.add(trimmed.toUpperCase());
                    } else {
                        specialCharacters.add(item);
                    }
                }
            }

            response.setOddNumbers(oddNumbers);
            response.setEvenNumbers(evenNumbers);
            response.setAlphabets(alphabets);
            response.setSpecialCharacters(specialCharacters);
            response.setSum(sum.toString());

            // Concatenation logic
            String concatStr = buildConcatString(request != null ? request.getData() : null);
            response.setConcatString(concatStr);

            response.setSuccess(true);
        } catch (Exception e) {
            response.setSuccess(false);
        }
        return response;
    }

    @Override
    public Map<String, Object> getOperationCode() {
        Map<String, Object> response = new HashMap<>();
        response.put("operation_code", 1);
        return response;
    }

    private boolean isNumber(String str) {
        return str.matches("^-?\\d+$");
    }

    private boolean isAlphabetical(String str) {
        return str.matches("^[a-zA-Z]+$");
    }

    private String buildConcatString(List<String> data) {
        if (data == null) {
            return "";
        }
        StringBuilder letters = new StringBuilder();
        for (String item : data) {
            if (item != null) {
                for (char c : item.toCharArray()) {
                    if (Character.isLetter(c)) {
                        letters.append(c);
                    }
                }
            }
        }
        String reversed = letters.reverse().toString();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }
}
