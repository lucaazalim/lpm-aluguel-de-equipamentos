package xhr;

public class Test {

    public static void main(String[] args) {

        double valorOriginalDiaria = 100.0; // Valor da diária inicial
        int numeroDeDiasAluguel = 7; // Número total de dias de aluguel
        double totalCustoAluguel = 0.0;
        int contadorPeriodo = 0;

        for (int dia = 1; dia <= numeroDeDiasAluguel; dia++) {
            if (contadorPeriodo == 3) {
                valorOriginalDiaria *= 1.15; // Aumenta o valor da diária em 15%
                contadorPeriodo = 0; // Zera o contador do período
            }

            totalCustoAluguel += valorOriginalDiaria;
            contadorPeriodo++;
        }

        System.out.println("O custo total do aluguel é: R$" + totalCustoAluguel);
    }

}
