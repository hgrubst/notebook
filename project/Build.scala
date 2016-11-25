import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "notebook"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
    	"commons-collections" % "commons-collections" % "3.2.1",
    	"org.springframework.data" % "spring-data-mongodb" % "1.1.1.RELEASE",
    	"play" %% "spring" % "2.0",
    	"org.springframework" % "spring-core" % "3.1.2.RELEASE",
    	"org.springframework" % "spring-context" % "3.1.2.RELEASE",
    	"org.springframework" % "spring-beans" % "3.1.2.RELEASE",
    	"org.springframework" % "spring-test" % "3.1.2.RELEASE",
    	"org.cloudfoundry" % "cloudfoundry-runtime" % "0.8.2",
        "org.markdownj" % "markdownj" % "0.3.0-1.0.2b4",
        "org.mindrot" % "jbcrypt" % "0.3m",
        "org.hibernate" % "hibernate-validator" % "4.2.0.Final",
        "javax.validation" % "validation-api" % "1.1.0.Final"
        
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here
      
      // Force compilation in java 1.7
	  javacOptions in Compile ++= Seq("-source", "1.7", "-target", "1.7"),
      
      //for spring
      resolvers += "Spring Maven Release Repository" at "http://repo.springsource.org/libs-release",

      //for cloud foundry
      resolvers += "Spring Maven Milestone Repository" at "http://maven.springframework.org/milestone",

      //for play spring
      resolvers += "TAMU Release Repository" at "https://maven.library.tamu.edu/content/repositories/releases/",
      
      //for markdownj
      resolvers += "Sonatype scala tools" at "https://oss.sonatype.org/content/groups/scala-tools/"
    )

}
