package com.easybank.accounts.controller;

import com.easybank.accounts.dto.CustomerDetailsDto;
import com.easybank.accounts.dto.CustomerDto;
import com.easybank.accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping(value = "/api/", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomersController {

    private final ICustomerService customerService;

    public CustomersController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Fetch Customer details", description = "Fetches Customer details for a customer using a mobile number.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer details fetched successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerDetailsDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid mobile number format"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number is not valid")
            @Parameter(description = "Mobile number of the customer") String mobileNumber) {
        CustomerDetailsDto customerDetails = customerService.fetchCustomerDerails(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetails);
    }
}
