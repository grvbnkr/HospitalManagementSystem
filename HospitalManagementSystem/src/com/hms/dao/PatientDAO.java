
package com.hms.dao;

import com.hms.model.Patient;
import com.hms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public void addPatient(Patient patient) {
        String sql = "INSERT INTO patients (name, age, gender, phone_number, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        	
            pstmt.setString(1, patient.getName());
            pstmt.setInt(2, patient.getAge());
            pstmt.setString(3, patient.getGender());
            pstmt.setString(4, patient.getPhoneNumber());
            pstmt.setString(5, patient.getAddress());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                patient.setPatientId(rs.getInt(1));
            }
            System.out.println("Patient added successfully: " + patient.getName());
        } catch (SQLException e) {
            System.err.println("Error adding patient: " + e.getMessage());
        }
    }

    public Patient getPatientById(int patientId) {
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Patient(
                    rs.getInt("patient_id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("phone_number"),
                    rs.getString("address")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting patient by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(new Patient(
                    rs.getInt("patient_id"),
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("gender"),
                    rs.getString("phone_number"),
                    rs.getString("address")
                ));
             }
        } catch (SQLException e) {
            System.err.println("Error getting all patients: " + e.getMessage());
        }
        return patients;
    }

    public void updatePatient(Patient patient) {
        String sql = "UPDATE patients SET name = ?, age = ?, gender = ?, phone_number = ?, address = ? WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
 
        	pstmt.setString(1, patient.getName());
            pstmt.setInt(2, patient.getAge());
            pstmt.setString(3, patient.getGender());
            pstmt.setString(4, patient.getPhoneNumber());
            pstmt.setString(5, patient.getAddress());
            pstmt.setInt(6, patient.getPatientId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient updated successfully: " + patient.getName());
            } else {
                System.out.println("No patient found with ID: " + patient.getPatientId());
            }
        } catch (SQLException e) {
            System.err.println("Error updating patient: " + e.getMessage());
        }
    }

    public void deletePatient(int patientId) {
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Patient with ID " + patientId + " deleted successfully.");
            } else {
                System.out.println("No patient found with ID: " + patientId);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting patient: " + e.getMessage());
            System.err.println("Note: Cannot delete patient if there are existing appointments for them.");
        }
    }
}