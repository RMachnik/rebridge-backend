package questionnaireTemplates

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should createWithRoleArchitect project"

    request {
        url "/questionnaire/templates"
        method POST()
        headers {
            header 'Authorization': 'Bearer TOKEN'
            contentType applicationJsonUtf8()
        }
        body(
                "name": "testQuestionnaire",
                "questions": ["firstquestion", "second", "jak masz na imie?", "czy jestes kotem?"]
        )
    }

    response {
        status CREATED()
        headers {
            contentType applicationJson()
        }
        body(
                id: anyUuid(),
                name: "testQuestionnaire"
        )
    }
}