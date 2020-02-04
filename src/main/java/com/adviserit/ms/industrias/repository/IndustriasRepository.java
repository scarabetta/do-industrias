package com.adviserit.ms.industrias.repository;

import com.adviserit.ms.industrias.domain.Industrias;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Industrias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IndustriasRepository extends JpaRepository<Industrias, Long> {

}
