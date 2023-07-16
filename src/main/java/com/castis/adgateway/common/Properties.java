package com.castis.adgateway.common;


import com.castis.adlib.util.idgenerator.IdGenerator;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.castis.adlib.util.tableLog.Block;
import com.castis.adlib.util.tableLog.Board;
import com.castis.adlib.util.tableLog.Table;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.List;


@Component("properties")
@Data
@Slf4j
public class Properties {

	@Value("${datacenter}")
	private Integer datacenter;

	@Value("${retry.dir:/retry/}")
	private String retryDir;

	@Value("${csisUrl:http://127.0.0.1:8080/CSIS/}")
	private String csisUrl;

	@Value("${lguUrl:http://127.0.0.1/shark-api/v1/request}")
	private String lguAdUrl;

	@Value("${csis.serverTimeOut:10000}")
	private Integer csisServerTimeOut;

	@Value("${lguAd.serverTimeOut:10000}")
	private Integer lguAdServerTimeOut;


	@Value("${assetDispatcherUrl:http://127.0.0.1:8080/assetDispatcher}")
	private String assetDispatcherUrl;

	@Value("${assetDispatcher.serverTimeOut:10000}")
	private Integer assetDispatcherServerTimeOut;

	@Value("${adsmUrl:http://127.0.0.1:8080/adsm}")
	private String adsmUrl;

	@Value("${adsm.serverTimeOut:10000}")
	private Integer adsmServerTimeOut;

	@Value("${lguAd.chance:50}")
	private Double lguAdChance;

	@Value("${dummy.ad.sd.file}")
	private String dummySdAdFile;

	@Value("${dummy.ad.hd.file}")
	private String dummyHdAdFile;

	@Value("${retry.other.ad:false}")
	private Boolean retryOtherAD;

	//AdGateway DB


	@Value("${jdbc.url.AD}")
	private String gateway_url;

	@Value("${jdbc.username.AD}")
	private String gateway_username;

	@Value("${jdbc.password.AD}")
	private String gateway_password;

	//hibernate
	@Value("${driver.class.name:com.mysql.jdbc.Driver}")
	private String driverClassName;

	@Value("${hibernate.hbm2ddl.auto.AD:update}")
	private String hibernateHbm2ddlAuto_AD;
	
	@Value("${hibernate.dialect:org.hibernate.dialect.MySQL5InnoDBDialect}")
	private String hibernateDialect;

	@Value("${hibernate.show_sql:false}")
	private String hibernateShowSql;

	@Value("${hibernate.format_sql:true}")
	private String hibernateFormatSql;

	@Value("${hibernate.use_sql_comments:true}")
	private String hibernateUseSqlComments;

	@Value("${hibernate.id.new_generator_mappings:true}")
	private String hibernateIdNewGeneratorMappings;
	public Properties() {
		super();
	}


	@PostConstruct
	public void setAndPrintProperties() throws Exception {

		FileUtils.forceMkdir(new File(this.retryDir));
		IdGenerator.getInstance().setDatacenterId(this.datacenter);
		List<String> headersList = Arrays.asList("KEY", "VALUE");
		List<List<String>> rowsList_properties = Arrays.asList(
				Arrays.asList("datacenter", String.valueOf(this.datacenter)),
				Arrays.asList("retry.dir", this.retryDir),
				Arrays.asList("csisUrl", this.csisUrl),
				Arrays.asList("lguUrl", this.lguAdUrl),
				Arrays.asList("csis.serverTimeOut", String.valueOf(this.csisServerTimeOut)),
				Arrays.asList("lguAd.serverTimeOut", String.valueOf(this.lguAdServerTimeOut)),
				Arrays.asList("assetDispatcherUrl", this.assetDispatcherUrl),
				Arrays.asList("assetDispatcher.serverTimeOut", String.valueOf(this.assetDispatcherServerTimeOut)),
				Arrays.asList("adsmUrl", this.adsmUrl),
				Arrays.asList("adsm.serverTimeOut", String.valueOf(this.adsmServerTimeOut)),
				Arrays.asList("lguAd.chance", String.valueOf(this.lguAdChance)),
				Arrays.asList("dummy.ad.sd.file", this.dummySdAdFile),
				Arrays.asList("dummy.ad.hd.file", this.dummyHdAdFile),
				Arrays.asList("retry.other.ad", String.valueOf(this.retryOtherAD))
		);

				
		Board board = new Board(150);
		board.setInitialBlock(new Block(board, 145, 1, "assetDispatcher").setDataAlign(Block.DATA_CENTER));
		board.appendTableTo(0, Board.APPEND_BELOW, new Table(board, 147, headersList, rowsList_properties));
		board.build();
		
		String tableString = board.getPreview();						
		log.info("\n" + tableString);

	}



}
