package my.plaground.Domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("moveDirection")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private MoveDirection moveDirection;
    private double hp;

    private int targetId;
    @JsonProperty("actionType")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private ActionType actionType;

    private double initHp;
    private int level;

    @JsonProperty("isConnected")
    private boolean isConnected;
    @JsonProperty("classId")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private CharacterClass classId;
}
