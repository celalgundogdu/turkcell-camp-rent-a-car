package com.turkcellcamp.rentacar.common.constants;

public class Messages {

    public static class Car {
        public static final String NOT_EXISTS = "Car does not exist";
        public static final String ALREADY_EXISTS = "Car already exists";
        public static final String NOT_AVAILABLE = "Car is not available";
        public static final String DUPLICATE_PLATE = "Plate already in use";
        public static final String PLATE_NOT_VALID = "Invalid plate pattern";
        public static final String MODEL_YEAR_NOT_VALID = "Invalid model year";
    }

    public static class Model {
        public static final String NOT_EXISTS = "Model does not exist";
        public static final String ALREADY_EXISTS = "Model already exists";
    }

    public static class Brand {
        public static final String NOT_EXISTS = "Brand does not exist";
        public static final String ALREADY_EXISTS = "Brand already exists";
    }

    public static class Maintenance {
        public static final String NOT_EXISTS = "Maintenance does not exist";
        public static final String CAR_ALREADY_UNDER_MAINTENANCE = "Car is already under maintenance";
        public static final String CAR_NOT_UNDER_MAINTENANCE= "Car is not under maintenance";
        public static final String CAR_RENTED = "Car can not be taken under maintenance as it has been rented";
    }

    public static class Rental {
        public static final String NOT_EXISTS = "Rental does not exist";
    }

    public static class Payment {
        public static final String NOT_EXISTS = "Payment does not exist";
        public static final String CARD_ALREADY_EXISTS = "Card already exists";
        public static final String NOT_ENOUGH_MONEY = "Insufficient balance";
        public static final String NOT_VALID_PAYMENT = "Payment is invalid";
        public static final String FAILED = "Payment failed";

    }

    public static class Invoice {
        public static final String NOT_EXISTS = "Invoice does not exist";
    }

    public static class Exception {
        public static final String VALIDATION = "Validation failed";
    }
}
