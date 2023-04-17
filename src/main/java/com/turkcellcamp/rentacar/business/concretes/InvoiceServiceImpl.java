package com.turkcellcamp.rentacar.business.concretes;

import com.turkcellcamp.rentacar.business.abstracts.InvoiceService;
import com.turkcellcamp.rentacar.business.dto.requests.create.CreateInvoiceRequest;
import com.turkcellcamp.rentacar.business.dto.requests.update.UpdateInvoiceRequest;
import com.turkcellcamp.rentacar.business.dto.responses.create.CreateInvoiceResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetAllInvoicesResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetInvoiceResponse;
import com.turkcellcamp.rentacar.business.dto.responses.update.UpdateInvoiceResponse;
import com.turkcellcamp.rentacar.business.rules.InvoiceBusinessRules;
import com.turkcellcamp.rentacar.entities.Invoice;
import com.turkcellcamp.rentacar.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceBusinessRules rules;
    private final ModelMapper mapper;

    @Override
    public List<GetAllInvoicesResponse> getAll() {
        List<Invoice> invoiceList = invoiceRepository.findAll();
        List<GetAllInvoicesResponse> response = invoiceList
                .stream()
                .map(invoice -> mapper.map(invoice, GetAllInvoicesResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetInvoiceResponse getById(int id) {
        rules.checkIfInvoiceExistsById(id);
        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        GetInvoiceResponse response = mapper.map(invoice, GetInvoiceResponse.class);
        return response;
    }

    @Override
    public CreateInvoiceResponse add(CreateInvoiceRequest request) {
        Invoice invoice = mapper.map(request, Invoice.class);
        invoice.setId(0);
        invoice.setTotalPrice(calculateTotalPrice(invoice));
        Invoice createdInvoice = invoiceRepository.save(invoice);
        CreateInvoiceResponse response = mapper.map(createdInvoice, CreateInvoiceResponse.class);
        return response;
    }

    @Override
    public UpdateInvoiceResponse update(int id, UpdateInvoiceRequest request) {
        rules.checkIfInvoiceExistsById(id);
        Invoice invoice = mapper.map(request, Invoice.class);
        invoice.setId(id);
        invoice.setTotalPrice(calculateTotalPrice(invoice));
        Invoice updatedInvoice = invoiceRepository.save(invoice);
        UpdateInvoiceResponse response = mapper.map(updatedInvoice, UpdateInvoiceResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        rules.checkIfInvoiceExistsById(id);
        invoiceRepository.deleteById(id);
    }

    private double calculateTotalPrice(Invoice invoice) {
        return invoice.getDailyPrice() * invoice.getRentedForDays();
    }
}
