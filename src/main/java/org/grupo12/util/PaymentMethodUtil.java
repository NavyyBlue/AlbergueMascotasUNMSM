package org.grupo12.util;

import java.util.Map;

public class PaymentMethodUtil {
    private static final Map<Integer, String> paymentMethods = Map.of(
            1, "Yape",
            2, "Plin",
            3, "Tranferencia Bancaria"
    );

    public static String getPaymentMethodName(int paymentMethodId) {
        return paymentMethods.getOrDefault(paymentMethodId, "No especificado");
    }
}
