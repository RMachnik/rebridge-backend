package createProject

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
                "name": "newProject"
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
        )
    }
}