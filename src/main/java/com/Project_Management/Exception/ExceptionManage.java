package com.Project_Management.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExceptionManage {
    private String message;
    private HttpStatus status;
    private LocalDate localDate;
    private boolean success;
}
