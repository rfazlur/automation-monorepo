package com.femaledaily.qa.api.models;

// ponytail: record cukup untuk DTO serialization. Tambah builder jika field > 5.
public record LoginRequest(String username, String password) {}
