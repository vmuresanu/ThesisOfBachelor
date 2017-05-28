package com.victor.model;

import com.victor.model.enums.DocumentType;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by vmuresanu on 5/4/2017.
 */
@Entity
@Table(name = "USER_DOCUMENT")
public class UserDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @NotEmpty
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE", nullable = false)
    private DocumentType documentType;

    @Column(name = "NEED_NOTARY")
    private Boolean needNotary;

    @Column(name = "START_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
   // @Temporal(TemporalType.TIMESTAMP)
    private DateTime startDate;

    @Column(name = "DEADLINE")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
  //  @Temporal(TemporalType.TIMESTAMP)
    private DateTime deadline;

    @NotNull
    @Column(name = "PRICE")
    private Double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Boolean getNeedNotary() {
        return needNotary;
    }

    public void setNeedNotary(Boolean needNotary) {
        this.needNotary = needNotary;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate.plusHours(3);
    }

    public DateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(DateTime deadline) {
        this.deadline = deadline.plusHours(3);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDocument document = (UserDocument) o;

        return id.equals(document.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDocument{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", documentType=" + documentType +
                ", needNotary=" + needNotary +
                ", startDate=" + startDate +
                ", deadline=" + deadline +
                ", price=" + price +
                ", user=" + user +
                '}';
    }
}
