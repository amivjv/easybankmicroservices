package com.easybank.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Schema(
    name = "Cards",
    description = "Schema to holds Card information"
)
public class CardsDto {

    @Schema(
        name = "mobileNumber",
        description = "Mobile number of the customer",
        example = "1234567890"
    )
    @NotEmpty(message = "Mobile number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number is not valid")
    private String mobileNumber;

    @Schema(
        name = "cardNumber",
        description = "Card number of the customer",
        example = "1234567890"
    )
    @NotEmpty(message = "Card number can not be a null or empty")
    @Pattern(regexp = "(^$|[0-9]{16})", message = "Card number is not valid")
    private String cardNumber;

    @Schema(
        name = "cardType",
        description = "Card type of the customer",
        example = "Credit"
    )
    @NotEmpty(message = "Card type can not be a null or empty")
    private String cardType;

    @Schema(
        name = "totalLimit",
        description = "Total limit of the card",
        example = "10000"
    )
    @Positive(message = "Total limit should be positive")
    private int totalLimit;

    @Schema(
        name = "amountUsed",
        description = "Amount used of the card",
        example = "5000"
    )
    @PositiveOrZero(message = "Amount used should be positive or zero")
    private int amountUsed;

    @Schema(
        name = "availableAmount",
        description = "Available amount of the card",
        example = "5000"
    )
    @PositiveOrZero(message = "Available amount should be positive or zero")
    private int availableAmount;
}
