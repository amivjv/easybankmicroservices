package com.easybank.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Accounts",
        description = "Schema to holds Accounts information"
)
public class CustomerDto {

    @Schema(
            name = "name",
            description = "Name of the customer",
            example = "Allen Kumar"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min=3, max=100, message = "Name must be between 3 and 100 characters")
    private String name;

    @Schema(
            name = "email",
            description = "Email of the customer",
            example = "ab@paymonk.com"
    )
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email can not be a null or empty")
    private String email;

    @Schema(
            name = "mobileNumber",
            description = "Mobile number of the customer",
            example = "1234567890"
    )
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number is not valid")
    private String mobileNumber;

    @Schema(
            name = "AccountsDto",
            description = "Accounts dto hols information of the customer's account",
            example = "22333442111"
    )
    private AccountsDto accountsDto;
}
