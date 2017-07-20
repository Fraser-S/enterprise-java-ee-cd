package com.qa.cd.business;

public interface CDService {
    String getAllCDs();

    String createCD(String cd);

    String deleteCD(Long id);
}
