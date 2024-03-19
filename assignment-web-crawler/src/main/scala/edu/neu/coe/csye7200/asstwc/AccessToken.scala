package edu.neu.coe.csye7200.asstwc

import scala.sys.process._

object AccessToken {
  def main(args: Array[String]): Unit = {
    val command = Seq("curl", "-X", "POST", "https://accounts.spotify.com/api/token",
      "-H", "Content-Type: application/x-www-form-urlencoded",
      "-d", "grant_type=client_credentials&client_id=d8401c345ca340baba8505d9cfa2924f&client_secret=f1cf967e67a94f1287a95bd271d7d817")

    val processBuilder = Process(command)
    val process = processBuilder.run()
    val exitCode = process.exitValue()

  }
}
