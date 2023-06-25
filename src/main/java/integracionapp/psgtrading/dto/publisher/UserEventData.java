package integracionapp.psgtrading.dto.publisher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEventData {
    public String SurveyId;
    public String Email;
    public Double VoteQuantity;
    public String Question;
    public String[] Answer;
}
