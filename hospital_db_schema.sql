CREATE DATABASE hospital_db;
USE hospital_db;

CREATE TABLE doctors (
    doctor_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE patients (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT,
    gender VARCHAR(10),
    phone_number VARCHAR(15),
    address VARCHAR(255)
);

CREATE TABLE appointments (
    appointment_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    appointment_time TIME NOT NULL,
    reason VARCHAR(255),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id)
);

show tables;

select * from doctors;
select * from patients;
select * from appointments;

desc appointments;
desc patients;
desc doctors;select current_user();

