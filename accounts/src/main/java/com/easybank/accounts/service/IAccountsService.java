package com.easybank.accounts.service;

import com.easybank.accounts.dto.CustomerDto;
import com.easybank.accounts.exceptions.CustomerAlreadyExistsException;

public interface IAccountsService {

    /**
     * Create a new account with the given customer information.
     *
     * @param customerDto
     *            the customer information
     */
    void createAccount(CustomerDto customerDto);

     /**
      * Retrieve the customer details from the given mobile number.
      *
      * @param mobileNumber
      *            the mobile number to search the customer details
      * @return the customer details
      */
     CustomerDto fetchAccount(String mobileNumber);

     /**
      * Update the customer details and account information.
      *
      * @param customerDto
      *            the customer information
      * @return true if the update was successful, false otherwise
      */
     boolean updateAccounts(CustomerDto customerDto);

    /**
     * Delete the account with the given mobile number.
     *
     * @param mobileNumber
     *            the mobile number of the account to delete
     */
     boolean deleteAccount(String mobileNumber);
}
