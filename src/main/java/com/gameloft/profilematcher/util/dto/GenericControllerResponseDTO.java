package com.gameloft.profilematcher.util.dto;

import lombok.Data;

@Data
public class GenericControllerResponseDTO <T> {
    private String message;
    private T data;
}
