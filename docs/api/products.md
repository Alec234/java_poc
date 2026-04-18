# Products API

Base path: `/api/products`

---

## `GET /api/products`

Returns a paginated, filtered list of products.

**Query Parameters:**

| Parameter | Type | Required | Default | Description |
|---|---|---|---|---|
| `name` | string | No | — | Case-insensitive partial match on product name |
| `inStock` | boolean | No | — | When `true`, returns only products with `stockQuantity > 0` |
| `minPrice` | double | No | — | Minimum price (inclusive) |
| `maxPrice` | double | No | — | Maximum price (inclusive) |
| `page` | int | No | `0` | Zero-based page number |
| `size` | int | No | `25` | Page size (minimum clamped to 1) |
| `sort` | string[] | No | `id,asc` | Sort fields in `field,direction` format. Supported fields: `id`, `name`, `price`, `description`, `stock` |

**Example request:**
```
GET /api/products?name=keyboard&inStock=true&minPrice=10&maxPrice=200&sort=price,desc
```

**Response:** `200 OK` — array of `ProductDTO`

```json
[
  {
    "productId": 1,
    "productName": "Mechanical Keyboard",
    "description": "Full-size mechanical keyboard",
    "price": 89.99,
    "stockQuantity": 12
  }
]
```
