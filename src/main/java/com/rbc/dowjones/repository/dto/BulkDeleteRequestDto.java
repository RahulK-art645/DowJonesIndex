package com.rbc.dowjones.repository.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

public class BulkDeleteRequestDto {
    private List<@NotNull(message = "ID list cannot be empty")
    @Positive(message = "ID must be greater than zero") Long> ids;

    public List<Long> getIds(){
        return ids;
    }

    public void setIds(List<Long> ids){
        this.ids=ids;
    }

}
