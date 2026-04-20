plugins {
	java
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
description = "Java api with an mvc layout"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(22)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
	all {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
	create("hibernateTools")
}

repositories {
	mavenCentral()
	maven {
		url = uri("https://repository.jboss.org/nexus/content/groups/public/")
	}
}

dependencies {
	//swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.microsoft.sqlserver:mssql-jdbc:12.6.1.jre11")
	"hibernateTools"("org.hibernate:hibernate-tools:5.6.15.Final")
	"hibernateTools"("org.hibernate:hibernate-core:5.6.15.Final")
	"hibernateTools"("javax.persistence:javax.persistence-api:2.2")
	"hibernateTools"("com.microsoft.sqlserver:mssql-jdbc:12.8.1.jre11")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-log4j2")
}

sourceSets {
	main {
		java {
			srcDir("src/main/java")
		}
	}
}

tasks.register("reverseEngineer") {
	doLast {
		val hibernateTools = configurations.getByName("hibernateTools")
		ant.withGroovyBuilder {
			"taskdef"(
				"name" to "hibernatetool",
				"classname" to "org.hibernate.tool.ant.HibernateToolTask",
				"classpath" to hibernateTools.asPath
			)
			"hibernatetool"(
				"destdir" to "src/main/java",
				"templatepath" to "src/main/resources"
			) {
				"jdbcconfiguration"(
					"propertyfile" to file("src/main/resources/hibernate.properties").absolutePath,
					"revengfile" to file("src/main/resources/hibernate.reveng.xml").absolutePath,
					"packagename" to "com.example.demo.Entities"
				)
				"hbm2java"(
					"jdk5" to true,
					"ejb3" to true
				)
			}
		}
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
