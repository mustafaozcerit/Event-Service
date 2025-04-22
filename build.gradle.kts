plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("com.google.protobuf") version "0.9.1"
	id("java")
}

group = "com.opthema.hcm"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

val grpcVersion = "1.53.0"
val protobufVersion = "3.25.5"

extra["springCloudVersion"] = "2024.0.0"

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.cloud:spring-cloud-config-server")
	implementation("org.springframework.cloud:spring-cloud-starter-config")
	implementation("org.springframework.cloud:spring-cloud-starter-gateway-mvc")
	implementation("com.microsoft.sqlserver:mssql-jdbc:12.2.0.jre11")
	implementation("org.postgresql:postgresql")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.springframework.kafka:spring-kafka")
	implementation("org.mapstruct:mapstruct:1.5.3.Final")

	//Grpc
	implementation("io.grpc:grpc-protobuf:$grpcVersion")
	implementation("io.grpc:grpc-stub:$grpcVersion")
	runtimeOnly("io.grpc:grpc-netty-shaded:$grpcVersion")
	implementation("com.google.protobuf:protobuf-java-util:$protobufVersion")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	// grpc-spring-boot-starter
	implementation("net.devh:grpc-server-spring-boot-starter:2.15.0.RELEASE")
//	implementation("net.devh:grpc-server-spring-boot-autoconfigure:2.15.0.RELEASE")
	implementation("net.devh:grpc-client-spring-boot-starter:2.15.0.RELEASE")

	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Final")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:$protobufVersion"
	}
	plugins {
		create("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:$grpcVersion"
		}
	}
	generateProtoTasks {
		all().forEach { task ->
			task.plugins {
				create("grpc") {}
			}
		}
	}
}

sourceSets {
	main {
		proto {
			srcDir("src/main/proto")
		}
		java {
			srcDirs("build/generated/source/proto/main/java", "build/generated/source/proto/main/grpc")
		}
	}
}
dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// Grpc dosyaların vs çakışma yaşandığından öncekini dışla son halini al anlamına geliyor
tasks.processResources {
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
