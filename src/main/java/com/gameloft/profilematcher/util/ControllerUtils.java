package com.gameloft.profilematcher.util;

import com.gameloft.profilematcher.util.dto.GenericControllerResponseDTO;

public class ControllerUtils {
    public static <T> GenericControllerResponseDTO<T> constructGenericControllerResponse(T data, String message) {
        GenericControllerResponseDTO<T> genericControllerResponseDTO = new GenericControllerResponseDTO<>();
        genericControllerResponseDTO.setData(data);
        genericControllerResponseDTO.setMessage(message);
        return genericControllerResponseDTO;
    }
}
