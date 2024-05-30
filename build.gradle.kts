val grpcVer: String by project // 1.64.0
val protobufVer : String by project // 3.25.1

plugins {
    java
    id("com.google.protobuf") version "0.9.4"
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

group = "ru.pachan"
version = "0.0.1"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("io.grpc:grpc-protobuf:$grpcVer")
    implementation("io.grpc:grpc-stub:$grpcVer")
    runtimeOnly("io.grpc:grpc-netty-shaded:$grpcVer")
    compileOnly("jakarta.annotation:jakarta.annotation-api:1.3.5") // Java 9+ compatibility - Do NOT update to 2.0.0
}
//val generatedFilesBaseDir = file("${layout.buildDirectory}/generated/source/proto")

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:$protobufVer"
    }


    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:$grpcVer"
        }
    }

    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
            }
        }
    }
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}