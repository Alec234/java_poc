# Payments API

Base path: `/api/payments`

---

## `POST /api/payments/capture`

Captures a payment for an order. Prevents duplicate payments — returns `400` if a payment already exists for the given order. The payment amount is calculated from the order's line items.

**Request Body:** `PaymentDTO`

```json
{
  "orderId": 5,
  "paymentMethod": "Visa"
}
```

**Responses:**

| Status | Body | Condition |
|---|---|---|
| `200 OK` | `"Payment captured"` | Payment saved |
| `400 Bad Request` | `"Payment already exists for this order"` | Duplicate payment attempt |
| `500 Internal Server Error` | Error message | Unexpected error |

---

## `POST /api/payments/refund`

Refunds a payment partially or fully. Deducts the refund amount from the payment record and updates the associated order status to `refunded` or `partially refunded`.

**Query Parameter:** `paymentId` — payment ID (integer)

**Request Body:** Array of `ProductDTO` representing items to refund. The refund amount is computed as `price × stockQuantity` for each product in the list.

```json
[
  {
    "productId": 1,
    "productName": "Mouse",
    "description": "Wireless",
    "price": 10.00,
    "stockQuantity": 2
  }
]
```

**Responses:**

| Status | Body | Condition |
|---|---|---|
| `200 OK` | `"Payment refunded"` | Refund processed |
| `500 Internal Server Error` | Error message | Payment not found, or refund exceeds original amount |

---

## `POST /api/payments/authorizePayment`

Placeholder endpoint. Returns `"Payment authorized"` without persisting any data.
