package tr.com.paydaytrade.accountservice.model.dto;

public class TraderUserAccountDto {

    private TraderUserDto traderUserDto;

    private AccountDto accountDto;

    public TraderUserDto getTraderUserDto() {
        return traderUserDto;
    }

    public void setTraderUserDto(TraderUserDto traderUserDto) {
        this.traderUserDto = traderUserDto;
    }

    public AccountDto getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(AccountDto accountDto) {
        this.accountDto = accountDto;
    }
}
