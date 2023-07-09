package com.transport.api.dto;

import com.transport.model.enums.LoadApproach;
import com.transport.model.enums.LoadMethod;
import com.transport.model.enums.Packaging;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CargoDto {
    private Long id;
    private String name;
    private LoadApproach loadApproach;
    private LoadMethod loadMethod;
    private Packaging packaging;
    private SizeDto size;
}
