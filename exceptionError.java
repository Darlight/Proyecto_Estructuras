/*
Universidad del Valle de Guatemala
Estructura de datos - Seccion 10
traductorLisp.java
Proposito: Traduce la sintaxis de Lisp a String, ayuda al Parser
 */

import java.util.*;

/**
 * Clase para errores básicos
 */
public class exceptionError extends Exception {
    //crear setter y getter
    private String error;

    /**
     * Formato de errores más básico
     * @param errorMessage
     * @param errorType
     */
    public exceptionError(String errorMessage, String errorType) {
        // Crea el error necesario de tipo y el mensaje para el usuario
        error = "** " + errorType + " Error in the input expression: ";
        error = error + errorMessage;
    }

    // Lo imprime hacia la pantalla
    public void printErrorMessage() {
        System.out.println(error);
    }

}