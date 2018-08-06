package auth

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should register user"

    request {
        url "/auth/register"
        method POST()
        headers {
            contentType applicationJsonUtf8()
        }
        body(
                "email": "zdenek@mail.com",
                "password": "pass"
        )
    }

    response {
        status CREATED()
        headers {
            contentType applicationJson()
        }
        body(
                "token": anyUuid()
        )
    }
}