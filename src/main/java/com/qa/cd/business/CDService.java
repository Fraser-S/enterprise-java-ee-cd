package com.qa.cd.business;

public interface CDService {

    String getCD(Long id);

    String getCD(String title);

    String getAllCDs();

    String createCD(String cd);

    String deleteCD(Long id);

    String deleteAllCDs();

    String editCD(Long id, String cd);
}
