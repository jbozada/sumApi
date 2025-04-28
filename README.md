# Sum API Project

## Descripción

Este proyecto expone una API REST para realizar operaciones de suma entre dos números para luego multiplicar ese resultado con un porcentaje recuperado de un servicio externo (utiliza la API `property-service` para obtener configuraciones dinámicas) y se almacena el historial de ejecuciones del endpoint de suma (se integra con Kafka para publicar este historial de forma asíncrona). Además, utiliza Redis y PostgreSQL para almacenamiento y cache.

## Requisitos previos

- GIT para clonar el repositorio
- Docker (para ejecutar los contenedores)
- Docker Compose
- Java 21 (para desarrollo)
- Maven (para compilar el proyecto)
- Asegurate de no tener ningún proxy o restricción que no le permita al docker compose levantar correctamente cuando descargue las dependencias de los servicios con maven.

## Arquitectura

La arquitectura del proyecto se basa en microservicios y usa Docker Compose para levantar todos los servicios de manera eficiente.

Los servicios principales del proyecto son:

1. **Sum API**: Exponer endpoints para sumar y ver historial.
2. **Property Service**: API para configuraciones dinámicas.
3. **Sum Processor**: Procesa mensajes de Kafka y los guarda en PostgreSQL.
4. **Kafka**: Comunicación asíncrona de resultados.
5. **Redis**: Cache de configuraciones para `property-service`.
6. **PostgreSQL**: Base de datos para historial.
7. **Kafka UI**: Interfaz para monitorear Kafka.

## Docker Compose

Todos los servicios se definen en `docker-compose.yml`.

**Orden de arranque:**

1. Zookeeper
2. Kafka
3. PostgreSQL
4. Redis
5. Property Service
6. Sum API
7. Sum Processor
8. Kafka UI

**Notas:**

- Esperar a que los contenedores estén "healthy" antes de usar la API.
- Swagger disponible en:
  - Sum API: `http://localhost:8081/swagger-ui`
  - Property Service: `http://localhost:8084/swagger-ui`

## Puertos locales necesarios

Para el correcto funcionamiento necesitas tener **libres** los siguientes puertos:

| Servicio         | Puerto Local |
| ---------------- | ------------ |
| PostgreSQL       | 5433         |
| Kafka            | 9092         |
| Zookeeper        | 2181         |
| Sum API          | 8081         |
| Sum Processor    | 8082         |
| Kafka UI         | 8083         |
| Redis            | 6379         |
| Property Service | 8084         |

### ¿Cómo liberar puertos ocupados?

**Linux / Mac:**

```bash
sudo lsof -i :<PUERTO>
sudo kill -9 <PID>
```

**Windows (en PowerShell o CMD):**

```powershell
netstat -ano | findstr :<PUERTO>
taskkill /PID <PID> /F
```

Reemplaza `<PUERTO>` por el número del puerto que quieres liberar, y `<PID>` por el identificador del proceso.

## Cómo probar el proyecto

1. **Clonar el repositorio, colocarte en una terminal y ejecutar:**

```bash
git clone https://github.com/jbozada/sumApi.git
cd sumApi
```

2. **Levantar los servicios:**

En la carpeta principal (`sumApi`), ejecuta:

```bash
# Para la mayoría de los entornos
docker-compose up --build -d

# Si tu Docker usa la versión moderna
# (por ejemplo Docker Desktop en Windows o Mac actualizados)
docker compose up --build -d
```

Esto construirá las imágenes necesarias y levantará todos los contenedores.

3. **Verificar logs de sum-api:**

Luego de levantar los servicios, ejecuta:

```bash
docker logs -f sum-api
```

Cuando veas el mensaje:

```
Started SumApiApplication in X seconds
```

podrás comenzar a usar la API.

---

## Endpoints de la API

### 1. `/api/sum`

- **Método**: `POST`
- **Host**: `http://localhost:8081/`
- **Cuerpo de la solicitud**:

```json
{
  "number1": 10,
  "number2": 20
}
```

- **Respuesta**:

```json
{
  "result": 30
}
```

### 2. `/api/history`

- **Método**: `GET`
- **Host**: `http://localhost:8081/`
- **Parámetros**:
  - `page`: (opcional)
  - `size`: (opcional)

Ejemplo:

```bash
GET /api/history?page=0&size=10
```

- **Respuesta**:

```json
{
  "content": [
    {
      "id": 1,
      "number1": 10,
      "number2": 20,
      "result": 30,
      "date": "2025-04-28T03:54:03"
    }
  ],
  "totalPages": 1,
  "totalElements": 1
}
```

### 3. `/api/properties/{key}`

- **Método**: `GET`
- **Host**: `http://localhost:8084/`

Ejemplo:

```bash
GET /api/properties/percentage
```

- **Respuesta**:

```json
{
  "key": "percentage",
  "value": 10
}
```
