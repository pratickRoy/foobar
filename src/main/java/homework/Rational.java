package homework;

public class Rational {

    private final int numerator;
    private final int denominator;

    public Rational(double floatingNumber) {

        double tolerance = 1.0E-6;

        double numerator = 1;
        double tempNumerator = 0;

        double denominator = 0;
        double tempDenominator = 1;

        double approximateFloatingNumber = floatingNumber;

        do {

            double integralNumber = Math.floor(approximateFloatingNumber);

            double temp = numerator;
            numerator = (integralNumber * numerator) + tempNumerator;
            tempNumerator = temp;

            temp = denominator;
            denominator = (integralNumber * denominator) + tempDenominator;
            tempDenominator = temp;

            approximateFloatingNumber = 1 / (approximateFloatingNumber - integralNumber);

        } while (Math.abs(floatingNumber - (numerator / denominator)) > (floatingNumber * tolerance));

        this.numerator = (int) numerator;
        this.denominator = (int) denominator;
    }

    public static void main(String[] args) {

        Rational r = new Rational(6.66667);
        System.out.println(r.numerator+"/"+r.denominator);
    }
}