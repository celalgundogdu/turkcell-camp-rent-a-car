package com.turkcellcamp.rentacar.repository;

import com.turkcellcamp.rentacar.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

}
