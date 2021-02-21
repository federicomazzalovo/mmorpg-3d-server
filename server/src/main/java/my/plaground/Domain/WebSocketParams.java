package my.plaground.Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketParams {
    private int characterId;
    private double positionX;
    private double positionY;
}
