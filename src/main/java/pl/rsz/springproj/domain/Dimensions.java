package pl.rsz.springproj.domain;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dimensions implements Serializable {

    private Float width;
    private Float height;
    private Float depth;
}