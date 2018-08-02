package project

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should findById projects"

    request {
        url "/projects"
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
                        id  : anyNonBlankString(),
                        name: "newProject",
                ]
        )
    }
}
