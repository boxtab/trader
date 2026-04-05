package org.trader.api.controller.manager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trader.api.dto.AssetGroupLeverageDto;
import org.trader.api.service.AssetGroupService;

import java.util.List;

@RestController
@RequestMapping("/api/v2/manager/asset-group")
public class AssetGroupManagerController
{
    private final AssetGroupService assetGroupService;

    public AssetGroupManagerController(AssetGroupService assetGroupService)
    {
        this.assetGroupService = assetGroupService;
    }

    @GetMapping("/leverage")
    public List<AssetGroupLeverageDto> getLeverageList()
    {
        return assetGroupService.getLeverageList();
    }
}
