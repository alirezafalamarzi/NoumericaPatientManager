package com.noumerica.noumericahealth.repository;


import com.noumerica.noumericahealth.data.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * A log repo class for log service, stores and retrieves from mysql
 *  * tables.
 */
public interface LogRepository extends JpaRepository<Log, Long> {
    List<Log> findAllByOrderByTimeAccessedDesc();
}
