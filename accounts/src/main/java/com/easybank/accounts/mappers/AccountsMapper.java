package com.easybank.accounts.mappers;

import com.easybank.accounts.dto.AccountsDto;
import com.easybank.accounts.entity.Accounts;

public class AccountsMapper {


    /**
     * Maps the given {@link Accounts} to a {@link AccountsDto}.
     *
     * @param accounts    the source object
     * @param accountsDto the target object
     * @return the target object
     */
    public static AccountsDto mapToAccountsDto(Accounts accounts, AccountsDto accountsDto) {
        // Copy the values from the entity to the Dto
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    /**
     * Maps the given {@link AccountsDto} to a {@link Accounts} object.
     *
     * @param accountsDto the source object
     * @param accounts    the target object
     * @return the target object
     */
    public static Accounts mapToAccounts(AccountsDto accountsDto, Accounts accounts) {
        // Copy the values from the Dto to the entity
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }

}
