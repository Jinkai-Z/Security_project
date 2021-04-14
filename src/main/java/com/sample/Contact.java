package com.sample;

/**
 *Represent  a contact
 * @author Jinkai Zhang
 */
public class Contact {

  private long id;
  private String name;
  private long personId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }

}
