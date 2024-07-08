package com.ia.inv_arg.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;

    public ApiResponse(String articuloCreadoExitosamente) {
    }
}
