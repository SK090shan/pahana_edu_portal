package com.pahana.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {

    private WebDriver driver;
    // IMPORTANT: Make sure this URL is exactly correct for your project
    private final String BASE_URL = "http://localhost:8080/BillingSyatem/"; 

    @BeforeEach
    void setUp() {
       
        driver = new ChromeDriver();
      
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 

    @Test
    void testAdminLoginSuccess() {
        driver.get(BASE_URL + "login.jsp");
        
        // Find elements by their 'id' attributes (ensure these IDs exist on your login.jsp)
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.tagName("button"));
        
        // !!! IMPORTANT: Use the actual credentials for your ACTIVE admin user !!!
        usernameField.sendKeys("thisari1"); 
        passwordField.sendKeys("1234"); 
        loginButton.click();
        
        // Verify that the login was successful
        assertTrue(driver.getCurrentUrl().endsWith("/dashboard"), "User should be redirected to the dashboard.");
        
        WebElement dashboardTitle = driver.findElement(By.tagName("h1"));
        assertEquals("Dashboard", dashboardTitle.getText(), "Dashboard title should be visible after login.");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}