package com.easybank.accounts.controller;

import com.easybank.accounts.constants.AccountsConstants;
import com.easybank.accounts.dto.AccountContactInfoDto;
import com.easybank.accounts.dto.CustomerDto;
import com.easybank.accounts.dto.ErrorResponseDto;
import com.easybank.accounts.dto.ResponseDto;
import com.easybank.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
//@AllArgsConstructor
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {

    private IAccountsService accountsService;

    public AccountsController(IAccountsService accountsService) {
        this.accountsService = accountsService;

    }

    @Value("${build.version}")
    private String buildVersion;
    @Autowired
    private Environment environment;
    @Autowired
    private AccountContactInfoDto accountContactInfoDto;

    @Operation(summary = "Create a new account", description = "Creates a new account with the provided customer details.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody @Valid CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(summary = "Fetch account details", description = "Fetches account details for a customer using a mobile number.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account details fetched successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid mobile number format"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number is not valid")
            @Parameter(description = "Mobile number of the customer") String mobileNumber) {
        return ResponseEntity.ok(accountsService.fetchAccount(mobileNumber));
    }

    @Operation(summary = "Update account details", description = "Updates account information based on provided customer details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "417", description = "Expectation failed, unable to update account"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccounts(@RequestBody @Valid CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateAccounts(customerDto);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(summary = "Delete an account", description = "Deletes an account associated with the specified mobile number.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account deleted successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "417", description = "Expectation failed, unable to delete account"),
            @ApiResponse(responseCode = "400", description = "Invalid mobile number format")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccounts(
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number is not valid")
            @Parameter(description = "Mobile number of the customer") String mobileNumber) {
        boolean isDeleted = accountsService.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @Operation(
            summary = "Get Build information",
            description = "Get Build information that is deployed into accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @Operation(
            summary = "Get Java version",
            description = "Get Java versions details that is installed into accounts microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaVersion(){
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }

    @Operation(
            summary = "Get contact info",
            description = "Contacts info details that can be reach out."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contact info details fetched successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AccountContactInfoDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })

    @GetMapping("/contact-info")
    public ResponseEntity<AccountContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(accountContactInfoDto);
    }
}
