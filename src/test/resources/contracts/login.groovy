import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should login user"

    request {
        url "/auth/login"
        method POST()
        headers {
            contentType applicationJsonUtf8()
        }
        body(
                "email": "test@email.com",
                "password": "password"
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