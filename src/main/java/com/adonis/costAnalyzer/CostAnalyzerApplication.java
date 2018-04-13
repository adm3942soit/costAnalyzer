package com.adonis.costAnalyzer;

import com.adonis.costAnalyzer.utils.DatabaseUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;
import java.util.TimeZone;

import static com.adonis.costAnalyzer.install.InstallConstants.DEFAULT_TIMEZONE;
import static com.adonis.costAnalyzer.install.InstallConstants.INSTALL;
import static com.adonis.costAnalyzer.install.Installer.createShortcat;

@SpringBootApplication
public class CostAnalyzerApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone(DEFAULT_TIMEZONE));
		Locale.setDefault(new Locale("lv", "LV", "Latvia"));

//		if(args!=null && args.length>0 && args[0].equals("install") ){
			INSTALL = true;
//		}else{
//			INSTALL = false;
//		}
//		if( INSTALL ) createShortcat();
		try {
			if( INSTALL ) DatabaseUtils.createDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		SpringApplication.run(CostAnalyzerApplication.class, args);
	}
}
