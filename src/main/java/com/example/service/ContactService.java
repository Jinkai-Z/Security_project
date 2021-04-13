package com.example.service;

import com.example.dao.ConnectionFactory;
import com.sample.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContactService {
    public Contact addContact(long personId, String contactName) {
        try {
            return ConnectionFactory.executeWithGeneratedKey("insert into contact (name, person_id) values (?,?);", ps -> {
                ps.setString(1, contactName);
                ps.setLong(2, personId);
                ps.executeUpdate();
                final ResultSet keys = ps.getGeneratedKeys();
                final Contact contact = new Contact();
                if (keys.next()) {
                    contact.setId(keys.getLong(1));
                } else {
                    throw new SQLException("Database not work correctly");
                }
                contact.setName(contactName);
                contact.setPersonId(personId);
                return contact;
            });
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    public boolean deleteContact(long contactId) {
        try {
            return ConnectionFactory.execute(connection -> {
                final PreparedStatement statement = connection.prepareStatement("delete from telephone where contact_id=?");
                statement.setLong(1, contactId);
                statement.executeUpdate();

                final PreparedStatement statement1 = connection.prepareStatement("delete from contact where id=?");
                statement1.setLong(1, contactId);
                statement1.executeUpdate();
                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Contact> queryContact(String personId) {
        return queryContact(Long.parseLong(personId));
    }

    public List<Contact> queryContact(long personId) {
        try {
            return ConnectionFactory.execute("select id,name from contact where person_id=?", ps -> {
                final ArrayList<Contact> contacts = new ArrayList<>();
                ps.setLong(1, personId);
                final ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    final Contact contact = new Contact();
                    contact.setId(resultSet.getLong(1));
                    contact.setName(resultSet.getString(2));
                    contact.setPersonId(personId);
                    contacts.add(contact);
                }
                return contacts;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return new ArrayList<>();
        }

    }
}
