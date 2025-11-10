// src/app/models/auth.model.ts

/**
 * Represents the payload for login or registration.
 */
export interface AuthRequest {
  email: string;
  password: string;
  firstName?: string;
  lastName?: string;
}

/**
 * Represents the response returned from login/register endpoints.
 */
export interface AuthResponse {
  token: string;
  userId: string;
  firstName: string;
  lastName: string;
  email: string;
}
