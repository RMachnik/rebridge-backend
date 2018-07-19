package domain.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType
@Data
@AllArgsConstructor
public class Question {
    String question;
    String answer;

    public void update(String userAnswer) {
        this.answer = userAnswer != null ? userAnswer : answer;
    }
}
