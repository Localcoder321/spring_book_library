package dev.localcoder.springbooklibrary.dto.reader;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterReaderRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;
}
