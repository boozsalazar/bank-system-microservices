package com.bank.clientes.validation;

import com.bank.clientes.entity.enums.TipoDocumento;

public class DocumentoValidator {

    public static boolean esValido(
        TipoDocumento tipo, String numero){
        return switch (tipo) {
            case DNI ->
                numero.matches("\\d{8}");
            case CARNET_EXTRANJERIA ->
                numero.matches("[A-Za-z0-9]{9}");
            case PASAPORTE ->
                numero.matches("[A-Za-z0-9]{6,12}");
        };
    }
}
