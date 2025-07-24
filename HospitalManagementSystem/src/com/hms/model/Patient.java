
package com.hms.model;

public class Patient {
    private int patientId;
    private String name;
    private int age;
    private String gender;
    private String phoneNumber;
    private String address;

    // Constructor used when you are retrieving an existing record
    public Patient(int patientId, String name, int age, String gender, String phoneNumber, String address) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Constructor without ID is used when you are creating a new record 
    public Patient(String name, int age, String gender, String phoneNumber, String address) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "ID: " + patientId + ", Name: " + name + ", Age: " + age +
               ", Gender: " + gender + ", Phone: " + phoneNumber + ", Address: " + address;
    }
}