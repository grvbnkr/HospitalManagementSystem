package com.hms.dao;

import com.hms.model.Appointment;
import com.hms.util.DBConnection;

import java.sql.*;
//import java.time.LocalDate;
//import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, appointment_time, reason) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
           
        	pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            
            pstmt.setDate(3, Date.valueOf(appointment.getAppointmentDate())); 
            pstmt.setTime(4, Time.valueOf(appointment.getAppointmentTime())); 
            
            pstmt.setString(5, appointment.getReason());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                appointment.setAppointmentId(rs.getInt(1));
            }
            System.out.println("Appointment scheduled successfully!");
        } catch (SQLException e) {
            System.err.println("Error scheduling appointment: " + e.getMessage());
        }
    }

    public Appointment getAppointmentById(int appointmentId) {
        String sql = "SELECT * FROM appointments WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Appointment(
                    rs.getInt("appointment_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    // Convert java.sql.Date to LocalDate
                    rs.getDate("appointment_date").toLocalDate(), 
                    // Convert java.sql.Time to LocalTime
                    rs.getTime("appointment_time").toLocalTime(), 
                    rs.getString("reason")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting appointment by ID: " + e.getMessage());
        }
        return null;
    }

    
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        // Use JOIN to get patient and doctor names for better display
        String sql = "SELECT a.appointment_id, a.patient_id, p.name AS patient_name, " +
                     "a.doctor_id, d.name AS doctor_name, a.appointment_date, a.appointment_time, a.reason " +
                     "FROM appointments a " +
                     "JOIN patients p ON a.patient_id = p.patient_id " +
                     "JOIN doctors d ON a.doctor_id = d.doctor_id " +
                     "ORDER BY a.appointment_date, a.appointment_time";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                
                appointments.add(new Appointment(
                    rs.getInt("appointment_id"),
                    rs.getInt("patient_id"),
                    rs.getInt("doctor_id"),
                    rs.getDate("appointment_date").toLocalDate(),
                    rs.getTime("appointment_time").toLocalTime(),
                    rs.getString("reason")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all appointments: " + e.getMessage());
        }
        return appointments;
    }

    public void updateAppointment(Appointment appointment) {
        String sql = "UPDATE appointments SET patient_id = ?, doctor_id = ?, appointment_date = ?, appointment_time = ?, reason = ? WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setDate(3, Date.valueOf(appointment.getAppointmentDate()));
            pstmt.setTime(4, Time.valueOf(appointment.getAppointmentTime()));
            pstmt.setString(5, appointment.getReason());
            pstmt.setInt(6, appointment.getAppointmentId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Appointment updated successfully!");
            } else {
                System.out.println("No appointment found with ID: " + appointment.getAppointmentId());
            }
        } catch (SQLException e) {
            System.err.println("Error updating appointment: " + e.getMessage());
        }
    }

    public void deleteAppointment(int appointmentId) {
        String sql = "DELETE FROM appointments WHERE appointment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, appointmentId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Appointment with ID " + appointmentId + " cancelled successfully.");
            } else {
                System.out.println("No appointment found with ID: " + appointmentId);
            }
        } catch (SQLException e) {
            System.err.println("Error cancelling appointment: " + e.getMessage());
        }
    }
}