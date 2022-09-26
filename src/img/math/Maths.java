package img.math;

public class Maths {
    public static final float EPSILON = 1.0f / 256.0f;

    /**
     * Compara dois floats e os considera iguais se sua diferença for menor que o intervalo de erro (epsilon)
     * @param v1 float 1
     * @param v2 float 2
     * @param epsilon Intervalo de erro
     * @return true se os dois floats forem considerados iguais
     */
    public static boolean floatEquals(float v1, float v2, float epsilon) {
        return Math.abs(v1 - v2) <= epsilon;
    }

    /**
     * Compara dois floats e os considera iguais se sua diferença for menor que o intervalo de erro dado por EPSILON.
     * @param v1 float 1
     * @param v2 float 2
     * @return true se os dois floats forem considerados iguais
     */
    public static boolean floatEquals(float v1, float v2) {
        return floatEquals(v1, v2, EPSILON);
    }

    /**
     * Garante que o valor estará dentro do intervalo definido por min e max. Caso o valor esteja fora do intervalo,
     * o extremo mais próximo será usado. Caso contrário, o próprio valor é retornado.
     *
     * @param v Valor
     * @param min Valor mínimo do intervalo
     * @param max Valor máximo do intervalo
     * @return O valor ajustado
     */
    public static float clamp(float v, float min, float max) {
        return v < min ? min : (v > max ? max : v);
    }
}
