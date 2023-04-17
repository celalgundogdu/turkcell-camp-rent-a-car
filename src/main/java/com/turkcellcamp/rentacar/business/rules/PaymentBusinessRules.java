package com.turkcellcamp.rentacar.business.rules;

import com.turkcellcamp.rentacar.common.constants.Messages;
import com.turkcellcamp.rentacar.common.dto.CreateRentalPaymentRequest;
import com.turkcellcamp.rentacar.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentBusinessRules {

    private final PaymentRepository paymentRepository;

    public void checkIfPaymentExists(int id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException(Messages.Payment.NOT_EXISTS);
        }
    }

    public void checkIfCardExists(String cardNumber) {
        if (paymentRepository.existsByCardNumber(cardNumber)) {
            throw new RuntimeException(Messages.Payment.CARD_ALREADY_EXISTS);
        }
    }

    public void checkIfPaymentIsValid(CreateRentalPaymentRequest request) {
        if (!paymentRepository.existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(
                request.getCardNumber(),
                request.getCardHolder(),
                request.getCardExpirationYear(),
                request.getCardExpirationMonth(),
                request.getCardCvv()
        )) {
            throw new RuntimeException(Messages.Payment.NOT_VALID_PAYMENT);
        }
    }

    public void checkIfBalanceIsEnough(double price, double balance) {
        if (price > balance) {
            throw new RuntimeException(Messages.Payment.NOT_ENOUGH_MONEY);
        }
    }
}
