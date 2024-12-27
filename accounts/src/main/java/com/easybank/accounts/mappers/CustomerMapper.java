package com.easybank.accounts.mappers;

import com.easybank.accounts.dto.CustomerDetailsDto;
import com.easybank.accounts.dto.CustomerDto;
import com.easybank.accounts.entity.Customer;

public class CustomerMapper {


    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    public static CustomerDetailsDto mapToCustomerDetailsDto(CustomerDetailsDto customerDetailsDto, CustomerDto customerDto) {
        customerDetailsDto.setName(customerDto.getName());
        customerDetailsDto.setEmail(customerDto.getEmail());
        customerDetailsDto.setMobileNumber(customerDto.getMobileNumber());
        return customerDetailsDto;
    }

}
