package com.turkcellcamp.rentacar.business.concretes;

import com.turkcellcamp.rentacar.business.abstracts.PaymentService;
import com.turkcellcamp.rentacar.business.abstracts.PosService;
import com.turkcellcamp.rentacar.business.dto.requests.create.CreatePaymentRequest;
import com.turkcellcamp.rentacar.business.dto.requests.update.UpdatePaymentRequest;
import com.turkcellcamp.rentacar.business.dto.responses.create.CreatePaymentResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetAllPaymentsResponse;
import com.turkcellcamp.rentacar.business.dto.responses.get.GetPaymentResponse;
import com.turkcellcamp.rentacar.business.dto.responses.update.UpdatePaymentResponse;
import com.turkcellcamp.rentacar.common.dto.CreateRentalPaymentRequest;
import com.turkcellcamp.rentacar.entities.Payment;
import com.turkcellcamp.rentacar.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PosService posService;
    private final ModelMapper mapper;

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        List<Payment> paymentList = paymentRepository.findAll();

        List<GetAllPaymentsResponse> response = paymentList
                .stream()
                .map(payment -> mapper.map(payment, GetAllPaymentsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetPaymentResponse getById(int id) {
        checkIfPaymentExists(id);
        Payment payment = paymentRepository.findById(id).orElseThrow();
        GetPaymentResponse response = mapper.map(payment, GetPaymentResponse.class);
        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        checkIfCardExists(request.getCardNumber());
        Payment payment = mapper.map(request, Payment.class);
        payment.setId(0);
        Payment createdPayment = paymentRepository.save(payment);

        CreatePaymentResponse response = mapper.map(createdPayment, CreatePaymentResponse.class);
        return response;
    }

    @Override
    public UpdatePaymentResponse update(int id, UpdatePaymentRequest request) {
        checkIfPaymentExists(id);
        Payment payment = mapper.map(request, Payment.class);
        payment.setId(id);
        Payment updatedPayment = paymentRepository.save(payment);

        UpdatePaymentResponse response = mapper.map(updatedPayment, UpdatePaymentResponse.class);
        return response;
    }

    @Override
    public void delete(int id) {
        checkIfPaymentExists(id);
        paymentRepository.deleteById(id);
    }

    @Override
    public void processRentalPayment(CreateRentalPaymentRequest request) {
        checkIfPaymentIsValid(request);
        Payment payment = paymentRepository.findByCardNumber(request.getCardNumber());
        checkIfBalanceIsEnough(request.getPrice(), payment.getBalance());
        posService.pay();
        payment.setBalance(payment.getBalance() - request.getPrice());
        paymentRepository.save(payment);
    }

    private void checkIfBalanceIsEnough(double price, double balance) {
        if (price > balance) {
            throw new RuntimeException("Insufficient balance");
        }
    }

    private void checkIfPaymentIsValid(CreateRentalPaymentRequest request) {
        if (!paymentRepository.existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
                request.getCardNumber(),
                request.getCardHolder(),
                request.getCardExpirationYear(),
                request.getCardExpirationMonth(),
                request.getCardCvv()
        )) {
            throw new RuntimeException("Card is invalid");
        }
    }

    private void checkIfPaymentExists(int id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Payment does not exist");
        }
    }

    private void checkIfCardExists(String cardNumber) {
        if (paymentRepository.existsByCardNumber(cardNumber)) {
            throw new RuntimeException("Card already in use");
        }
    }
}
