package com.easybank.accounts.service;

import com.easybank.accounts.dto.CustomerDetailsDto;

public interface ICustomerService {

    CustomerDetailsDto fetchCustomerDerails(String mobileNumber);
}
