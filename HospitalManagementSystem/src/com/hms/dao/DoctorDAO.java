package com.hms.dao;

import com.hms.model.Doctor;
import com.hms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {

    public void addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (name, specialization, phone_number, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getSpecialization());
            pstmt.setString(3, doctor.getPhoneNumber());
            pstmt.setString(4, doctor.getEmail());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                doctor.setDoctorId(rs.getInt(1)); // Set the auto-generated ID back to the object
            }
            System.out.println("Doctor added successfully: " + doctor.getName());
        } catch (SQLException e) {
            System.err.println("Error adding doctor: " + e.getMessage());
        }
    }

    public Doctor getDoctorById(int doctorId) {
        String sql = "SELECT * FROM doctors WHERE doctor_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Doctor(
                    rs.getInt("doctor_id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("phone_number"),
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting doctor by ID: " + e.getMessage());
        }
        return null;
    }

    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                doctors.add(new Doctor(
                    rs.getInt("doctor_id"),
                    rs.getString("name"),
                    rs.getString("specialization"),
                    rs.getString("phone_number"),
                    rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all doctors: " + e.getMessage());
        }
        return doctors;
    }

    public void updateDoctor(Doctor doctor) {
        String sql = "UPDATE doctors SET name = ?, specialization = ?, phone_number = ?, email = ? WHERE doctor_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, doctor.getName());
            pstmt.setString(2, doctor.getSpecialization());
            pstmt.setString(3, doctor.getPhoneNumber());
            pstmt.setString(4, doctor.getEmail());
            pstmt.setInt(5, doctor.getDoctorId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Doctor updated successfully: " + doctor.getName());
            } else {
                System.out.println("No doctor found with ID: " + doctor.getDoctorId());
            }
        } catch (SQLException e) {
            System.err.println("Error updating doctor: " + e.getMessage());
        }
    }

    public void deleteDoctor(int doctorId) {
        String sql = "DELETE FROM doctors WHERE doctor_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, doctorId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Doctor with ID " + doctorId + " deleted successfully.");
            } else {
                System.out.println("No doctor found with ID: " + doctorId);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting doctor: " + e.getMessage());
            System.err.println("Note: Cannot delete doctor if there are existing appointments for them.");
        }
    }
}