package com.example.contentproviderdivisas.Model;

// Importamos la clase Map de Java.util para usarla en nuestra clase Posts:
import java.util.Map;

// Esta clase representa una respuesta JSON de la API de conversión de divisas.
// Tiene varias propiedades, cada una de las cuales corresponde a una clave en el JSON.
public class Posts {
    private String result; // El resultado de la solicitud (por ejemplo, "éxito")
    private String documentation; // Un enlace a la documentación de la API
    private String terms_of_use; // Los términos de uso de la API
    private long time_last_update_unix; // El tiempo de la última actualización en formato UNIX
    private String time_last_update_utc; // El tiempo de la última actualización en formato UTC
    private long time_next_update_unix; // El tiempo de la próxima actualización en formato UNIX
    private String time_next_update_utc; // El tiempo de la próxima actualización en formato UTC
    private String base_code; // El código de la moneda base
    private Map<String, Double> conversion_rates; // Un mapa de tasas de cambio para diferentes monedas

    public String getResult() {
        return result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getTerms_of_use() {
        return terms_of_use;
    }

    public long getTime_last_update_unix() {
        return time_last_update_unix;
    }

    public String getTime_last_update_utc() {
        return time_last_update_utc;
    }

    public long getTime_next_update_unix() {
        return time_next_update_unix;
    }

    public String getTime_next_update_utc() {
        return time_next_update_utc;
    }

    public String getBase_code() {
        return base_code;
    }

    public Map<String, Double> getConversion_ratesonversions() {
        return conversion_rates;
    }
}