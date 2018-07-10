# rebridge-backend

VPS: vps563480.ovh.net


Created users: rmachnik, aprzybylski

Commands to run and  build

1. `gradle build`
2. `docker build -t rmachnik/rebridge-backend .`
3. `docker run -p 8080:8080 -d --rm rmachnik/rebridge-backend`
4. `http://localhost:8080`

### API
#####Authentication
Based on header token `Authentication: token-fb4c2d3a-3f62-44dd-a25f-2fbc73820649`.
Token is send back after successful login request.
+ `/auth/login` - user login: POST{username,password}
+ `/auth/logout` - user logout: GET
+ `/auth/register` - user registration: POST{username,password}, 
registration returns authentication token that can be used for subsequent interactions with API
+ `/users` - get current user
+ `/projects` - resource: GET, POST{projectName}, PUT(according to project schema), DELETE
+ `/projects/{projectId}/inspirations` - GET, POST{inspirationName}, PUT(according to inspiration schema), DELETE
+ `/projects/{projectId}/inspirations/{inspirationId}/comments`-
 GET, POST{content}, PUT(according to inspiration schema), DELETE

#####Json Schemas
+ `/schemas/users` - schema for user endpoint
+ `/schemas/projects` - schema for inspiration endpoint



