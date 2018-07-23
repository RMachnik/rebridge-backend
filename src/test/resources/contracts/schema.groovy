import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should respond with auth schema"

    request {
        url "/schemas/auth"
        method GET()
        headers {
            contentType applicationJsonUtf8()
        }
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
    }
}