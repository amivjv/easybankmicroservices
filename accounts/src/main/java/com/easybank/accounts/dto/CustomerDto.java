package com.easybank.accounts.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min=3, max=100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email can not be a null or empty")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number is not valid")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
