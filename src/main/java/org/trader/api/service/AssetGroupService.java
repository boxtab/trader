package org.trader.api.service;

import org.springframework.stereotype.Service;
import org.trader.api.dto.AssetGroupLeverageDto;
import org.trader.api.entity.InvestmentAssetType;
import org.trader.api.repository.InvestmentAssetTypeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssetGroupService
{
    private final InvestmentAssetTypeRepository repository;

    public AssetGroupService(InvestmentAssetTypeRepository repository)
    {
        this.repository = repository;
    }

    public List<AssetGroupLeverageDto> getLeverageList()
    {
        List<InvestmentAssetType> types = repository.findAll();

        return types.stream()
                .map(type -> new AssetGroupLeverageDto(
                        type.getId(),
                        type.getName(),
                        type.getLeverage()
                ))
                .collect(Collectors.toList());
    }
}
