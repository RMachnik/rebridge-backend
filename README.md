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

#####Json Schemas
+ `/schemas/projects` - schema for inspiration endpoint



