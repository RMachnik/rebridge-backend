package project

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should createWithRoleArchitect project"

    request {
        url "/projects"
        method POST()
        headers {
            header 'Authorization': 'Bearer TOKEN'
            contentType applicationJsonUtf8()
        }
        body(
                "name": "newProject",
                "questionnaireTemplateId": UUID.randomUUID()
        )
    }

    response {
        status CREATED()
        headers {
            contentType applicationJson()
        }
        body(
                id: anyUuid(),
                name: "newProject",
                questionnaireTemplateId: anyUuid()
        )
    }
}