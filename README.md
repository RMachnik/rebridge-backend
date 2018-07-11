# rebridge-backend

VPS: vps563480.ovh.net


Created users: rmachnik, aprzybylski

Commands to run and  build

1. `gradle build`
2. `docker build -t rmachnik/rebridge-backend .`
3. `docker run -p 8080:8080 -d --rm rmachnik/rebridge-backend`
4. `http://localhost:8080/schemas/users`

### API
#####Authentication
Based on Bearer token `Authentication: Bearer fb4c2d3a-3f62-44dd-a25f-2fbc73820649`.
Token is send back after successful login request.
+ `/auth/login` - user login: POST{"username":"name","password":"pass"}
+ `/auth/logout` - user logout: GET
+ `/auth/register` - user registration: POST{"username":"name","password":"pass"}, 
registration returns authentication token that can be used for subsequent interactions with API
+ `/users/current` - GET current user
+ `/projects` - resource: GET, POST{"name","projectName"}, PUT(according to project schema), DELETE
+ `/projects/{projectId}/inspirations` - GET, POST{"name":"insName"}, PUT(according to inspiration schema), DELETE
+ `/projects/{projectId}/inspirations/{inspirationId}/comments`-
 GET, POST{content}, PUT(according to inspiration schema), DELETE

#####Json Schemas
+ `/schemas/users` - schema for user endpoint
+ `/schemas/projects` - schema for inspiration endpoint



