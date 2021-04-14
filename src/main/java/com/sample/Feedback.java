package com.sample;

/**
 * represent a feedback
 * @author Jinkai Zhang
 */
public class Feedback {

  private long id;
  private java.sql.Date releaseDate;
  private String title;
  private String content;
  private long personId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public java.sql.Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(java.sql.Date releaseDate) {
    this.releaseDate = releaseDate;
  }


  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public long getPersonId() {
    return personId;
  }

  public void setPersonId(long personId) {
    this.personId = personId;
  }

}
