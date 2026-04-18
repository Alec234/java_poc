# Orders API

Base path: `/api/orders`

---

## `POST /api/orders/createOrder`

Creates a new order in `Pending` status. All line items are saved as order items and the total amount is calculated automatically.

**Request Body:** `OrderDTO`

```json
{
  "customerId": 4,
  "orderItems": [
    {
      "productId": 99,
      "quantity": 2,
      "price": 15.00
    }
  ]
}
```

**Validation rules:**
- `orderItems` must be non-null and non-empty
- Each item must have a non-null `price` and a positive `quantity`

**Responses:**

| Status | Body | Condition |
|---|---|---|
| `200 OK` | `true` | Order and all line items saved |
| `200 OK` | `false` | Validation failed or exception thrown |

---

## `POST /api/orders/{id}/submitOrder`

Submits a pending order. Validates that all products in the order exist and have sufficient stock, then updates the order status to `Submitted`.

**Path Parameter:** `id` — order ID (integer)

**Responses:**

| Status | Body | Condition |
|---|---|---|
| `200 OK` | `"Order submitted successfully"` | Order transitioned to `Submitted` |
| `200 OK` | `"Order is already submitted"` | Order status was not `Pending` |
| `400 Bad Request` | `"Order validation failed"` | A product is missing or out of stock |
| `500 Internal Server Error` | Error message | Unexpected error |

---

## `POST /api/orders/{id}/cancel`

Cancels an order by updating its status to `Cancelled`.

**Path Parameter:** `id` — order ID (integer)

**Responses:**

| Status | Body | Condition |
|---|---|---|
| `200 OK` | `"Order cancelled successfully"` | Order cancelled |
| `400 Bad Request` | `"Cannot cancel an order that has already been shipped"` | Order status is `Shipped` |
| `404 Not Found` | `"Order not found"` | No order with the given ID |
| `500 Internal Server Error` | Error message | Unexpected error |

---

## `GET /api/orders`

Returns a filtered list of orders.

**Query Parameters:**

| Parameter | Type | Required | Description |
|---|---|---|---|
| `customerId` | integer | No | Filter by customer ID |
| `status` | string | No | Filter by order status (e.g., `Pending`, `Submitted`, `Shipped`, `Cancelled`, `refunded`, `partially refunded`) |
| `orderDate` | string | No | Filter to orders on or after this date |

**Response:** `200 OK` — array of `OrderDTO`

```json
[
  {
    "orderId": 5,
    "customerId": 4,
    "orderDate": "2026-02-15T10:00:00.000+00:00",
    "status": "Submitted",
    "totalAmount": 30.00
  }
]
```
