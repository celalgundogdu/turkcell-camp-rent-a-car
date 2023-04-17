package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InvoiceBusinessRules {

    private final InvoiceRepository invoiceRepository;

    public void checkIfInvoiceExistsById(int id) {
        if (!invoiceRepository.existsById(id)) {
            throw new RuntimeException(Messages.Invoice.NOT_EXISTS);
        }
    }
}
