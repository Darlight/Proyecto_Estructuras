/*
Universidad del Valle de Guatemala
Seccion - 10
Autores:
Andrés Quan Littow       17652
Mario Andrés             18029
Josué Sagastume          18173
exceptionError.java
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
     * @param errorMessage Tira el mensaje de error en consola
     * @param errorType Identifica que tipo de error es
     */
    public exceptionError(String errorMessage, String errorType) {
        // Crea el error necesario de tipo y el mensaje para el usuario
        error = "** " + errorType + " Error in the input expression: ";
        error = error + errorMessage;
    }

    /**
     * Solo imprime el mensaje
     */
    // Lo imprime hacia la pantalla
    public void printErrorMessage() {
        System.out.println(error);
    }

}