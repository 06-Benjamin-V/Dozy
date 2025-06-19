plugins {
	java
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
	id("jacoco") // ← cobertura de tests
	id("org.sonarqube") version "4.4.1.3373" // ← SonarQube
}


group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

jacoco {
	toolVersion = "0.8.10"
}

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport) // Generar el reporte después del test
}

tasks.jacocoTestReport {
	dependsOn(tasks.test) // primero ejecuta los tests
	reports {
		xml.required.set(true) // ← requerido por SonarQube
		html.required.set(true)
	}
}


sonarqube {
	properties {
		property("sonar.projectKey", "dozyBack") // cambia por el nombre de tu proyecto
		property("sonar.projectName", "dozyBack")
		property("sonar.host.url", "http://localhost:9000") // dirección del servidor Sonar
		property("sonar.token", "sqp_5c145a9e06585e69461c2a97bfcb3d8f5c5f6d98") // token generado en tu SonarQube
		property("sonar.junit.reportPaths", "build/test-results/test")
		property("sonar.jacoco.reportPaths", "build/jacoco/test.exec")
		property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
	}
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.google.firebase:firebase-admin:9.2.0")
	implementation("com.fasterxml.jackson.core:jackson-databind")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.mockito:mockito-core:5.11.0")
	testImplementation("org.mockito:mockito-junit-jupiter:5.11.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}