package com.castis.adgateway.model;

import com.castis.adgateway.dto.csis.Asset;
import com.castis.adgateway.dto.csis.Notification;
import com.castis.adgateway.util.CryptoUtil;
import com.castis.adlib.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Entity
@Table(name = "tbl_description", indexes = {@Index(name = "idx_vodRequestId", columnList = "vodRequestId")})
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Description {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="vodRequestId")
    private String vodRequestId;

    @Column(name="userKey")
    private String userKey;

    @Column(name="category")
    private String category;

    @Column(name="contentProvider")
    private String contentProvider;

    @Column(name="watchingGrade")
    private String watchingGrade;

    @Column(name="series")
    private String series;

    @Column(name="regionCode")
    private String regionCode;

    @Column(name="payPerView")
    private String payPerView;

    @Column(name="resumeYn")
    private String resumeYn;

    @Column(name="albumId")
    private String albumId;

    @Column(name="runTime")
    private Integer runTime;

    @Column(name="hdContent")
    private boolean hdContent;

    @Column(name="registerDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime registerDate;

    public Description(Notification notification) throws NoSuchAlgorithmException {
        this.vodRequestId = notification.getVodRequestId();

        //유저
        if(StringUtil.isNull(notification.getUser()) || StringUtil.isNull(notification.getUser().getStbId()))
            this.userKey = null;

        this.userKey = CryptoUtil.md5(notification.getUser().getStbId());

        //시청등급
        if(StringUtil.isNull(notification.getAsset()) || StringUtil.isNull(notification.getAsset().getRating()))
            this.watchingGrade = null;

        String rating = notification.getAsset().getRating();

        if(rating.equals("00") || rating.equals("ALL") || rating.equals("일반") || rating.equals("전체"))
            this.watchingGrade = "1";
        else if(rating.equals("07") || rating.equals("7세"))
            this.watchingGrade = "2";
        else if(rating.equals("12") || rating.equals("12세"))
            this.watchingGrade = "3";
        else if(rating.equals("15") || rating.equals("15세") || rating.equals("15세이상"))
            this.watchingGrade = "4";
        else if(rating.equals("19") || rating.equals("19세"))
            this.watchingGrade = "5";
        else
            this.watchingGrade = "6";

        //가격정보
        if(StringUtil.isNull(notification.getProduct()))
            this.payPerView = null;
        if(notification.getProduct().getPrice() > 0.0)
            this.payPerView = "ppv";
        else
            this.payPerView = "fod";

        //이어보기 유무
        if(StringUtil.isNull(notification.getWatchInfo()))
            this.resumeYn = null;

        if(notification.getWatchInfo().getOffset() > 0L)
            this.resumeYn = "y";
        else
            this.resumeYn = "n";

        // 컨텐츠 길이
        if(StringUtil.isNull(notification.getAsset()) || StringUtil.isNull(notification.getAsset().getRunningTime()))
            this.runTime = null;
        else {
            String runningTime = notification.getAsset().getRunningTime();
            LocalTime time= LocalTime.parse(runningTime, DateTimeFormatter.ofPattern("HH:mm"));
            this.runTime = time.getHour() * 60 + time.getMinute();
        }

        if(StringUtil.isNull(notification.getAsset()) || StringUtil.isNull(notification.getAsset().getAssetId()))
            this.albumId = null;
        else
            this.albumId = notification.getAsset().getAssetId();

        if(StringUtil.isNull(notification.getAsset()) || StringUtil.isNull(notification.getAsset().getHdContent()))
            this.hdContent = false;
        else {
            this.hdContent = notification.getAsset().getHdContent().equalsIgnoreCase("y");
        }
    }

}