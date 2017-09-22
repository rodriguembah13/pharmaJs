package com.ballack.com.service;

import com.ballack.com.domain.Medicament;
import com.ballack.com.domain.Stock;
import com.ballack.com.repository.MedicamentRepository;
import com.ballack.com.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Medicament.
 */
@Service
@Transactional
public class MedicamentService {

    private final Logger log = LoggerFactory.getLogger(MedicamentService.class);

    private final MedicamentRepository medicamentRepository;
    private final StockRepository stockRepository;
    public MedicamentService(MedicamentRepository medicamentRepository, StockRepository stockRepository) {
        this.medicamentRepository = medicamentRepository;
        this.stockRepository = stockRepository;
    }

    /**
     * Save a medicament.
     *
     * @param medicament the entity to save
     * @return the persisted entity
     */
    public Medicament save(Medicament medicament) {
        Stock stock=new Stock();
        stock.setQuantite(0);
        stock.setLibelle("st_"+medicament.getCode());
        stock.setAlertQuantite(10);
        Stock stock1=stockRepository.save(stock);
        medicament.setStock(stock1);
        log.debug("Request to save Medicament : {}", medicament);
        return medicamentRepository.save(medicament);
    }
    /**
     * Save a medicament.
     *
     * @param medicament the entity to save
     * @return the persisted entity
     */
    public Medicament saveAndF(Medicament medicament) {

        log.debug("Request to save Medicament : {}", medicament);
        return medicamentRepository.saveAndFlush(medicament);
    }
    /**
     *  Get all the medicaments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Medicament> findAll(Pageable pageable) {
        log.debug("Request to get all Medicaments");
        return medicamentRepository.findAll(pageable);
    }

    /**
     *  Get one medicament by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Medicament findOne(Long id) {
        log.debug("Request to get Medicament : {}", id);
        return medicamentRepository.findOne(id);
    }

    /**
     *  Delete the  medicament by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Medicament : {}", id);
        medicamentRepository.delete(id);
    }
}
