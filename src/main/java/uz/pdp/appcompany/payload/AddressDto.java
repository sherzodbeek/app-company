package uz.pdp.appcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotNull(message = "Street shouldn't be empty!")
    private String street;

    @NotNull(message = "Home number shouldn't be empty")
    private String homeNumber;
}
