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
                "questionnaireTemplateId": "128cb75a-3b8b-4c3f-b0e7-64e9ec10f467"
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
                questionnaireTemplateId: "128cb75a-3b8b-4c3f-b0e7-64e9ec10f467"
        )
    }
}