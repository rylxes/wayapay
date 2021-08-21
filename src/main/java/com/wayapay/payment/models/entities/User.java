package com.wayapay.payment.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User extends AbstractAuditingEntity implements Serializable {
    static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;


    private String fullName;
    private String username;
    private String email;
    private String mobile;
    private Timestamp accountVerifiedOn;
    private String password;
    private Timestamp passwordCreatedOn;
    private String pin;
    private Timestamp lastFailedPinAuthDate;
    private Timestamp lastPinAuthDate;
    private Integer failedPinAuthAttempts;
    private Timestamp pinCreatedOn;
    private Boolean passwordChangeRequired;
    private Integer loginCount;
    private Integer loginFailedAttempts;
    private Boolean locked;
    private Timestamp lastLoginDate;
    private Timestamp lastFailedLoginDate;
    private String profileID;
    private String profilePicture;
    private String userType;
    private Boolean deviceLockedToProfile;
    private String deviceId;
    private String deviceType;


}
