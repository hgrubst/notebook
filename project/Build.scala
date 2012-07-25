import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "notebook"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
    	"commons-collections" % "commons-collections" % "3.2.1",
    	"org.springframework.data" % "spring-data-mongodb" % "1.0.2.RELEASE",
    	"play" %% "spring" % "2.0",
    	"org.springframework" % "spring-test" % "3.1.1.RELEASE"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here
      
      //for spring
      resolvers += "Spring Maven Release Repository" at "http://repo.springsource.org/libs-release",

      //for play spring
      resolvers += "TAMU Release Repository" at "https://maven.library.tamu.edu/content/repositories/releases/"
      
            
    )

}
