import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import io.circe._
import io.circe.parser._
import io.circe.generic.auto._
import requests._

case class Playlist(id: String, name: String, followers: Int)
case class Artist(id: String, name: String, followers: Int)
case class Track(name: String, duration_ms: Long, artists: List[Playlist])

object SpotifyPlaylistAnalyzer {
  val playlistId = "5Rrf7mqN8uus2AaQQQNdc1"
  val playlistUrl = s"https://api.spotify.com/v1/playlists/$playlistId"
  val artistUrlBase = "https://api.spotify.com/v1/artists/"
  val accessToken = "BQAx9U0nAdEFTeglY-NYHiUDQuQLbytUBXRUNc_09nWXBcma5oHd5pRFWGigLhtOY7KMD-XFSnNdSWCCbGEZ44QLVfblJSK79f5ye81TIyer_UQGAsE"

  def getPlaylist(): Future[List[Track]] = {
    val response = requests.get(
      playlistUrl,
      headers = Map("Authorization" -> s"Bearer $accessToken")
    )
    val tracksJson = parse(response.text)

    tracksJson match {
      case Left(parseFailure) =>
        println(s"Failed to parse JSON: $parseFailure")
        Future.successful(List.empty)
      case Right(json) =>
        val topTracks = json.hcursor.downField("tracks").downField("items").as[List[Json]].getOrElse(List.empty).map { trackJson =>
          val name = trackJson.hcursor.downField("track").downField("name").as[String].getOrElse("")
          val duration = trackJson.hcursor.downField("track").downField("duration_ms").as[Long].getOrElse(0L)
          val artists = trackJson.hcursor.downField("track").downField("artists").as[List[Json]].getOrElse(List.empty).map { artistJson =>
            val artistName = artistJson.hcursor.downField("name").as[String].getOrElse("")
            val artistId = artistJson.hcursor.downField("id").as[String].getOrElse("")
            Playlist(artistName, artistId, 0)
          }
          Track(name, duration, artists)
        }
        topTracks.foreach { track =>
          println(s"Track: ${track.name}, Duration: ${track.duration_ms}, Artists: ${track.artists.map(_.name).mkString(", ")}")
        }
        Future.successful(topTracks)
    }
  }

  def getArtist(artistId: String): Future[Artist] = {
    val artistUrl = s"https://api.spotify.com/v1/artists/{id}"
    // val accessToken = "BQAAZEZkxBKhLFxZHKq4AiE2SqgT2BV41EkiYONMZlQB4kG1rRvIL0-V9hXmtOaZn0Joi1XR9ddIlp7Ghv02I6HzHKtAeaOeSkAtbH0yUp5lnmMZ8kM"
    val response = requests.get(
      artistUrl,
      headers = Map("Authorization" -> s"Bearer $accessToken")
    )
    response.statusCode match {
      case 200 =>
        val artistJson = parse(response.text).getOrElse(Json.Null)
        val artistName = artistJson.hcursor.downField("name").as[String].getOrElse("")
        val followers = artistJson.hcursor.downField("followers").downField("total").as[Int].getOrElse(0)
        println(s"Artist Name: $artistName, Followers: $followers")
        Future.successful(Artist(artistId, artistName, followers))

    }
  }

  def main(args: Array[String]): Unit = {
    val playlistFuture = getPlaylist()
    playlistFuture.flatMap { tracks =>
        println("Part 1:")
        tracks.take(10).zipWithIndex.foreach { case (track, index) =>
          println(s"Songname${index + 1} , ${track.name} , ${track.duration_ms}")
        }
        val artistIds = tracks.flatMap(_.artists).distinctBy(_.id).take(10).map(_.id)
        val artistFutures = artistIds.map { artistId =>
          getArtist(artistId).map { detailedArtist =>
            (artistId, detailedArtist)
          }
        }
        Future.sequence(artistFutures).map { detailedArtists =>
          val sortedArtists = detailedArtists.sortBy(_._2.followers).reverse
          println("\nPart 2:")
          sortedArtists.foreach { case (artistId, detailedArtist) =>
            println(s"Artist ${detailedArtist.name}: ${detailedArtist.followers}")
          }
        }
      }
      .onComplete {
        case scala.util.Success(_) => println("Success")
        case scala.util.Failure(exception) => println(s"Error: ${exception.getMessage}")
      }
  }
}
