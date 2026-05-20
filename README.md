# Productos Service

Microservicio de catálogo de productos del sistema e-commerce.

## Información general

| Campo | Valor |
|-------|-------|
| Puerto | `8081` |
| Base de datos | `db_productos` (PostgreSQL) |
| Contexto | `/api/productos` |

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/productos` | Listar todos los productos |
| `GET` | `/api/productos/{id}` | Obtener producto por ID |
| `POST` | `/api/productos` | Crear producto |
| `PUT` | `/api/productos/{id}` | Actualizar producto |
| `DELETE` | `/api/productos/{id}` | Eliminar producto |

## Ejemplo de uso

**Crear producto:**
```bash
curl -X POST http://localhost:8081/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Gaming",
    "description": "RTX 4090, 32GB RAM",
    "price": 1499.99
  }'
```

**Respuesta:**
```json
{
  "id": 1,
  "name": "Laptop Gaming",
  "description": "RTX 4090, 32GB RAM",
  "price": 1499.99
}
```

## Modelo de datos

```sql
CREATE TABLE products (
    id          BIGINT         GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description VARCHAR(1000),
    price       NUMERIC(19, 2) NOT NULL
);
```

## Dependencias externas

Ninguna. Servicio autónomo.

> Este servicio es consumido por: **inventory**, **orders**, **reviews**, **whitelist**, **cart**.

## Configuración (variables de entorno Docker)

| Variable | Descripción |
|----------|-------------|
| `SPRING_DATASOURCE_URL` | URL de conexión a PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | Usuario de la base de datos |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de la base de datos |

## Tecnologías

- Java 25 · Spring Boot 4.0.6
- Spring Data JPA · Hibernate 7
- Flyway (migraciones)
- PostgreSQL 16
- Lombok · Bean Validation 
