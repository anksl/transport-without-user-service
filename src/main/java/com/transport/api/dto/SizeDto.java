package com.transport.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SizeDto {
    private Long id;
    @Positive
    private BigDecimal width;
    @Positive
    private BigDecimal height;
    @Positive
    private BigDecimal depth;
    @Positive
    private BigDecimal weight;
}
