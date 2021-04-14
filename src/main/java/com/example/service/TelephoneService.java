package com.example.service;

import com.example.dao.ConnectionFactory;
import com.sample.Telephone;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Telephone Service
 * @author Jinkai Zhang
 */
public class TelephoneService {

    public Telephone addTelephone(Long contactId, String telephone) {
        try {
            return ConnectionFactory.executeWithGeneratedKey("insert into telephone(phone, contact_id) value (?,?) ", ps -> {
                ps.setString(1, telephone);
                ps.setLong(2, contactId);
                ps.executeUpdate();
                final ResultSet generatedKeys = ps.getGeneratedKeys();
                final Telephone tel = new Telephone();
                if (generatedKeys.next()) {
                    tel.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("You should use newly mysql jdbc driver");
                }
                tel.setPhone(telephone);
                tel.setContactId(contactId);
                return tel;
            });
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    public boolean deleteTelephone(Long telephoneId) {
        try {
            return ConnectionFactory.execute("delete from telephone where id=?", ps -> {
                ps.setLong(1, telephoneId);
                ps.executeUpdate();
                return true;
            });
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    public List<Telephone> getAllTelephone(Long contactId) {
        try {
            return ConnectionFactory.execute("select id, phone from telephone where contact_id=?", ps -> {
                ps.setLong(1, contactId);
                final ResultSet resultSet = ps.executeQuery();
                final ArrayList<Telephone> telephones = new ArrayList<>();
                while (resultSet.next()) {
                    final Telephone telephone = new Telephone();
                    telephone.setId(resultSet.getLong(1));
                    telephone.setPhone(resultSet.getString(2));
                    telephone.setContactId(contactId);
                    telephones.add(telephone);
                }
                return telephones;
            });
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return new ArrayList<>();
        }
    }
}
