package com.bank.clientes.validation;

import com.bank.clientes.entity.enums.TipoDocumento;

public class DocumentoValidator {

    public DocumentoValidator(){}

    public static boolean esValido(TipoDocumento tipoDocumento, String numeroDocumento){
        return switch (tipoDocumento){
            case DNI ->
                numeroDocumento.matches("\\d{8}");
            case CARNET_EXTRANJERIA ->
                numeroDocumento.matches("[A-Za-z0-9]{9}");
            case PASAPORTE ->
                numeroDocumento.matches("[A-Za-z0-9]{6,12}");
        };
    }
}
