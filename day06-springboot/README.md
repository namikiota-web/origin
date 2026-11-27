WEBEDGE@DESKTOP-B3KKR6T UCRT64 /c/研修/training/demo
$ curl -i -X POST http://localhost:8080/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "buy milk"}'
HTTP/1.1 201
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 27 Nov 2026 09:01:33 GMT

{"id":4,"title":"buy milk","done":false}
WEBEDGE@DESKTOP-B3KKR6T UCRT64 /c/研修/training/demo
$ curl http://localhost:8080/todos
[{"id":4,"title":"buy milk","done":false}]
WEBEDGE@DESKTOP-B3KKR6T UCRT64 /c/研修/training/demo
$ curl -i -X PATCH http://localhost:8080/todos/4/toggle
HTTP/1.1 200
Content-Type: application/json
Transfer-Encoding: chunked
Date: Fri, 27 Nov 2026 09:02:09 GMT

{"id":4,"title":"buy milk","done":true}
