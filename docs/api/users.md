# Users API

Base path: `/api/users`

---

## `GET /api/users/user/{id}`

Returns a `Users` entity by its primary ID.

**Path Parameter:** `id` — user primary ID (integer)

**Response:** `200 OK` — `Users` entity

---

## `POST /api/users/updateUser/{id}`

Enables a user account and records the current timestamp as `lastLogin`.

**Path Parameter:** `id` — user primary ID (integer)

**Response:** `200 OK` — `true` on success, `false` on error
