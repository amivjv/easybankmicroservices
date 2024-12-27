package com.easybank.accounts.service.impl;

import com.easybank.accounts.dto.CustomerDetailsDto;
import com.easybank.accounts.dto.CustomerDto;
import com.easybank.accounts.mappers.CustomerMapper;
import com.easybank.accounts.repository.AccountsRepository;
import com.easybank.accounts.repository.CustomerRepository;
import com.easybank.accounts.service.IAccountsService;
import com.easybank.accounts.service.ICustomerService;
import com.easybank.accounts.service.client.CardsFeignClient;
import com.easybank.accounts.service.client.LoansFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;
    private final CardsFeignClient cardsFeignClient;
    private final LoansFeignClient loansFeignClient;
    private final IAccountsService accountsService;


    @Override
    public CustomerDetailsDto fetchCustomerDerails(String mobileNumber) {
        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
        CustomerDto customerDto = accountsService.fetchAccount(mobileNumber);

        customerDetailsDto.setAccountsDto(customerDto.getAccountsDto());
        CustomerMapper.mapToCustomerDetailsDto(customerDetailsDto, customerDto);

        customerDetailsDto.setCardsDto(cardsFeignClient.fetchCardDetails(mobileNumber).getBody());
        customerDetailsDto.setLoansDto(loansFeignClient.fetchLoanDetails(mobileNumber).getBody());

        return customerDetailsDto;
    }
}
