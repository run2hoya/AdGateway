package com.castis.adgateway.model;

import com.castis.adgateway.dto.csis.Notification;
import com.castis.adgateway.util.CryptoUtil;
import com.castis.adlib.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Entity
@Table(name = "tbl_tracking")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Tracking {
    @Id
    @Column(name="trackingId")
    private String trackingId;

    @Column(name="startEvent", columnDefinition="TEXT" )
    private String startEvent;

    @Column(name="completeEvent", columnDefinition="TEXT")
    private String completeEvent;

    @Column(name="clickEvent", columnDefinition="TEXT")
    private String clickEvent;


    @Column(name="registerDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime registerDate;

    public Tracking(String trackingId, String startEvent, String completeEvent, String clickEvent) {
        this.trackingId = trackingId;
        this.startEvent = startEvent;
        this.completeEvent = completeEvent;
        this.clickEvent = clickEvent;
    }
}