package com.easybank.accounts.service.impl;

import com.easybank.accounts.constants.AccountsConstants;
import com.easybank.accounts.dto.AccountsDto;
import com.easybank.accounts.dto.CustomerDto;
import com.easybank.accounts.entity.Accounts;
import com.easybank.accounts.entity.Customer;
import com.easybank.accounts.exceptions.CustomerAlreadyExistsException;
import com.easybank.accounts.exceptions.ResourceNotFoundException;
import com.easybank.accounts.mappers.AccountsMapper;
import com.easybank.accounts.mappers.CustomerMapper;
import com.easybank.accounts.repository.AccountsRepository;
import com.easybank.accounts.repository.CustomerRepository;
import com.easybank.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsService implements IAccountsService {

    private CustomerRepository customerRepository;
    private AccountsRepository accountsRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> byMobileNumber = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if (byMobileNumber.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already exists with given mobileNumber " + customer.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);

        accountsRepository.save(createNewAccount(savedCustomer));
    }

    /**
     * Retrieve the customer details from the given mobile number.
     *
     * @param mobileNumber the mobile number to search the customer details
     * @return the customer details
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer byMobileNumber = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(byMobileNumber.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", byMobileNumber.getCustomerId())
        );

        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(byMobileNumber, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    /**
     * Update the customer details and account information.
     *
     * @param customerDto the customer information
     * @return true if the update was successful, false otherwise
     */
    @Override
    public boolean updateAccounts(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if(accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", accountsDto.getAccountNumber())
            );
            Accounts updatedAccount = AccountsMapper.mapToAccounts(accountsDto, accounts);
            accountsRepository.save(updatedAccount);
            Customer customer = customerRepository.findById(accounts.getCustomerId()).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", accounts.getCustomerId())
            );
            Customer updatedCustomer = CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(updatedCustomer);
            isUpdated = true;
        }
        return isUpdated;
    }

    /**
     * Delete the account with the given mobile number.
     *
     * @param mobileNumber the mobile number of the account to delete
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer byMobileNumber = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(byMobileNumber.getCustomerId());
        customerRepository.deleteById(byMobileNumber.getCustomerId());
        return true;
    }


    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }
}
