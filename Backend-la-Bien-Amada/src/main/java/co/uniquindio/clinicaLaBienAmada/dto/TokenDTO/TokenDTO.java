package co.uniquindio.clinicaLaBienAmada.dto.TokenDTO;

import jakarta.validation.constraints.NotBlank;

public record TokenDTO(
        @NotBlank
        String token
         ){
}
