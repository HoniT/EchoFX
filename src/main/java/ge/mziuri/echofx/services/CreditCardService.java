package ge.mziuri.echofx.services;

public class CreditCardService {
    // Luhn algorithm implementation
    public static boolean isValidCardNumber(String number) {
        int sum = 0;
        boolean alternate = false;

        // Loop from the end of the card number
        for (int i = number.length() - 1; i >= 0; i--) {
            char c = number.charAt(i);

            if (!Character.isDigit(c)) {
                return false;  // Invalid character
            }

            int n = c - '0';
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }

        return (sum % 10 == 0);
    }}
