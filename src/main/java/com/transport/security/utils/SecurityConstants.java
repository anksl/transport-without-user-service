package com.transport.security.utils;

public class SecurityConstants {
    public static final String SECRET = "iQ+WEoteNbCOH42/8fpM2ygfosnEAtQEXzwIhZsPJvY=";
    public static final String ROLES = "ROLES";
    public static final String ROLE = "ROLE_";
    public static final long EXPIRATION_TIME = 86_400_000; // 1 day
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String EMPTY = "";
    public static final String[] TRANSPORTER_ENDPOINTS = new String[]
            {
                    "/api/transportations/current",
                    "/api/transportations/period",
                    "/api/transportations/income",
                    "/api/transportations/fuelCost",
                    "/api/transportations/fuelConsumption",
                    "/api/transportations/distance"
            };
}
