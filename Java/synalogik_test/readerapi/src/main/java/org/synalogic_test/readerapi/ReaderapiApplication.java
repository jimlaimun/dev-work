package org.synalogic_test.readerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.synalogic_test.readerapi.fileuploaddownload.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class ReaderapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReaderapiApplication.class, args);
	}

}
