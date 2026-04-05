package org.trader.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssetGroupLeverageDto
{
    private Long id;
    private String name;
    private Integer leverage;
}
