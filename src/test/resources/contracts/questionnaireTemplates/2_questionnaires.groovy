package questionnaireTemplates

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should findById projects"

    request {
        url "/questionnaire/templates"
        method GET()
        headers {
            header 'Authorization': 'Bearer TOKEN'
            contentType applicationJsonUtf8()
        }
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(


                [
                        id  : anyUuid(),
                        name: "testQuestionnaire",
                ]

        )
    }
}
