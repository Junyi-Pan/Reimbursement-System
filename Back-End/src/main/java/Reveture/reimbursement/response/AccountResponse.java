package Reveture.reimbursement.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountResponse {
    private Long accountId;
    private Boolean isManager;

    @JsonCreator
    public AccountResponse(
        @JsonProperty("accountId")  Long accountId,
        @JsonProperty("isManager") Boolean isManager) {
        
        this.accountId = accountId;
        this.isManager = isManager;
    }
}
