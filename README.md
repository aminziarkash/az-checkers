# Checkers Game API

A simple, turn-based Checkers game engine implemented in Java with Spring Boot.  
Players alternate moving a single piece (BLACK or WHITE) on an 8×8 board.

---

## Demo Board

_Squares are labeled for readability: files A–H (left to right) and ranks 1–8 (bottom to top)._


`.` = empty  
`B` = Black man  
`W` = White man  
`b`/`w` = kinged Black/White

---

## API Endpoints

### Base URL http://localhost:8080/api/


`{gameId}` is a UUID that identifies each game session.

### Endpoints

| Method | Path          | Description                               |
| ------ | ------------- | ----------------------------------------- |
| GET    | `/board`      | Return current board as JSON.             |
| GET    | `/board/text` | Return current board as ASCII text.       |
| GET    | `/player`     | Return whose turn it is (`BLACK`/`WHITE`).|
| POST   | `/move`       | Attempt a move (see body format below).   |
| POST   | `/reset`      | Reset the game to initial state.          |

#### Request & Response Formats

- **`GET /board`**
  ```json
  {
    "squares": [
      ["", "B", "", "B", "..."],
      [...],
      ...
    ]
  }


- **`GET /board/text`**
  ```text
      A B C D E F G H
     ----------------
  8 | . B . B . B . B | 8
  7 | B . B . B . B . | 7
  6 | . B . B . B . B | 6
  5 | . . . . . . . . | 5
  4 | . . . . . . . . | 4
  3 | W . W . W . W . | 3
  2 | . W . W . W . W | 2
  1 | W . W . W . W . | 1
     ----------------
      A B C D E F G H
  
  ```

- **`GET /player`**
  ```json
  { "message": "WHITE" }

- **`POST /move`**
  ```json
  {
    "from": "B6", 
    "to": "A5"
  }

- **`Making a move example`**
  ```text
  curl -X POST "http://localhost:8080/api/3fa85f64-5717-4562-b3fc-2c963f66afa6/move" \
     -H "Content-Type: application/json" \
     -d '{"from":"B6","to":"A5"}'

## Actuator
##### Actuator endpoints
  ```text
  http://localhost:8080/actuator
  
  http://localhost:8080/actuator/health
  
  http://localhost:8080/actuator/info 
  ```

## Docker
##### To run the application in a docker container
```text 
  1. From the rood directory, run: 
     mvn clean package -pl checkers-app -am -DskipTests 
     
     _This will produce something like: checkers-app/target/checkers-app-1.0.0.jar_
     
  2. From the root directory, run: docker build -t checkers-app:latest .
  
  3. To run the docker container, run: docker run --rm -p 8080:8080 checkers-app:latest
  
  4. App is running, you access the endpoints using POSTMAN or curl commands
```

## Swagger / OpenAPI
##### Interactive API docs & “Try it out”:

http://localhost:8080/swagger-ui/index.html