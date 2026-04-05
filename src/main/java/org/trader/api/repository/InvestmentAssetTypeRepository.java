package org.trader.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.trader.api.entity.InvestmentAssetType;

@Repository
public interface InvestmentAssetTypeRepository extends JpaRepository<InvestmentAssetType, Long> {
}
