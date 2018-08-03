package domain.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import static io.netty.util.internal.StringUtil.EMPTY_STRING;

@UserDefinedType
@Data
@AllArgsConstructor
public class Question {

    int id;
    String question;
    String answer;

    static Question notAnswered(int id, String question) {
        return new Question(id, question, EMPTY_STRING);
    }

    public void update(String userAnswer) {
        this.answer = userAnswer != null ? userAnswer : answer;
    }
}
