package com.kodlamaio.invoiceservice.business.abstracts;

import com.kodlamaio.invoiceservice.business.dto.requests.create.CreateInvoiceRequest;
import com.kodlamaio.invoiceservice.business.dto.requests.update.UpdateInvoiceRequest;
import com.kodlamaio.invoiceservice.business.dto.responses.create.CreateInvoiceResponse;
import com.kodlamaio.invoiceservice.business.dto.responses.get.GetAllInvoicesResponse;
import com.kodlamaio.invoiceservice.business.dto.responses.get.GetInvoiceResponse;
import com.kodlamaio.invoiceservice.business.dto.responses.update.UpdateInvoiceResponse;
import com.kodlamaio.invoiceservice.entities.Invoice;

import java.util.List;

public interface InvoiceService {
    List<GetAllInvoicesResponse> getAll();

    GetInvoiceResponse getById(String id);

    CreateInvoiceResponse addInvoice(CreateInvoiceRequest request);

    UpdateInvoiceResponse update(String id, UpdateInvoiceRequest request);

    void add(Invoice invoice);

    void delete(String id);
}
