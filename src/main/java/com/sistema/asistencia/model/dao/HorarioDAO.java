package com.sistema.asistencia.model.dao;

import com.sistema.asistencia.config.ConexionBD;
import com.sistema.asistencia.model.entity.Horario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HorarioDAO {

    // 1. LISTAR HORARIOS CON INNER JOIN
    public List<Horario> listar() {
        List<Horario> lista = new ArrayList<>();
        String sql = "SELECT h.id_horario, h.id_seccion, h.dia, h.hora_inicio, h.hora_fin, s.codigo_seccion " +
                     "FROM horario h " +
                     "INNER JOIN seccion s ON h.id_seccion = s.id_seccion " +
                     "ORDER BY CASE h.dia " +
                     "  WHEN 'Lunes' THEN 1 WHEN 'Martes' THEN 2 WHEN 'Miércoles' THEN 3 " +
                     "  WHEN 'Jueves' THEN 4 WHEN 'Viernes' THEN 5 WHEN 'Sábado' THEN 6 END, h.hora_inicio ASC";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Horario h = new Horario();
                h.setIdHorario(rs.getInt("id_horario"));
                h.setIdSeccion(rs.getInt("id_seccion"));
                h.setDia(rs.getString("dia"));
                
                // Formateamos los objetos de tiempo de la base de datos a cadenas limpias (HH:mm)
                h.setHoraInicio(rs.getTime("hora_inicio").toString().substring(0, 5));
                h.setHoraFin(rs.getTime("hora_fin").toString().substring(0, 5));
                h.setCodigoSeccion(rs.getString("codigo_seccion"));
                
                lista.add(h);
            }
        } catch (SQLException ex) {
            System.err.println("Error en HorarioDAO.listar: " + ex.getMessage());
        }
        return lista;
    }

    // 2. REGISTRAR HORARIO (INSERT)
    public boolean registrar(Horario horario) {
        String sql = "INSERT INTO horario (id_seccion, dia, hora_inicio, hora_fin) VALUES (?, ?, CAST(? AS time), CAST(? AS time))";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, horario.getIdSeccion());
            ps.setString(2, horario.getDia());
            ps.setString(3, horario.getHoraInicio());
            ps.setString(4, horario.getHoraFin());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error en HorarioDAO.registrar: " + ex.getMessage());
            return false;
        }
    }

    // 3. MODIFICAR HORARIO (UPDATE)
    public boolean modificar(Horario horario) {
        String sql = "UPDATE horario SET id_seccion = ?, dia = ?, hora_inicio = CAST(? AS time), hora_fin = CAST(? AS time) WHERE id_horario = ?";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, horario.getIdSeccion());
            ps.setString(2, horario.getDia());
            ps.setString(3, horario.getHoraInicio());
            ps.setString(4, horario.getHoraFin());
            ps.setInt(5, horario.getIdHorario());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error en HorarioDAO.modificar: " + ex.getMessage());
            return false;
        }
    }

    // 4. ELIMINAR HORARIO (DELETE)
    public boolean eliminar(int idHorario) {
        String sql = "DELETE FROM horario WHERE id_horario = ?";
        
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            
            ps.setInt(1, idHorario);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error en HorarioDAO.eliminar: " + ex.getMessage());
            return false;
        }
    }
}